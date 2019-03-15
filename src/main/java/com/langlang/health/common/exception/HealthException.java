package com.langlang.health.common.exception;

/**
 * Created by tyj on 2019/01/08.
 */
public class HealthException extends RuntimeException {

    public HealthException() {}

    public HealthException(String message) {
        super(message);
    }

    public HealthException(String message, Throwable cause) {
        super(message, cause);
    }

    public HealthException(Throwable cause) {
        super(cause);
    }

}
