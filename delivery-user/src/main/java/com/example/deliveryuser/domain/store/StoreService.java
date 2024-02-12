package com.example.deliveryuser.domain.store;

import com.example.deliverycore.enums.StoreCategory;
import com.example.deliverycore.enums.StoreStatus;
import com.example.deliveryuser.common.exception.store.StoreNotFoundException;
import com.example.deliveryuser.domain.product.ProductDto;
import com.example.deliveryuser.domain.product.ProductService;
import com.example.deliveryuser.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public List<StoreDto.ListViewData> getAllStores(StoreCategory storeCategory, String name) {
        log.info("[StoreService:getAllStores] 매장 목록 조회");

        return storeRepository.findAllStores(storeCategory, name);
    }

    public StoreDto.DetailViewData getStoreById(Long storeId) {
        StoreDto.DetailViewData detailViewData = storeRepository.findStoreById(storeId);
        if (detailViewData == null) {
            throw new StoreNotFoundException(storeId);
        }
        return detailViewData;
    }

    // === 매장 내 메뉴  === //
    private final ProductService productService;

    public List<ProductDto.ListData> getAllProducts(Long storeId) {
        log.info("[StoreService:getAllProducts]");
        return productService.getAllProducts(storeId);
    }

    public ProductDto.DetailInfo getProduct(Long storeId, Long productId) {
        return productService.getProduct(storeId, productId);
    }

}
