package com.example.deliveryadmin.domain.admin;

import com.example.deliverycore.entity.Admin;
import com.example.deliverycore.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);


    Admin joinDtoToAdmin(AdminDto.RequestJoinDto requestJoinDto);
}
