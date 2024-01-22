package com.example.deliveryadmin.domain.member;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);


    Member joinDtoToEntity(MemberRequestDto.Join joinDto);
}
