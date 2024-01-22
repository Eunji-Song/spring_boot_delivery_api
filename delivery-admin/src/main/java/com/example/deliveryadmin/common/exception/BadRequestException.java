package com.example.deliveryadmin.common.exception;

import com.example.deliveryadmin.common.response.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private final ResultCode resultCode;

    public BadRequestException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
