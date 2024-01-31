package com.example.deliveryadmin.domain.product.repository;

import com.example.deliveryadmin.domain.product.ProductDto;

import java.util.List;

public interface ProductRepositoryCustom {
    /**
     * 메뉴 등록 - 중복 검사
     * 조건 : storeId, product name
     */
    boolean isExistName(Long storeId, String productName, Long productId);



    // == 매장 내 메뉴 로직 처리 == //

    /**
     * 매장 내 전체 메뉴 리스트
     */
    List<ProductDto.ListData> getAllProducts(Long storeId);

    /**
     * 매장에 등록된 메뉴 상세 데이터
     */
    ProductDto.DetailInfo getProduct(Long productId);
}
