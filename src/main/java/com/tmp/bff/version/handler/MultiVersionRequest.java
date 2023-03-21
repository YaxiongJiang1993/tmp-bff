package com.tmp.bff.version.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 多版本请求
 *
 * @author jiangyaxiong
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MultiVersionRequest {

    private String path;

    private String method;

}
