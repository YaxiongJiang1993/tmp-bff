package com.tmp.bff.service.home.handler.procelain;

import com.google.common.collect.Lists;
import com.tmp.bff.dto.home.GetHomeRequest;
import com.tmp.bff.dto.home.HomeContentDto;
import com.tmp.bff.dto.home.HomeModuleDto;
import com.tmp.bff.service.home.AbstractHomeModuleHandler;
import com.tmp.bff.service.home.ModuleHandlerVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 首页模块处理器V0
 *
 * @author jiangyaxiong
 */
@Slf4j
@Component
@ModuleHandlerVersion(type = 2, minVersion = "2.4.0")
public class PorcelainHomeModuleHandlerV0 extends AbstractHomeModuleHandler {

    @Override
    public void handle(HomeModuleDto homeModuleDto, GetHomeRequest getHomeRequest) {
        log.info("PorcelainHomeModuleHandlerV0 homeModuleDto: {}, getHomeRequest: {}", homeModuleDto, getHomeRequest);
        List<HomeContentDto> homeContentDtos = Lists.newArrayList();
        /*
         * 业务逻辑 这里做模拟
         */
        homeContentDtos.add(
                HomeContentDto.builder()
                        .id(100L)
                        .title("procelain-100")
                        .desc("procelain-100, minVersion=2.4.0")
                        .picUrl("https://test.com/procelain/content100")
                        .url("https://test.com/procelain/jump100")
                        .build()
        );
        homeModuleDto.setData(homeContentDtos);
    }


}
