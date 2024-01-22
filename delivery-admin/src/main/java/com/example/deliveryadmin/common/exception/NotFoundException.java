package com.example.deliveryadmin.common.exception;

import com.example.deliveryadmin.common.response.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    private final ResultCode resultCode;

    public NotFoundException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
