package com.example.deliveryuser.domain.cart;

import com.example.deliverycore.entity.Product;
import com.example.deliveryuser.common.exception.NotFoundException;
import com.example.deliveryuser.domain.product.repository.ProductRepository;
import com.example.deliveryuser.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartValidate {
    private final ProductRepository productRepository;
    // 매장 + 상품 유효성 검사
    public Product getProductPriceInfo(Long storeId, Long productId) {
        Product productPriceInfo = productRepository.getProductPrice(storeId, productId);
        if (productPriceInfo == null) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }
        return productPriceInfo;
    }
}
