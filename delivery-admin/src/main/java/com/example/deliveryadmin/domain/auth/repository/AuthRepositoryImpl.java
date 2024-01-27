package com.example.deliveryadmin.domain.auth.repository;

import com.example.deliveryadmin.common.exception.member.MemberNotFoundException;
import com.example.deliveryadmin.domain.auth.AuthDto;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.MemberDto;
import com.example.deliveryadmin.domain.member.QMember;
import com.example.deliveryadmin.domain.member.QMemberDto_DetailInfo;
import com.example.deliveryadmin.domain.store.QStoreDto_DetailInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class AuthRepositoryImpl implements AuthRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Member findOneByAccountId(String accountId)  {
        QMember member = new QMember("m");

        return jpaQueryFactory.select(member).from(member).where(member.accountId.eq(accountId), member.isWithdrawal.isFalse()).fetchOne();
    }

    /**
     * 계정 존애 여부 확인
     * fetchOne : NonUniqueResultException 발생, 단일 결과가 필요한 경우 사용
     * fetchFirst : null 리턴, 여러개 중 하나만 필요한 경우 사용
     */
    @Override
    public boolean isExistsMember(String accountId, String password) {
        QMember member = new QMember("m");

        return jpaQueryFactory.selectOne()
                .from(member)
                .where(
                        member.accountId.eq(accountId)
                        , member.password.eq(password)
                        , member.isWithdrawal.isFalse()
                )
                .fetchFirst() != null;
    }
}
