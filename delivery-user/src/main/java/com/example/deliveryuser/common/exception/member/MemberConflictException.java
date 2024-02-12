package com.example.deliveryuser.common.exception.member;

import com.example.deliveryuser.common.exception.ConflictException;

public class MemberConflictException extends ConflictException {
    public MemberConflictException(String accountId) {
        super(accountId + " 는 이미 존재하는 계정입니다.");
    }
}
