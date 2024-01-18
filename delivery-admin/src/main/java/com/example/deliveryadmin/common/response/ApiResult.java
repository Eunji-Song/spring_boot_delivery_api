package com.example.deliveryadmin.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API Response format
 */
@Getter
@AllArgsConstructor
public class ApiResult<T> {
    private final Integer code; // ResultCode.code
    private final String message; // ResultCode.message or custom message
    private final HttpStatus httpStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data; // return data

    public ApiResult(int httpStatusCode, String message) {
        HttpStatus status = HttpStatus.valueOf(httpStatusCode);
        this.code = httpStatusCode;
        this.message = message;
        this.httpStatus = status;
        this.data = null;
    }

    public ApiResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.httpStatus = resultCode.getHttpStatus();
        this.data = null;
    }

    public ApiResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.httpStatus = resultCode.getHttpStatus();
        this.data = data;
    }

    public ApiResult(ResultCode resultCode, String message) {
        this.code = resultCode.getCode();
        this.httpStatus = resultCode.getHttpStatus();
        this.message = message;
        this.data = null;
    }

}

