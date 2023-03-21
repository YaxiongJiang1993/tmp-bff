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
@ModuleHandlerVersion(type = 2, minVersion = "3.2.0")
public class PorcelainHomeModuleHandlerV1 extends AbstractHomeModuleHandler {

    @Override
    public void handle(HomeModuleDto homeModuleDto, GetHomeRequest getHomeRequest) {
        log.info("PorcelainHomeModuleHandlerV1 homeModuleDto: {}, getHomeRequest: {}", homeModuleDto, getHomeRequest);
        List<HomeContentDto> homeContentDtos = Lists.newArrayList();
        /*
         * 业务逻辑 这里做模拟
         */
        homeContentDtos.add(
                HomeContentDto.builder()
                        .id(200L)
                        .title("procelain-200")
                        .desc("procelain-200, minVersion=3.2.0")
                        .picUrl("https://test.com/procelain/content200")
                        .url("https://test.com/procelain/jump200")
                        .build()
        );
        homeModuleDto.setData(homeContentDtos);
    }


}
