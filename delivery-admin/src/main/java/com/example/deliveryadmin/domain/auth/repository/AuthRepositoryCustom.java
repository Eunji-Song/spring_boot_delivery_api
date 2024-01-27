package com.example.deliveryadmin.domain.auth.repository;

import com.example.deliveryadmin.domain.auth.AuthDto;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.MemberDto;

import java.util.Optional;

public interface AuthRepositoryCustom  {
    boolean isExistsMember(String accountId, String password);

    Member findOneByAccountId(String accountId);
}
