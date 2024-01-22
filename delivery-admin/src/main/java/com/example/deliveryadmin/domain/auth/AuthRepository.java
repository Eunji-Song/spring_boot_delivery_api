package com.example.deliveryadmin.domain.auth;

import com.example.deliveryadmin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<Member, Long> {
    Member findOneByAccountId(String accountId);
}
