package com.example.deliveryadmin.common.exception;

import com.example.deliveryadmin.common.response.ResultCode;
import lombok.Getter;

// unchecked Exception
@Getter
public class ApplicationException extends RuntimeException {
    private final ResultCode resultCode;

    public ApplicationException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
