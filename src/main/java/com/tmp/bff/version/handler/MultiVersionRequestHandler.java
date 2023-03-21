package com.tmp.bff.version.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.method.HandlerMethod;

/**
 * 多版本请求处理
 *
 * @author jiangyaxiong
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MultiVersionRequestHandler {

    private Integer minVersion;

    private HandlerMethod handlerMethod;
}
