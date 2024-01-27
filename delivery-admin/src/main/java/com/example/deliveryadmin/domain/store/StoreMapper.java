package com.example.deliveryadmin.domain.store;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

//    StoreDto.DetailInfo Sto(Store store);


    Store saveDtoToEntity(StoreDto.RequestSaveDto requestSaveDto);
}
