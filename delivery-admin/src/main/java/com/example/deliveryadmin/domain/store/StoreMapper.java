package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.domain.auth.AuthMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", builder = @Builder(buildMethod = "build"))
public interface StoreMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    StoreDto.DetailInfo storeToDetailDto(Store store);


    Store saveDtoToEntity(StoreDto.RequestSaveDto requestSaveDto);
}
