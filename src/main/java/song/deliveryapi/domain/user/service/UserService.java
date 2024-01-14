package song.deliveryapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.deliveryapi.common.config.security.jwt.JwtFilter;
import song.deliveryapi.common.config.security.jwt.TokenProvider;
import song.deliveryapi.domain.auth.dto.TokenRequestDto;
import song.deliveryapi.domain.auth.dto.TokenResponseDto;
import song.deliveryapi.domain.user.User;
import song.deliveryapi.domain.user.UserMapper;
import song.deliveryapi.domain.user.UserRepository;
import song.deliveryapi.domain.user.dto.request.UserJoinRequestDto;
import song.deliveryapi.domain.user.dto.request.UserLoginRequestDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    // Token 관련
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    // 회원가입
    @Transactional
    public Long join(UserJoinRequestDto requestDto) {
        // 사용자 가입 여부 확인

        Optional<User> user = userRepository.findByEmail(requestDto.getEmail());
        if (user.isPresent()) {
            throw new IllegalArgumentException("Test");
        }

        // 회원가입 진행
        String encodePassword =  passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(encodePassword);

        // DTO -> Entity
        User userEntity = userMapper.joinDtoToEntity(requestDto);

        // save
        User savedUser = userRepository.save(userEntity);
        return savedUser.getId();
    }

    // 로그인
    @Transactional
    public TokenResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        // 입력한 값을 기준으로 인증 후 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        // Header 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);


        return new TokenResponseDto(token);
    }

    // Token 값 유효성 검사
    @Transactional
    public void check(TokenRequestDto requestDto) {
        Authentication authentication = tokenProvider.getAuthentication(requestDto.getToken());
        log.info("authentication : {}", authentication);
    }


}
