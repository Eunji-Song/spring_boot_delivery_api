package com.example.deliveryadmin.domain.member.repository;

import com.example.deliveryadmin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepositoryInterface extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
}
