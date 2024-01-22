package com.example.deliveryadmin.domain.auth;

import com.example.deliveryadmin.common.config.jwt.JwtFilter;
import com.example.deliveryadmin.common.config.jwt.TokenProvider;
import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.common.response.ResultCode;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.deliveryadmin.common.config.jwt.JwtFilter.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@Tag(name = "Auth", description = "Token 확인 및 재발급")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 토큰으로 유저 정보 확인
     */
    @GetMapping("/check")
    public ApiResult check(@AuthenticationPrincipal UserDetails userDetails) {
        String accountId = userDetails.getUsername();
        return ApiResponse.success(ResultCode.SUCCESS, authService.getUserInfo(accountId));
    }

    /**
     * 토큰 재발급
     */
    @PatchMapping("/reissue")
    public void reissue(HttpServletRequest request,
                        HttpServletResponse response) {
        log.info("reissue");

        String accessToken = request.getHeader("Access-token");
        String refreshToken = request.getHeader("Refresh-token");

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        Authentication authentication = tokenProvider.getAuthentication(bearerToken);


        // 인증 정보 get

        // refresh Token 생성


    }

}
