package com.tmp.bff.dto.common;

import com.tmp.bff.util.RespUtil;
import lombok.Data;

/**
 * 基础的响应类，应用服务接口应该都返回这个类型。
 * <p>
 * 此类型参考了常用的CMD模型，包含如下字段：
 * <p>
 * success:用于便捷的判断操作是否成功。
 * <p>
 * status:自定义的响应码，此字段不是必须的，但是如果用于区分不同的业务响应，则可以由业务项目自行填入。
 * 业务项目也需要提供一个对应的枚举（常量），方便依赖者解析响应码的含义。作为约定，操作成功的响应码一般都为0。
 * <p>
 * msg:当前操作的响应信息，一般在操作失败时填入这个字段，用于返回自定义错误信息。
 * <p>
 * data:当前响应的额外返回内容，一般在操作成功时会填入这个字段。
 * <p>
 * 在使用时，可以直接构造这个类，也可以通过{@link RespUtil}提供的辅助方法来构造。
 *
 * @author jiangyxiong
 */
@Data
public class Resp<T> {

    /**
     * 是否成功的标记
     */
    private boolean success;

    /**
     * 状态码，alias for code
     * <p>
     * 10000是一个约定的常量，表示操作成功
     */
    private int status;

    /**
     * 错误消息，alias for message
     */
    private String msg;

    /**
     * 响应包含的数据内容
     */
    private T data;
}
