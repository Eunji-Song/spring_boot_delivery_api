package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.common.response.ResultCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
@Slf4j
public class StoreController {
    private final StoreService storeService;
    /**
     * 매장 목록 조회
     * - Role : Admin
     */
    public void getAllStores() {

    }


    /**
     * 매장 정보 조회
     * - Role : Admin, Owner
     */
    @GetMapping("/{storeId}")
    public void getStoreById(@PathVariable Long storeId) {

    }

    /**
     * 매장 등록
     */
    @PostMapping("")
    public ApiResult save(@Valid @RequestBody StoreRequestDto.SaveDto saveDto, Authentication authentication) {
        Long stroeId = storeService.saveStore(saveDto);
        return ApiResponse.success(ResultCode.SUCCESS, stroeId);
    }
//
//    /**
//     * 매장 정보 수정
//     */
//
//    @PutMapping("")
//    public void update() {
//
//    }
//
//    /**
//     * 매장 상태 변경
//     * : 운영, 휴업, 폐업, 오픈 준비중
//     */
//    @PutMapping("")
//    public void updateStatus() {
//
//    }
}
