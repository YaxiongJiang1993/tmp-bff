package com.tmp.bff.service.home;


import com.tmp.bff.constant.UserRoleEnum;
import com.tmp.bff.dto.common.Resp;
import com.tmp.bff.dto.home.GetHomeRequest;
import com.tmp.bff.dto.home.HomeModuleDto;
import com.tmp.bff.dto.home.HomePageDto;
import com.tmp.bff.service.common.UserCommonService;
import com.tmp.bff.service.rpc.HomePageRpcService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.tmp.bff.constant.UserRoleEnum.UN_REGISTER;
import static com.tmp.bff.util.ThreadPoolUtil.getThreadPool;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * HomeService
 *
 * @author jiangyaxiong
 */

@Slf4j
@Service
public class HomeService {

    @Autowired
    private HomeModuleHandlerFactory factory;

    @Autowired
    private HomePageRpcService homePageRpcService;

    @Autowired
    private UserCommonService userCommonService;

    public Resp<HomePageDto> home(Byte level, String version, String deviceId) {
        UserRoleEnum userRole = UN_REGISTER;
        try {
            userRole = userCommonService.getUserRole();
        } catch (Exception e) {
            log.warn("getUserRole exception, msg: {}", e.getMessage(), e);
        }
        /*
         * 准备各个模块需要的参数
         */
        GetHomeRequest getHomeRequest = GetHomeRequest.builder()
                .userId(userCommonService.getUserId())
                .deviceId(deviceId)
                .roleId(userRole.getId())
                .version(version)
                .vip(userCommonService.isVip() ? (byte) 1 : 0)
                .build();
        log.info("home after getHomeRequest: {}", getHomeRequest);
        return getHomePage(getHomeRequest);

    }

    private Resp<HomePageDto> getHomePage(GetHomeRequest getHomeRequest) {
        // rpc 获取
        Resp<HomePageDto> homePageDTOResp = homePageRpcService.homeDesc(getHomeRequest);
        if (!homePageDTOResp.isSuccess()) {
            log.error("getHomePage msg: {}, homePageDTOResp: {}", homePageDTOResp.getMsg(), homePageDTOResp);
            return homePageDTOResp;
        }
        HomePageDto homePageDto = homePageDTOResp.getData();
        List<HomeModuleDto> homeModulesWaitingFill = homePageDto.getModules()
                .stream()
                .filter(HomeModuleDto::shouldPull)
                .collect(Collectors.toList());
        CountDownLatch countDownLatch = new CountDownLatch(homeModulesWaitingFill.size());
        // 生成填充任务
        Set<FillTask> fillTasks = homeModulesWaitingFill.stream()
                .map(
                        e -> FillTask.builder()
                                .moduleDto(e)
                                .handler(factory.getHandler(e.getType(), getHomeRequest.getVersion()))
                                .request(getHomeRequest)
                                .latch(countDownLatch)
                                .build()
                )
                .collect(Collectors.toSet());
        // 执行填充任务
        fillTasks.forEach(e -> getThreadPool().execute(e));
        try {
            /*
             * 时间可以放到配置里，这里写死
             */
            countDownLatch.await(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("getHomePage countDownLatch time out, msg: {}", e.getMessage(), e);
            homePageDTOResp.setSuccess(false);
            homePageDTOResp.setMsg("服务器忙, 请稍后重试");
            homePageDTOResp.setData(null);
            return homePageDTOResp;
        }
        // 结果处理
        Set<HomeModuleDto> homeModuleFillErrors = homeModulesWaitingFill.stream()
                .filter(e -> !e.getPullResult())
                .collect(Collectors.toSet());
        if (isNotEmpty(homeModuleFillErrors)) {
            log.error("getHomePage homeModuleFillErrors homeModuleFillErrors: {}", homeModuleFillErrors);
            homePageDTOResp.setSuccess(false);
            homePageDTOResp.setMsg("服务器忙, 请稍后重试");
            homePageDTOResp.setData(null);
            return homePageDTOResp;
        }
        // 过滤
        List<HomeModuleDto> homeModuleAll = homePageDto.getModules();
        log.info("getHomePage homeModuleAll: {}", homeModuleAll);
        List<HomeModuleDto> homeModuleDisPlay = homeModuleAll
                .stream()
                .filter(e -> e.getDisplayForcely() || isNotEmpty(e.getData()))
                .collect(Collectors.toList());
        homePageDto.setModules(homeModuleDisPlay);
        return homePageDTOResp;
    }

    @Data
    @Slf4j
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    static class FillTask implements Runnable {

        private HomeModuleDto moduleDto;

        private HomeModuleHandler handler;

        private GetHomeRequest request;

        private CountDownLatch latch;

        @Override
        public void run() {
            try {
                handler.handle(moduleDto, request);
                moduleDto.setPullResult(true);
            } catch (Exception e) {
                log.error("handle exceptioned, msg: {}", e.getMessage(), e);
                moduleDto.setPullResult(false);
            } finally {
                latch.countDown();
            }
        }
    }
}
