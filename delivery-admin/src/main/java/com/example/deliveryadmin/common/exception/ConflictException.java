package com.example.deliveryadmin.common.exception;

import com.example.deliveryadmin.common.response.ResultCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Getter
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{
    private final ResultCode resultCode;


    public ConflictException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
