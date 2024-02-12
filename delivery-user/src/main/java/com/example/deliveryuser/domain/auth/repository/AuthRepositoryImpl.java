package com.example.deliveryuser.domain.auth.repository;

import com.example.deliverycore.entity.Member;
import com.example.deliverycore.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor

public class AuthRepositoryImpl implements AuthRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private QMember member;

    @Override
    public Member findOneByAccountId(String accountId)  {
        member = new QMember("m");

        return jpaQueryFactory.select(member).from(member).where(member.accountId.eq(accountId), member.isWithdrawal.isFalse()).fetchOne();
    }
}
