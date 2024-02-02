package com.example.deliveryadmin.domain.member.repository;

import com.example.deliverycore.entity.Member;


public interface MemberRepositoryCustom {
    Boolean existsByAccountId(String accountId);

    Member findOneByAccountId(String accountId);

    void withdrawal(Long memberId);
}
