package com.example.deliveryadmin.domain.auth.repository;

import com.example.deliverycore.entity.Member;

public interface AuthRepositoryCustom  {
    Member findOneByAccountId(String accountId);
}
