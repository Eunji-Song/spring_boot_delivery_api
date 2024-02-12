package com.example.deliveryadmin.domain.admin.repository;

import com.example.deliverycore.entity.Admin;


public interface AdminRepositoryCustom {
    Boolean existsByAccountId(String accountId);

    Admin findOneByAccountId(String accountId);

    void withdrawal(Long memberId);
}
