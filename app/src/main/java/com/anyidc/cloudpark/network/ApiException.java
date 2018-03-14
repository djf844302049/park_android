package com.anyidc.cloudpark.network;

/**
 * Created by Administrator on 2018/3/14.
 */

public class ApiException extends RuntimeException {
    private int errCode;

    public ApiException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
    }
}
