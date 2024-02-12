package com.example.deliveryuser.common.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 실제로 인증 작업을 진행하는 필터
 * 이 필터는 검증이 끝난 JWT로부터 유저정보를 받아와서 UsernamePasswordAuthenticationFilter 로 전달
 * GenericFilterBean : 필터가 2번 실행되는 현상 발생 -> OncePerRequestFilter 로 변경
 */

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

   private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

   private TokenProvider tokenProvider;

   public static final String AUTHORIZATION_HEADER = "Authorization";




   public JwtFilter(TokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      log.info("[JwtFilter:doFilterInternal] 입력 토큰 검증");
      String accessToken = tokenProvider.resolveToken(request);
      if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
         setAuthenticationToContext(accessToken);
      }

      filterChain.doFilter(request, response);
   }

   // Context에 토큰 정보 저장
   private void setAuthenticationToContext(String accessToken) {
      Authentication authentication = tokenProvider.getAuthentication(accessToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("[JwtFilter:setAuthenticationToContext] Token 인증 성공 ");
   }

   // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
   private String resolveToken(HttpServletRequest request) {
      String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

      String accessToken = request.getHeader("Access-token");
      String refreshToken = request.getHeader("Refresh-token");

      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }

      return null;
   }

}