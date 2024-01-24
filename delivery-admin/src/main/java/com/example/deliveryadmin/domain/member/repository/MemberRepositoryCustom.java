package com.example.deliveryadmin.domain.member.repository;

import com.example.deliveryadmin.domain.member.Member;

// QueryDSL 로 커스텀해서 사용할 메소드 선언하는 파일
public interface MemberRepositoryCustom {
    Boolean existsByAccountId(String accountId);

    Member findOneByAccountId(String accountId);

    void withdrawal(Long memberId);
}
