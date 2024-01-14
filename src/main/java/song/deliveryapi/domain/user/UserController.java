package song.deliveryapi.domain.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import song.deliveryapi.domain.auth.dto.TokenRequestDto;
import song.deliveryapi.domain.auth.dto.TokenResponseDto;
import song.deliveryapi.domain.user.dto.request.UserJoinRequestDto;
import song.deliveryapi.domain.user.dto.request.UserLoginRequestDto;
import song.deliveryapi.domain.user.dto.response.UserJoinResponseDto;
import song.deliveryapi.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    @PostMapping("/join")
    public UserJoinResponseDto join(@Valid @RequestBody UserJoinRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Err");
        }

        // 회원가입 진행
        Long id = userService.join(requestDto);

        // 성공 리턴
        return new UserJoinResponseDto(id);
    }

    // 로그인
    @PostMapping("/login")
    public TokenResponseDto login(@Valid @RequestBody UserLoginRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Err");
        }

        // Login 진행
        TokenResponseDto tokenResponseDto = userService.login(requestDto);
        return tokenResponseDto;
    }

    @GetMapping("/check")
    public void check(@Valid @RequestBody TokenRequestDto requestDto) {
        log.info("check");
        userService.check(requestDto);
    }
}
