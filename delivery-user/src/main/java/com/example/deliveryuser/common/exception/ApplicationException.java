package com.example.deliveryuser.common.exception;

import com.example.deliveryuser.common.response.ResultCode;
import lombok.Getter;

// unchecked Exception
@Getter
public class ApplicationException extends RuntimeException {
    private final ResultCode resultCode;

    public ApplicationException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
