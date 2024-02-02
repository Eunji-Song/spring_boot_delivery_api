package com.example.deliveryadmin.common.exception.store;

public class StoreConflictException extends RuntimeException {
    public StoreConflictException(String name) {
        super("이미 등록된 매장명 입니다. : " + name);
    }
}
