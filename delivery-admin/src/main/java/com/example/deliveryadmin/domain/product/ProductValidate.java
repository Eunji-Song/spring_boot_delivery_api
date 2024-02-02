package com.example.deliveryadmin.domain.product;

import com.example.deliveryadmin.common.exception.ConflictException;
import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.domain.product.repository.ProductRepository;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductValidate {
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    /**
     * 입력값 유효성 검사
     * - 매장 내 같은 이름의 상품 등록 불가
     * - store_id는 1 이상의 값만 입력 가능
     * - price 100원 단위로 입력 가능
     */
    public void validRequestDtoInputs(Long storeId, String name, int price, Long productId) {
        log.info("[ProductService:validRequestDtoInputs] 데이터 유효성 검증 시작 product name : {}", name);

        // storeId 데이터 존재 여부 확인
        boolean isExistStore = storeRepository.isExists(storeId);
        if (!isExistStore) {
            throw new NotFoundException("매장 정보가 존재하지 않습니다. ");
        }

        // 1보다 작은 값 입력 불가
        if (storeId < 1 || storeId == null) {
            throw new IllegalArgumentException("매장 정보를 입력해 주세요.");
        }

        // 매장 내 상품명 중복 불가
        boolean isExistProduct = productRepository.isExistName(storeId, name, productId);
        if (isExistProduct) {
            throw new ConflictException(name + "은 이미 매장 내 등록된 상품명 입니다.");
        }

        // 가격는 100원 단위로 입력 가능
        if (price % 100 != 0) {
            throw new IllegalArgumentException("가격은 100원 단위로 입력이 가능합니다.");
        }
    }

}
