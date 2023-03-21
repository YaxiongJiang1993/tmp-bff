package com.tmp.bff.service.home;

import com.tmp.bff.dto.home.GetHomeRequest;
import com.tmp.bff.dto.home.HomeModuleDto;

/**
 * HomeModuleHandler
 *
 * @author jiangyaxiong
 */
public interface HomeModuleHandler {

    /**
     * 处理HomeModuleDto
     *
     * @param moduleDto moduleDto
     * @param request request
     * @throws Exception Exception
     */
    void handle(HomeModuleDto moduleDto, GetHomeRequest request) throws Exception;
}
