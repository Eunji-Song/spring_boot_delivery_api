package com.example.deliveryuser.domain.product;

import com.example.deliveryuser.common.exception.NotFoundException;
import com.example.deliveryuser.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<ProductDto.ListData> getAllProducts(Long storeId) {
        log.info("[ProductService:getAllProducts]");
        List<ProductDto.ListData> list = productRepository.getAllProducts(storeId);
        return list;
    }

    public ProductDto.DetailInfo getProduct(Long storeId, Long productId) {
        log.info("[ProductService:getProduct]");
        ProductDto.DetailInfo detailInfo = productRepository.getProduct(storeId, productId);
        if (detailInfo == null) {
            throw new NotFoundException("상품 정보가 존재하지 않습니다.");
        }
        return detailInfo;
    }
}
