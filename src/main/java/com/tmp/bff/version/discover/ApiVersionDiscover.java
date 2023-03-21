package com.tmp.bff.version.discover;

import javax.servlet.http.HttpServletRequest;

/**
 * ApiVersion 解析器
 *
 * @author jiangyaxiong
 */
public interface ApiVersionDiscover {


    /**
     *  获取header中或者url中版本号
     *
     * @param request request
     * @return api版本号
     */
    int getVersionCode(HttpServletRequest request);

}
