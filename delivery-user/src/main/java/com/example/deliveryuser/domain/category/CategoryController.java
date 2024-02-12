package com.example.deliveryuser.domain.category;

import com.example.deliveryuser.common.response.ApiResponse;
import com.example.deliveryuser.common.response.ApiResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name = "Categories - 메뉴 카테고리")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    /**
     * 카테고리 목록 표출
     */
    @GetMapping("/")
    public ApiResult getAllCategories() {
        List<CategoryDto.ListData> list = categoryService.getAllCategories();
        return ApiResponse.success(list);
    }
}
