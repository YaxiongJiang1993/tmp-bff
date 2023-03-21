package com.tmp.bff.service.home;


import com.tmp.bff.constant.HomeModuleTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ModuleHandlerVersion的版本信息
 *
 * @author jiangyaxiong
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModuleHandlerVersion {

    /**
     * @return HomeModuleTypeEnum id
     * @see HomeModuleTypeEnum
     */
    byte type();

    String minVersion();

    /**
     * 是否注册到策略工厂
     *
     * @return bool
     */
    boolean register() default true;
}
