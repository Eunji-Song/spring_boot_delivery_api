package com.example.deliveryadmin.domain.member.repository;

import com.example.deliverycore.entity.Member;
import com.example.deliverycore.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Boolean existsByAccountId(String accountId) {
        QMember member = new QMember("m");
        boolean isExsist = jpaQueryFactory
                .selectOne()
                .from(member)
                .where(
                    member.accountId.eq(accountId)
                    , member.isWithdrawal.eq(false)
                )
                .fetchOne() != null;

        return isExsist;
    }

    @Override
    public Member findOneByAccountId(String accountId) {
        QMember member = new QMember("m");

        Member findMember = jpaQueryFactory
                .select(member)
                .from(member)
                .where(
                    member.accountId.eq(accountId)
                    , member.isWithdrawal.eq(false)
                )
                .fetchOne();
        return findMember;
    }

    @Override
    public void withdrawal(Long memberId) {
        QMember member = new QMember("m");

        jpaQueryFactory
                .update(member)
                .set(member.isWithdrawal, true)
                .set(member.withdrawalDate, LocalDateTime.now())
                .where(member.id.eq(memberId))
                .execute();
    }
}
