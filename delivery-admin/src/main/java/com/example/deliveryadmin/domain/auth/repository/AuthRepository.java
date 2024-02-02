package com.example.deliveryadmin.domain.auth.repository;

import com.example.deliverycore.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthRepository extends JpaRepository<Member, Long>, AuthRepositoryCustom {
}

