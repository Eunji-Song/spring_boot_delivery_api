package com.example.deliveryadmin.domain.store;

import com.example.deliverycore.entity.Store;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    Store saveDtoToEntity(StoreDto.RequestSaveDto requestSaveDto);
}
