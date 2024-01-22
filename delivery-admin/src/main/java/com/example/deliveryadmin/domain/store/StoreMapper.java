package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.domain.auth.AuthMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    StoreResponseDto.StoreDetailDTO entityToDetailDto(Store store);

    Store saveDtoToEntity(StoreRequestDto.SaveDto saveDto);
}
