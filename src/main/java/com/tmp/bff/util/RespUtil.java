package com.tmp.bff.util;

import com.tmp.bff.dto.common.PageData;
import com.tmp.bff.dto.common.PageResp;
import com.tmp.bff.dto.common.Resp;
import com.tmp.bff.dto.common.RespConstraints;

import java.util.List;

/**
 * 工具类，用于构造{@link Resp}和{@link PageResp}的实例
 *
 * @author jiangyxiong
 */
public final class RespUtil {

    private RespUtil() {

    }

    public static <T> Resp<T> success() {
        return buildResp(true, RespConstraints.STATUS_SUCCESS, "", null);
    }

    public static <T> Resp<T> success(T data) {
        return buildResp(true, RespConstraints.STATUS_SUCCESS, "", data);
    }

    public static <T> Resp<T> success(int status, T data) {
        return buildResp(true, status, "", data);
    }

    public static <T> Resp<T> failure(String msg) {
        return buildResp(false, RespConstraints.STATUS_FAILURE, msg, null);
    }

    public static <T> Resp<T> failure(int status, String msg) {
        return buildResp(false, status, msg, null);
    }

    public static <T> PageResp<T> successPage(List<T> items) {
        return buildPageResp(true, RespConstraints.STATUS_SUCCESS, "", items, 0, 0, 0);
    }

    public static <T> PageResp<T> successPage(List<T> items, int pageIndex, int pageSize, int total) {
        return buildPageResp(true, RespConstraints.STATUS_SUCCESS, "", items, pageIndex, pageSize, total);
    }

    public static <T> PageResp<T> successPage(int status, List<T> items, int pageIndex, int pageSize, int total) {
        return buildPageResp(true, status, "", items, pageIndex, pageSize, total);
    }

    public static <T> PageResp<T> failurePage(String msg) {
        return buildPageResp(false, RespConstraints.STATUS_FAILURE, msg, null, 0, 0, 0);
    }

    public static <T> PageResp<T> failurePage(int status, String msg) {
        return buildPageResp(false, status, msg, null, 0, 0, 0);
    }

    private static <T> Resp<T> buildResp(boolean success, int status, String msg, T data) {
        Resp<T> resp = new Resp<>();
        resp.setSuccess(success);
        resp.setStatus(status);
        resp.setData(data);
        resp.setMsg(msg);

        return resp;
    }

    private static <T> PageResp<T> buildPageResp(boolean success, int status, String msg, List<T> items, int pageIndex, int pageSize, int total) {
        PageResp<T> resp = new PageResp<>();
        resp.setSuccess(success);
        resp.setStatus(status);
        resp.setMsg(msg);

        PageData<T> data = new PageData<>();
        data.setPageIndex(pageIndex);
        data.setPageSize(pageSize);
        data.setTotal(total);
        data.setData(items);
        resp.setData(data);

        return resp;
    }
}

