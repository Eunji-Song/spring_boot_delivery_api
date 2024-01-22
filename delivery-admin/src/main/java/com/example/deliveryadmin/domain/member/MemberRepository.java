package com.example.deliveryadmin.domain.member;

import com.example.deliveryadmin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findOneByAccountId(String accountId);
}

