package com.example.deliveryadmin.domain.auth.repository;

import com.example.deliverycore.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthRepository extends JpaRepository<Admin, Long>, AuthRepositoryCustom {
}

