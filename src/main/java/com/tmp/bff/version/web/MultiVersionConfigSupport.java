package com.tmp.bff.version.web;

import com.tmp.bff.version.discover.DefaultApiVersionDiscover;
import com.tmp.bff.version.handler.MultiVersionRequestMappingHandlerMapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 多版本适配注入
 *
 * @author jiangyaxiong
 */
@Configuration
public class MultiVersionConfigSupport extends WebMvcConfigurationSupport {

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        MultiVersionRequestMappingHandlerMapping multiVersionHandlerMapping = new MultiVersionRequestMappingHandlerMapping();
        multiVersionHandlerMapping.registerApiVersionCodeDiscovers(new DefaultApiVersionDiscover());
        return multiVersionHandlerMapping;
    }
}
