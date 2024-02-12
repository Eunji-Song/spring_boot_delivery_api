package com.example.deliveryuser.domain.member.repository;

import com.example.deliverycore.entity.Admin;
import com.example.deliverycore.entity.QAdmin;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private QAdmin admin;


    @Override
    public Boolean existsByAccountId(String accountId) {
        admin = new QAdmin("a");
        boolean isExsist = jpaQueryFactory
                .selectOne()
                .from(admin)
                .where(
                        admin.accountId.eq(accountId)
                    , admin.isWithdrawal.eq(false)
                )
                .fetchOne() != null;

        return isExsist;
    }

    @Override
    public Admin findOneByAccountId(String accountId) {
        admin = new QAdmin("a");

        Admin findAdmin = jpaQueryFactory
                .select(admin)
                .from(admin)
                .where(
                    admin.accountId.eq(accountId)
                    , admin.isWithdrawal.eq(false)
                )
                .fetchOne();
        return findAdmin;
    }

    @Override
    public void withdrawal(Long memberId) {
        admin = new QAdmin("a");

        jpaQueryFactory
                .update(admin)
                .set(admin.isWithdrawal, true)
                .set(admin.withdrawalDate, LocalDateTime.now())
                .where(admin.id.eq(memberId))
                .execute();
    }
}
