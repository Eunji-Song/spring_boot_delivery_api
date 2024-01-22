package com.example.deliveryadmin.common.exception.member;

import com.example.deliveryadmin.common.exception.ConflictException;
import com.example.deliveryadmin.common.response.ResultCode;
import lombok.Getter;

@Getter
public class MemberConflictException extends ConflictException {
    private String message;
    public MemberConflictException(ResultCode resultCode) {
        super(resultCode);
    }

    public MemberConflictException(ResultCode resultCode, String message) {
        super(resultCode);
        this.message = message;
    }
}
