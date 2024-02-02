package com.example.deliveryadmin.domain.product;

import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.domain.store.StoreDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "product - 메뉴 관리")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * 메뉴 신규 등록
     */
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "메뉴 신규 등록")
    public ApiResult save(@RequestPart(name = "request") @Valid ProductDto.RequestSave requestSave
                        , @RequestPart(name = "thumbnail", required = false) MultipartFile thumbnailFile) {
        Long productId = productService.save(requestSave, thumbnailFile);

        return ApiResponse.success(productId);
    }

    /**
     * 메뉴 수정
     */
    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult update(@PathVariable Long productId
                        , @RequestPart(name = "request") @Valid ProductDto.RequestUpdate requestUpdate
                        , @RequestPart(name = "thumbnail", required = false) MultipartFile thumbnailFile) {
        productService.update(productId, requestUpdate, thumbnailFile);
        return ApiResponse.success();
    }

    /**
     * 메뉴 삭제
     */
    @DeleteMapping("/{productId}")
    public ApiResult delete(@PathVariable Long productId) {
        productService.delete(productId);
        return ApiResponse.success();
    }
}
