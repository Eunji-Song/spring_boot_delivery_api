package com.example.deliveryuser.domain.product.repository;


import com.example.deliverycore.entity.Product;
import com.example.deliveryuser.domain.product.ProductDto;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductDto.ListData> getAllProducts(Long storeId);

    ProductDto.DetailInfo getProduct(Long storeId, Long productId);

    Product getProductPrice(Long storeId, Long productId);
}
