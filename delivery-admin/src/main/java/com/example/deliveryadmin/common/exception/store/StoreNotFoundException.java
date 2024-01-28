package com.example.deliveryadmin.common.exception.store;

import com.example.deliveryadmin.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StoreNotFoundException extends NotFoundException {

    public StoreNotFoundException() {
        super("매장 데이터가 존재하지 않습니다.");
        log.error("[StoreNotFoundException:StoreNotFoundException] 매장 데이터가 존재하지 않습니다.");
    }

    public StoreNotFoundException(Long storeId) {
        super("해당 ID의 매장 정보가 존재하지 않습니다. ID: " + storeId);
        log.error("[StoreNotFoundException:StoreNotFoundException] 매장 데이터 존재하지 않음 id : {}", storeId);
    }
}
