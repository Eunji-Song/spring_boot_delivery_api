package com.example.deliveryadmin.domain.auth.repository;

import com.example.deliverycore.entity.Admin;

public interface AuthRepositoryCustom  {
    Admin findOneByAccountId(String accountId);
}
