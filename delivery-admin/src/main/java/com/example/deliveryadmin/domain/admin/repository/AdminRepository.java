package com.example.deliveryadmin.domain.admin.repository;

import com.example.deliverycore.entity.Admin;
import com.example.deliverycore.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {
    Optional<Admin> findById(Long id);
}

