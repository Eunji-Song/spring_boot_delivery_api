package com.example.deliveryuser.domain.member;

import com.example.deliverycore.entity.Admin;
import com.example.deliverycore.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);


    Member joinDtoToMember(MemberDto.RequestJoinDto requestJoinDto);
}
