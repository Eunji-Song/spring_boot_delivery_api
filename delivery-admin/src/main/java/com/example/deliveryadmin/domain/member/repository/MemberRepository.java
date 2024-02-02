package com.example.deliveryadmin.domain.member.repository;

import com.example.deliverycore.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findById(Long id);
}

