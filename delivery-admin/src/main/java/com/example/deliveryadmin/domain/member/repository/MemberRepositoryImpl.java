package com.example.deliveryadmin.domain.member.repository;

import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.QMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

// RepositoryCustom interface 에 선언한 메소드 구현하는 파일
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
