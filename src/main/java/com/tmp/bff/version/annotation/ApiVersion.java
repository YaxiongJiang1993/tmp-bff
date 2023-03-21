package com.tmp.bff.version.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 适配方法版本 eg. 2.3.1
 * 当前最小版本到下一个最小版本之间
 *
 * @author jiangyaxiong
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiVersion {

    String minVersion() default "";
}
