package com.example.deliveryuser.domain.store;

import com.example.deliverycore.enums.StoreCategory;
import com.example.deliveryuser.common.response.ApiResponse;
import com.example.deliveryuser.common.response.ApiResult;
import com.example.deliveryuser.domain.product.ProductDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Store - 매장 정보")
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    /**
     * 매장 목록 표출
     */
    @GetMapping("")
    public ApiResult getAllStores(
            @RequestParam(required = false) StoreCategory storeCategory
            , @RequestParam(required = false) String name) {

        return ApiResponse.success(storeService.getAllStores(storeCategory, name));
    }




    /**
     * 매장 상세 정보 표출
     */
    @GetMapping("/{storeId}")
    public ApiResult getStoreById(@PathVariable Long storeId) {
        // 유효성 검사
        if (storeId == null || storeId < 1) {
            return ApiResponse.error("올바른 값을 입력해주세요");
        }

        // 데이터 조회
        StoreDto.DetailViewData detailViewData = storeService.getStoreById(storeId);

        return ApiResponse.success(detailViewData);
    }

    // === 매장 내 메뉴  === //

    /**
     * 메뉴 전체 조회
     */
    @GetMapping("/{storeId}/products")
    public ApiResult getAllProducts(@PathVariable Long storeId) {
        // 유효성 검사
        if (storeId == null || storeId < 1) {
            return ApiResponse.error("올바른 값을 입력해주세요");
        }

        List<ProductDto.ListData> list = storeService.getAllProducts(storeId);
        return ApiResponse.success(list);
    }

    /**
     * 메뉴상세 조회
     */
    @GetMapping("/{storeId}/products/{productId}")
    public ApiResult getProduct(@PathVariable Long storeId, @PathVariable Long productId) {
        // 유효성 검사
        if (storeId == null || storeId < 1 || productId == null || productId < 1) {
            return ApiResponse.error("올바른 값을 입력해주세요");
        }

        ProductDto.DetailInfo detailInfo = storeService.getProduct(storeId, productId);
        return ApiResponse.success(detailInfo);
    }
}
