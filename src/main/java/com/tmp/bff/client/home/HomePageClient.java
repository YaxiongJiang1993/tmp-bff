package com.tmp.bff.client.home;

import com.tmp.bff.dto.common.Resp;
import com.tmp.bff.dto.home.GetHomeRequest;
import com.tmp.bff.dto.home.HomePageDto;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

/**
 * 首页 api接口
 */
/*@FeignClient(
        value = "kids-client")*/
public interface HomePageClient {

    @GetMapping("/home-desc")
    Resp<HomePageDto> homeDesc(@Valid GetHomeRequest getHomeRequest);
}
