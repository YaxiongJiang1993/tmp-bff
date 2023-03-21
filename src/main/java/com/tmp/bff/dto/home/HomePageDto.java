package com.tmp.bff.dto.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 首页模块 信息
 *
 * @author jiangyaxiong
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HomePageDto {

    private List<HomeModuleDto> modules;
}
