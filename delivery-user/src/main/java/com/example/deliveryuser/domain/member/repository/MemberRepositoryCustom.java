package com.example.deliveryuser.domain.member.repository;

import com.example.deliverycore.entity.Admin;


public interface MemberRepositoryCustom {
    Boolean existsByAccountId(String accountId);

    Admin findOneByAccountId(String accountId);

    void withdrawal(Long memberId);
}
