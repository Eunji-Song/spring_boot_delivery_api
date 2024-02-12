package com.example.deliveryuser.domain.auth.repository;

import com.example.deliverycore.entity.Member;

public interface AuthRepositoryCustom  {
    Member findOneByAccountId(String accountId);
}
