package com.tmp.bff.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * C端用户首页角色
 *
 * @author jiangyaxiong
 */
public enum UserRoleEnum {

    /**
     * 使用box和live 区分盒子和直播
     */
    UN_REGISTER(1, "未注册", 8, "兜底页面"),
    NEW_PURE(2, "纯新用户", 7, "注册，未购买体验期用户"),
    TRIVAL_BOX(3, "体验期-盒子", 5, "已购买29元盒子课程，且课程在有效期内"),
    TRIVAL_LIVE(4, "体验期-直播", 5, "已购买29元直播课程，且课程在有效期内"),
    FORMAL_BOX(5, "正式期-盒子", 3, "已购买盒子正式课的用户"),
    FORMAL_LIVE(6, "正式期-直播", 2, "已购买直播正价课用户"),
    FORMAL_NO_WARRY(7, "正式期-无忧", 1, "已经购买无忧卡的用户"),
    ;

    private static Map<Integer, UserRoleEnum> map;

    UserRoleEnum(Integer id, String name, Integer priority, String desc) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.desc = desc;
    }

    @Getter
    private Integer id;

    @Getter
    private String name;

    @Getter
    private Integer priority;

    @Getter
    private String desc;

    public static UserRoleEnum valueOf(Integer id) {
        return map.get(id);
    }

    static {
        map = Arrays.stream(values())
                .collect(toMap(UserRoleEnum::getId, identity()));
    }

    public static byte vip(UserRoleEnum userRole) {
        boolean formal = userRole == FORMAL_BOX || userRole == FORMAL_LIVE || userRole == FORMAL_NO_WARRY;
        return formal ? (byte) 1 : 0;
    }
}
