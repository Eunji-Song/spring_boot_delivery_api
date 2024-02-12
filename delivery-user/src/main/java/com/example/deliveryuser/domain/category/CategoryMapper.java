package com.example.deliveryuser.domain.category;

import com.example.deliverycore.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    // Entity -> Dto
    CategoryDto.ListData entityToDto(Category category);
    List<CategoryDto.ListData> toDtoList(List<Category> categoryList);

}
