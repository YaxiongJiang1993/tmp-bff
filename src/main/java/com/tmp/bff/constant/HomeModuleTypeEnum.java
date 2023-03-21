package com.tmp.bff.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * C端首页模块
 *
 * @author jiangyaxiong
 */
public enum HomeModuleTypeEnum {

    /**
     * 各种类型模块
     *
     */
    BANNER((byte)0, "首页顶部banner", "banner"),
    JIN_GANG((byte)1, "金刚位", "金刚位"),
    PROCELAIN((byte)2, "首页瓷片区", "瓷片区"),
    RECOMMAND_SINGLE((byte)3, "推荐课程区单图", "推荐课程区单图"),
    VERTICLE_CONTENT((byte)4, "竖版内容列表", "竖版内容列表"),
    HORIZONTAL_CONTENT((byte)5, "横版内容列表", "横版内容列表"),
    RECOMMAND_DOUBLE((byte)6, "推荐课程区双图", "推荐课程区双图"),
    VOICE_ALBUM((byte)7, "音频专辑", "音频专辑"),
    VIDEO_ALBUM((byte)8, "视频专辑", "音频专辑"),
    AGE_VIDEO_ALBUM((byte)9, "月龄专辑", "月龄专辑"),
    ;

    private static Map<Byte, HomeModuleTypeEnum> map;

    HomeModuleTypeEnum(Byte id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    @Getter
    private Byte id;

    @Getter
    private String name;

    @Getter
    private String desc;

    public static HomeModuleTypeEnum valueOf(Integer id) {
        return map.get(id);
    }

    static {
        map = Arrays.stream(values())
                .collect(toMap(HomeModuleTypeEnum::getId, identity()));
    }
}
