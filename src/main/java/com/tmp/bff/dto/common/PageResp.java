package com.tmp.bff.dto.common;

import com.tmp.bff.util.RespUtil;
import lombok.Data;

/**
 * 用于分页的响应类
 *
 * 此类包装了返回的data, 返回的是一个{@link PageData}类。关于cmd模型的约定，
 * 与{@link Resp}一致
 *
 * 也可以用{@link RespUtil}来构造这个类。
 *
 * @author jiangyxiong
 */
@Data
public class PageResp<T> extends Resp<PageData<T>> {
}
