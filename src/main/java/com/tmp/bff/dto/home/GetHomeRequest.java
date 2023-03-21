package com.tmp.bff.dto.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

/**
 * 首页描述请求对象
 *
 * @author jiangyaxiong
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetHomeRequest {

    @Positive(message = "userId不合法")
    private Long userId;

    @NotEmpty(message = "deviceId不可以为空")
    private String deviceId;

    @Positive(message = "roleId不合法")
    private Integer roleId;

    private String level;

    @NotEmpty(message = "version不可以为空")
    private String version;

    /**
     * baby年龄
     */
    private Integer age;

    /**
     * 是否是vip
     */
    private Byte vip;
}