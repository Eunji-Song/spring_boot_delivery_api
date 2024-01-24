package com.example.deliveryadmin.common.exception.member;

import com.example.deliveryadmin.common.exception.ConflictException;
import com.example.deliveryadmin.common.response.ResultCode;
import lombok.Getter;

public class MemberConflictException extends ConflictException {
    public MemberConflictException(String accountId) {
        super(accountId + " 는 이미 존재하는 계정입니다.");
    }
}
