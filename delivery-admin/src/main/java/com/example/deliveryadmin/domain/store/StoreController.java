package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.enums.StoreCategory;
import com.example.deliveryadmin.common.enums.StoreStatus;
import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.common.response.ResultCode;
import com.example.deliveryadmin.common.util.PagingUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public ApiResult getAllStores(
            @RequestParam(required = false) StoreCategory storeCategory
            , @RequestParam(required = false) StoreStatus storeStatus
            , @RequestParam(required = false) String name
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
            , @RequestParam(defaultValue = "storeId") String sort
            , @RequestParam(defaultValue = "DESC") String dir) {

        // 페이징 정보 생성
        Pageable pageable = PagingUtil.getPageable(page, size, sort, dir);

        List<StoreDto.ListViewData> list = storeService.getAllStores(storeCategory, storeStatus, name, pageable);
        return ApiResponse.success(list);
    }


    /**
     * 매장 정보 조회
     */
    @GetMapping("/{storeId}")
    public ApiResult getStoreById(@PathVariable Long storeId) {
        StoreDto.DetailInfo storeDto = storeService.getStoreById(storeId);
        return ApiResponse.success(storeDto);
    }

    /**
     * 매장 등록
     */
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "가게 신규 등록")
    public ApiResult<Object> save(@RequestPart(name = "request") @Valid StoreDto.RequestSaveDto requestSaveDto
                                , @RequestPart(name = "thumbnail", required = false) MultipartFile thumbnail
                                , @RequestPart(name = "detailImages", required = false) MultipartFile[] detailImages
                                , Authentication authentication) {
        Long storeId = storeService.save(requestSaveDto, thumbnail, detailImages);
        return ApiResponse.success(ResultCode.SUCCESS, storeId);
    }


    /**
     * 매장 정보 수정
     */
    @PutMapping(value = "/{storeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult update(@PathVariable Long storeId
                            , @RequestPart(name = "request") @Valid StoreDto.RequestUpdateDto requestUpdateDto
                            , @RequestPart(name = "thumbnail", required = false) MultipartFile thumbnail
                            , @RequestPart(name = "detailImages", required = false) MultipartFile[] detailImages
                            , @RequestPart(name = "deleteFilesIdList",required = false) List<Integer> deleteFilesIdList) {
        storeService.update(storeId, requestUpdateDto, thumbnail, detailImages, deleteFilesIdList);

        return ApiResponse.success(ResultCode.SUCCESS);
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
