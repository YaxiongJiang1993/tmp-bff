package com.tmp.bff.service.rpc;

import com.google.common.collect.Lists;
import com.tmp.bff.constant.HomeModuleTypeEnum;
import com.tmp.bff.dto.common.Resp;
import com.tmp.bff.dto.home.GetHomeRequest;
import com.tmp.bff.dto.home.HomeContentDto;
import com.tmp.bff.dto.home.HomeModuleDto;
import com.tmp.bff.dto.home.HomePageDto;
import com.tmp.bff.util.RespUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模拟rpc,本来不需要用feign
 *
 * @author Yaxio
 */
@Service
public class HomePageRpcService {

    public Resp<HomePageDto> homeDesc(GetHomeRequest getHomeRequest) {
        List<HomeModuleDto> modules = Lists.newArrayList();
        // banner 动态填充
        modules.add(
                HomeModuleDto.builder()
                        .id(1)
                        .title("banner")
                        .desc("banner区")
                        .url("https://test.com/banner")
                        .type(HomeModuleTypeEnum.BANNER.getId())
                        .pull((byte) 1)
                        .displayForcely(false)
                        .build()
        );
        // 金刚区
        List<HomeContentDto> contentDtos = Lists.newArrayList();
        contentDtos.add(
                HomeContentDto.builder()
                        .id(1L)
                        .title("金刚-1")
                        .desc("金刚-1 描述")
                        .picUrl("https://test.com/jingang/content1")
                        .url("https://test.com/jingang/jump1")
                        .build()
        );
        modules.add(
                HomeModuleDto.builder()
                        .id(1)
                        .title("金刚区")
                        .desc("金刚区")
                        .url("https://test.com/jingang")
                        .type(HomeModuleTypeEnum.JIN_GANG.getId())
                        .pull((byte) 0)
                        .displayForcely(false)
                        .data(contentDtos)
                        .build()
        );
        // procelain 动态填充
        modules.add(
                HomeModuleDto.builder()
                        .id(1)
                        .title("procelain")
                        .desc("瓷片")
                        .url("https://test.com/procelain")
                        .type(HomeModuleTypeEnum.PROCELAIN.getId())
                        .pull((byte) 1)
                        .displayForcely(false)
                        .build()
        );
        return RespUtil.success(
                HomePageDto.builder()
                        .modules(modules)
                        .build()
        );
    }
}
