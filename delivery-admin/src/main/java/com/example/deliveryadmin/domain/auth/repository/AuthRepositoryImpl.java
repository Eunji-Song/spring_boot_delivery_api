package com.example.deliveryadmin.domain.auth.repository;

import com.example.deliverycore.entity.Admin;
import com.example.deliverycore.entity.QAdmin;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AuthRepositoryImpl implements AuthRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private QAdmin admin;

//
    @Override
    public Admin findOneByAccountId(String accountId)  {
        admin = new QAdmin("m");
//
        return jpaQueryFactory.select(admin).from(admin).where(admin.accountId.eq(accountId), admin.isWithdrawal.isFalse()).fetchOne();
    }
}
