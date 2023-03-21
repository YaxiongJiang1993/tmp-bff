package com.tmp.bff.dto.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 首页内容 信息
 *
 * @author jiangyaxiong
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HomeContentDto {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述 副标题
     */
    private String desc;

    /**
     * 图片url
     */
    private String picUrl;

    /**
     * 内容url
     */
    private String url;

    /**
     * 图片用 image/png 视频用 video/mpeg4
     */
    private String contentType;

    /**
     * 附录参数
     */
    private Map<String, Object> extra;
}
