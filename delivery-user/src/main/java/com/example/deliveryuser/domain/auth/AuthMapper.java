package com.example.deliveryuser.domain.auth;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);
//
    // Entity -> DTO
//    AuthResponseDto.CheckInfo memberEntityToCheckDto(Member member);
}
