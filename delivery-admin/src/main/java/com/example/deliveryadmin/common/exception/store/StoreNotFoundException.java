package com.example.deliveryadmin.common.exception.store;

import com.example.deliveryadmin.common.exception.NotFoundException;

public class StoreNotFoundException extends NotFoundException {
    public StoreNotFoundException(Long storeId) {
        super("해당 ID의 매장 정보가 존재하지 않습니다. ID: " + storeId);
    }
}
