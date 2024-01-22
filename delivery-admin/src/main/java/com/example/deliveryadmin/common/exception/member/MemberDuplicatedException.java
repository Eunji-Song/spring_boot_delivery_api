package com.example.deliveryadmin.common.exception.member;


import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.common.response.ResultCode;

public class MemberDuplicatedException extends NotFoundException {
    private String message;


    public MemberDuplicatedException(ResultCode resultCode) {
        super(resultCode);
    }

    public MemberDuplicatedException(ResultCode resultCode, String message) {
        super(resultCode);
        this.message = message;
    }
}
