package com.tmp.bff.controller;

import com.tmp.bff.version.annotation.ApiVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author jiangyaxiong
 */

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    @ApiVersion(minVersion = "2.9.0")
    public String test(){
        return "test~2.9.0+";
    }

    @GetMapping("/test")
    @ApiVersion(minVersion = "3.9.0")
    public String test1(){
        return "test~3.9.0+";
    }

    @GetMapping("/test")
    public String test0(){
        return "test";
    }
}
