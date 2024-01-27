package com.example.deliveryadmin.common.config.jwt;

import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.domain.auth.service.AuthDetailService;
import com.example.deliveryadmin.domain.member.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

/**
 * JWT 발급 및 검증
 */
@Component
@Slf4j
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "auth";
    private final String secretKey;
    private Key key;
    // Token 만료 기간
    private final Long accessTokenExpirationPeriodMs;
    private final Long refreshTokenExpirationPeriodMs;
//    private final CustomMemberDetailsService customMemberDetailsService;
    private final AuthDetailService authDetailService;

    public static final String AUTHORIZATION_HEADER = "Authorization";



    public TokenProvider(@Value("${jwt.secret-key}") String secretKey
                        , @Value("${jwt.access-token-expiration-period-ms}") Long tokenValidityInMs
                        , @Value("${jwt.refresh-token-expiration-period-ms}") Long refreshTokenValidInMs
    , AuthDetailService authDetailService) {
        this.secretKey = secretKey;
        this.accessTokenExpirationPeriodMs = tokenValidityInMs;
        this.refreshTokenExpirationPeriodMs = refreshTokenValidInMs;
        this.authDetailService = authDetailService;
    }

    // 빈이 생성이 되고 의존성 주입 받은 secretKey 값을 Decode해서 key변수에 할당
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[TokenProvider] init");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Access token 발급하기
     * 기간 : 1분
     */
    public String createAccessToken(Authentication authentication) {
        return createToken(authentication, this.accessTokenExpirationPeriodMs);
    }


    /**
     * Refresh token 발급하기
     * 기간 : 3분
     */
    public String createRefreshToken(Authentication authentication) {
        return createToken(authentication, this.refreshTokenExpirationPeriodMs);
    }

    /**
     * Authentication 객체의 권한 정보를 이용해서 토큰을 생성
     * @param expirationPeriodMs : 토큰 만료 기간
     */
    public String createToken(Authentication authentication, Long expirationPeriodMs) {
        Member member = (Member) authentication.getPrincipal();

        // 토큰 만료 기간 생성
        long now = (new Date()).getTime();
        Date validity = new Date(now + expirationPeriodMs);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("admin_id", member.getId())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    /**
     * Token에 담겨있는 정보를 이용해 Authentication 객체를 리턴
     * claims : Payload 부분에 들어가는 정보의 한 조각
     */
    public Authentication getAuthentication(String token) {
        log.info("getAuthentication");
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            UserDetails userDetails = authDetailService.loadUserByUsername(claims.getSubject());
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, token, null);

            if (auth == null) {
                log.info("auth null");
            }
            return  auth;

        }catch (Exception e) {
            log.info("exception message: {}", e.getMessage());
            throw new NotFoundException("!");
        }

    }

    /**
     * 토큰의 유효성 검증, 토큰을 파싱하여 exception 캐치
     */
    public boolean validateToken(String token) {
        log.info("=== validateToken ===");
        try {
            log.info("token : {}", token);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("validate token result : true");
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        String accessToken = request.getHeader("Access-token");
        String refreshToken = request.getHeader("Refresh-token");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }



}
