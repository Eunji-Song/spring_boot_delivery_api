package com.example.deliveryadmin.domain.auth.service;

import com.example.deliveryadmin.common.config.AppConfig;
import com.example.deliveryadmin.common.config.jwt.TokenProvider;
import com.example.deliveryadmin.common.exception.member.MemberNotFoundException;
import com.example.deliveryadmin.domain.auth.AuthDto;
import com.example.deliveryadmin.domain.auth.repository.AuthRepository;
import com.example.deliveryadmin.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
//    private final AuthMapper authMapper;

    /**
     * 로그인
     * @param requestAuthenticate
     * @return
     */
    public AuthDto.Authenticate authenticateUser(AuthDto.RequestAuthenticate requestAuthenticate) {
        log.info("[AuthService:authenticateUser] 인증 요청 발생 : {}", requestAuthenticate);

        String accountId = requestAuthenticate.getAccountId();

        // 회원 존재 여부 검사
        Member member = authRepository.findOneByAccountId(accountId);
        if (member == null) {
            throw new MemberNotFoundException();
        }

        // Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountId, requestAuthenticate.getPassword());

        // id, pw 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // Token 생성
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        // DTO Return
        AuthDto.Authenticate authenticate = new AuthDto.Authenticate(accessToken, refreshToken);

        return authenticate;
    }
}
