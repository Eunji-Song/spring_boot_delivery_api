package com.example.deliveryadmin.domain.auth.repository;

import com.example.deliveryadmin.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<Member, Long>, AuthRepositoryCustom {



}

