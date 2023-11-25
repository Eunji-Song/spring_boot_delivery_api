package song.deliveryapi.user;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import song.deliveryapi.common.exception.BadRequestException;
import song.deliveryapi.common.response.ApiResponse;
import song.deliveryapi.product.Product;
import song.deliveryapi.product.ProductDto;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // 회원가입
    @Transactional
    public ApiResponse join(UserDto.Request request) {
        // 데이터 중복 확인
        userRepository.findUserByUserId(request.getUserId()).ifPresent(data -> {
            throw new BadRequestException();
        });

        // 데이터 저장
        // DTO -> Entity
        User user = modelMapper.map(request, User.class);
        userRepository.save(user);

        // Entity -> dto
        UserDto.Response response = modelMapper.map(user, UserDto.Response.class);
        return ApiResponse.success(response);
    }
}
