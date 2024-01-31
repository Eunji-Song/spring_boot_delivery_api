package com.example.deliveryadmin.domain.product;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product saveDtoToEntity(ProductDto.RequestSave requestSave);

    void  updateDtoToEntity(ProductDto.RequestUpdate requestUpdate, @MappingTarget Product product);


}
