package com.tmp.bff.dto.common;

import lombok.Data;

import java.util.List;

/**
 * 分页的响应实体类
 *
 * @author jiangyaxiong
 */
@Data
public class PageData<T> {

    private int pageIndex;

    private int pageSize;

    private int total;

    private List<T> data;
}
