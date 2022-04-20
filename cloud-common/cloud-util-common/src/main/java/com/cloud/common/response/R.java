package com.cloud.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2020/6/29
 * @Author WTX
 * @Version
 **/
@Data
public class R<T> implements Serializable {
    /**
     * 返回代码
     */
    private int status;
    /**
     * 返回描述
     */
    private String message;

    private T data;

    public R() {
        super();
    }

    public R(int status, String message, T data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> R<T> newInstance() {
        return new R<T>();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BaseResponse [status=");
        builder.append(status);
        builder.append(", message=");
        builder.append(message);
        builder.append(", data=");
        builder.append(data);
        builder.append("]");
        return builder.toString();
    }
}
