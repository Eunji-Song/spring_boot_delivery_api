package com.example.deliveryadmin.common.exception.member;

import com.example.deliveryadmin.common.exception.NotFoundException;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super("사용자 정보가 존재하지 않습니다.");
    }
}
