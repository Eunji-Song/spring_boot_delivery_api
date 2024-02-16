package com.example.deliveryadmin.domain.order;

import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.common.exception.store.StoreNotFoundException;
import com.example.deliveryadmin.domain.order.repository.OrderRepository;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import com.example.deliverycore.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    /**
     * 주문 목록
     */
    public void getOrders(Long storeId) {
        // 매장 정보 확인
        Boolean isExistStore = storeRepository.isExists(storeId);
        if (!isExistStore) {
            throw new StoreNotFoundException(storeId);
        }

        // 매장 내 주문 목록 조회

        log.info("bool : " + isExistStore);
    }

    /**
     * 주문 상세
     */

    /**
     * 주문 상태 변경
     */

    /**
     * 주문 거절(취소)
     */


}
