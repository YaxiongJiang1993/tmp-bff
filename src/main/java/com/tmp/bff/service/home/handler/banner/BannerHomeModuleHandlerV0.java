package com.tmp.bff.service.home.handler.banner;

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
 * @since 10/18/2021
 */
@Slf4j
@Component
@ModuleHandlerVersion(type = 0, minVersion = "2.4.0")
public class BannerHomeModuleHandlerV0 extends AbstractHomeModuleHandler {

    @Override
    public void handle(HomeModuleDto homeModuleDto, GetHomeRequest getHomeRequest) {
        log.info("BannerHomeModuleHandlerV0 homeModuleDto: {}, getHomeRequest: {}", homeModuleDto, getHomeRequest);
        List<HomeContentDto> homeContentDtos = Lists.newArrayList();
        /*
         * 业务逻辑 这里做模拟
         */
        homeContentDtos.add(
                HomeContentDto.builder()
                        .id(10L)
                        .title("banner-10")
                        .desc("banner-10, minVersion=2.4.0")
                        .picUrl("https://test.com/banner/content10")
                        .url("https://test.com/banner/jump10")
                        .build()
        );
        homeModuleDto.setData(homeContentDtos);
    }


}
