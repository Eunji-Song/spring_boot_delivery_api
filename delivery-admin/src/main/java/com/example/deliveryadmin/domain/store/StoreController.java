package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.common.response.ResultCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Store - 매장 관리")
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
@Slf4j
public class StoreController {
    private final StoreService storeService;

    /**
     * 전체 매장 목록 조회
     * 필터링 가능 : category, status, name
     * 페이징 가능
     * limit : 30
     */
    @GetMapping("")
    public ApiResult getAllStores(StoreDto.RequestSearchDto requestSearchDto) {
        return ApiResponse.success(storeService.getAllStores(requestSearchDto));
    }


    /**
     * 매장 정보 조회
     */
    @GetMapping("/{storeId}")
    public ApiResult getStoreById(@PathVariable Long storeId) {
        return ApiResponse.success(storeService.getStoreById(storeId));
    }

    /**
     * 매장 등록
     */
    @PostMapping("")
    public ApiResult save(@Valid @RequestBody StoreDto.RequestSaveDto requestSaveDto, Authentication authentication) {
        Long stroeId = storeService.save(requestSaveDto);
        return ApiResponse.success(ResultCode.SUCCESS, stroeId);
    }


    /**
     * 매장 정보 수정
     */
    @PutMapping("/{storeId}")
    public ApiResult update(@PathVariable Long storeId, @Valid @RequestBody StoreDto.RequestUpdateDto requestUpdateDto) {
        storeService.update(storeId, requestUpdateDto);
        return ApiResponse.success(ResultCode.SUCCESS, storeId);
    }

    /**
     * 매장 삭제
     */
    @DeleteMapping("/{storeId}")
    public ApiResult delete(@PathVariable Long storeId) {
        storeService.delete(storeId);
        return ApiResponse.success();
    }

}
