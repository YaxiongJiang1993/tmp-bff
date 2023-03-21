package com.tmp.bff.dto.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 首页模块 信息
 *
 * @author jiangyaxiong
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HomeModuleDto {

    /**
     * 模块id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述 副标题
     */
    private String desc;

    /**
     * 模块url
     */
    private String url;

    /**
     * 模块类型 0=banner, 1=金刚位, 2=瓷片区, 3=推荐课程区单图, 4=竖版内容列表, 5=横版内容列表,6=推荐课程区双图
     */
    private Byte type;

    /**
     * 是否展示更多 0 不展示 1展示
     */
    private Byte showMore;

    /**
     * 0 使用默认数据展示 1 根据url从后端拉取数据
     */
    private Byte pull;

    /**
     * 支持最小版本
     */
    private String minVersion;

    /**
     * 支持最大版本
     */
    private String maxVersion;

    /**
     * 模块内容
     */
    private List<HomeContentDto> data;


    /**
     * 附录参数
     */
    private List<Map<String, Object>> extra;

    /**
     * 强制展示
     */
    private Boolean displayForcely = false;

    /**
     * 填充结果
     */
    private Boolean pullResult = false;

    /**
     * 是否需要动态pull数据
     *
     * @return boolean
     */
    public boolean shouldPull() {
        return Byte.valueOf("1").equals(this.pull);
    }

}
