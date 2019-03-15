package com.langlang.health.common.entity;

import lombok.Data;

/**
 * Created by tyj on 2019/01/07.
 */

@Data
public class RestResponseBo<T> {
    /**
     * 服务器响应数据
     */
    private T data;
    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String message = "";

    /**
     * 状态码
     */
    private int code = -1;

    /**
     * 服务器响应时间
     */
    private long timestamp;

    public RestResponseBo() {
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public RestResponseBo(boolean success) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
    }

    public RestResponseBo(boolean success, T data) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.data = data;
    }

    public RestResponseBo(boolean success, T data, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.data = data;
        this.code = code;
    }

    public RestResponseBo(boolean success, String message) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.message = message;
    }

    public RestResponseBo(boolean success, String message, int code) {
        this.timestamp = System.currentTimeMillis() / 1000;
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public static RestResponseBo<Object> ok() {
        return new RestResponseBo<Object>(true);
    }

    public static <T> RestResponseBo<T> ok(T data) {
        return new RestResponseBo<T>(true, data);
    }

    public static <T> RestResponseBo<Object> ok(int code) {
        return new RestResponseBo<Object>(true, null, code);
    }

    public static <T> RestResponseBo<T> ok(T data, int code) {
        return new RestResponseBo<T>(true, data, code);
    }

    public static RestResponseBo<Object> fail() {
        return new RestResponseBo<Object>(false);
    }

    public static RestResponseBo<Object> fail(String message) {
        return new RestResponseBo<Object>(false, message);
    }

    public static RestResponseBo<Object> fail(int code) {
        return new RestResponseBo<Object>(false, null, code);
    }

    public static RestResponseBo<Object> fail(int code, String message) {
        return new RestResponseBo<Object>(false, message, code);
    }

}
