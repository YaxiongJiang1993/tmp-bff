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
@ModuleHandlerVersion(type = 0, minVersion = "3.2.0")
public class BannerHomeModuleHandlerV1 extends AbstractHomeModuleHandler {

    @Override
    public void handle(HomeModuleDto homeModuleDto, GetHomeRequest getHomeRequest) {
        log.info("BannerHomeModuleHandlerV0 homeModuleDto: {}, getHomeRequest: {}", homeModuleDto, getHomeRequest);
        List<HomeContentDto> homeContentDtos = Lists.newArrayList();
        /*
         * 业务逻辑 这里做模拟
         */
        homeContentDtos.add(
                HomeContentDto.builder()
                        .id(20L)
                        .title("banner-20")
                        .desc("banner-20, minVersion=3.2.0")
                        .picUrl("https://test.com/banner/content20")
                        .url("https://test.com/banner/jump20")
                        .build()
        );
        homeModuleDto.setData(homeContentDtos);
    }


}
