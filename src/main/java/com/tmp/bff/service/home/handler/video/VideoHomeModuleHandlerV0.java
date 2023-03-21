package com.tmp.bff.service.home.handler.video;

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
@ModuleHandlerVersion(type = 8, minVersion = "2.5.0")
public class VideoHomeModuleHandlerV0 extends AbstractHomeModuleHandler {

    @Override
    public void handle(HomeModuleDto homeModuleDto, GetHomeRequest getHomeRequest) {
        log.info("AgeVideoHomeModuleHandlerV0 homeModuleDto: {}, getHomeRequest: {}", homeModuleDto, getHomeRequest);
        List<HomeContentDto> homeContentDtos = Lists.newArrayList();
        /*
         * 业务逻辑
         */
        homeModuleDto.setData(homeContentDtos);
    }


}
