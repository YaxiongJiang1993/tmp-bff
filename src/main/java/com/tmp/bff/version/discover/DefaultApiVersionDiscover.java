package com.tmp.bff.version.discover;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static com.tmp.bff.version.constant.CommonConstant.HEADER_VERSION_NAME;
import static com.tmp.bff.version.constant.CommonConstant.URL_VERSION_NAME;
import static com.tmp.bff.version.util.VersionParseUtil.getVersionNum;


/**
 * default implements for {@link ApiVersionDiscover}
 *
 * @author jiangyaxiong
 */
public class DefaultApiVersionDiscover implements ApiVersionDiscover {

    private static final Logger log = LoggerFactory.getLogger(DefaultApiVersionDiscover.class);

    /**
     * 解析request的版本号
     *
     * @param request current HTTP request
     * @return -1 不存在版本号 -2 版本号不合法 >0 正常版本号
     */
    @Override
    public int getVersionCode(HttpServletRequest request) {
        String version = request.getHeader(HEADER_VERSION_NAME);
        if (!StringUtils.hasText(version)) {
            version = request.getParameter(URL_VERSION_NAME);
        }
        if (!StringUtils.hasText(version)) {
            return -1;
        }
        int versionCode = 0;
        try {
            versionCode = getVersionNum(version);
        } catch (Exception e) {
            versionCode = -1;
            log.warn("getVersionCode getVersionNum exceptioned, msg: {}", e.getMessage(), e);
        }

        return versionCode;
    }
}
