package com.example.deliveryadmin.domain.auth;

import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Auth - 토큰 인증", description = "Token 확인 및 재발급")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    /**
     * 사용자 계정 정보로 토큰 발급
     */
    @PostMapping("/authenticate")
    public ApiResult authenticateUser(@Valid @RequestBody AuthDto.RequestAuthenticate requestAuthenticate) {
        AuthDto.Authenticate authenticateInfo = authService.authenticateUser(requestAuthenticate);
        return ApiResponse.success(authenticateInfo);
    }

    /**
     * 발급된 토큰 유효성 검증
     */
    @PostMapping("/validation")
    public void validation(@Valid @RequestBody AuthDto.RequestTokenValidation requestTokenValidation) {

    }

    /**
     * 토큰 재발급
     */
    @PostMapping("/reissue")
    public void reissue() {

    }
}
