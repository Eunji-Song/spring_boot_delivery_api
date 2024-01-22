package com.example.deliveryadmin.domain.auth;

import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.MemberMapper;
import com.example.deliveryadmin.domain.member.MemberRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    // Entity -> DTO
    AuthResponseDto.CheckInfo memberEntityToCheckDto(Member member);
}
