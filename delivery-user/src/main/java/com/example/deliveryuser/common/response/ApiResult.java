package com.example.deliveryuser.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API Response format
 */
@Getter
@AllArgsConstructor
public class ApiResult<T> {
    private final Integer code; // ResultCode.code
    private final String message; // ResultCode.message or custom message
    private final T data; // return data

    public ApiResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = null;
    }

    public ApiResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public ApiResult(ResultCode resultCode, String message) {
        this.code = resultCode.getCode();
        this.message = message;
        this.data = null;
    }

}

