package com.example.deliveryuser.domain.category;

import com.example.deliverycore.entity.Category;
import com.example.deliveryuser.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 목록
     */
    public List<CategoryDto.ListData> getAllCategories() {
        log.info("[CategoryService:getAllCategories] 전체 카테고리 목록 조회");

        // DB에서 데이터 조회
        List<Category> entityList = categoryRepository.findAll();

        // Entity -> Dto
        List<CategoryDto.ListData> list = categoryMapper.toDtoList(entityList);
        return list;
    }

}
