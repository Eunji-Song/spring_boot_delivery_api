package song.deliveryapi.domain.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import song.deliveryapi.domain.user.dto.request.UserJoinRequestDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User joinDtoToEntity(UserJoinRequestDto requestDto);
}
