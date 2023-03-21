package com.tmp.bff.controller.home;

import com.tmp.bff.dto.home.HomePageDto;
import com.tmp.bff.dto.common.Resp;
import com.tmp.bff.util.RespUtil;
import com.tmp.bff.service.home.HomeService;
import com.tmp.bff.version.annotation.ApiVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author jiangyaxiong
 */

@Slf4j
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/home-desc")
    public Resp<String> home() {
        log.info("home");
        return RespUtil.success("home without version");

    }

    @GetMapping("/home-desc")
    @ApiVersion(minVersion = "1.5.0")
    public Resp<String> home0() {
        log.info("home0 1.5.0");
        return RespUtil.success("home after 1.5.0");

    }

    @GetMapping("/home-desc")
    @ApiVersion(minVersion = "2.9.0")
    public Resp<HomePageDto> home1(@RequestParam(value = "level", required = false) Byte level,
                                  @RequestHeader("X-CLIENT-VERSION") String version,
                                  @RequestHeader(value = "X-CLIENT-DEVICEID", required = false, defaultValue = "DEVICEID1") String deviceId) {
        log.info("home before level: {}, version: {}, deviceId: {}", level, version, deviceId);
        return homeService.home(level, version, deviceId);

    }
}
