package com.example.deliveryuser.common.config.security;

import com.example.deliveryuser.common.config.jwt.JwtAuthenticationEntryPoint;
import com.example.deliveryuser.common.config.jwt.JwtCustomAccessDeniedHandler;
import com.example.deliveryuser.common.config.jwt.JwtSecurityConfig;
import com.example.deliveryuser.common.config.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableMethodSecurity

@EnableWebSecurity // 스프링 시큐리티, 웹 보안 설정 구성 활성화, 인증이 완료되지 않은 사용자는 API에 접근할 수 없어진다.
//@RequiredArgsConstructor // final 필드의 생성자를 자동으로 생성
@Configuration // 설정 클래스를 Bean에 등록하는 어노테이션

public class SecurityConfig {
    private final TokenProvider tokenProvider; // JWT 발급 및 검증
    private final CorsFilter corsFilter;

    public SecurityConfig(TokenProvider tokenProvider, CorsFilter corsFilter) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
    }

    /**
     * SecurityFilterChain => Spring Security에서 제공하는 인증, 인가를 위한 필터들의 모음
     * AbstractHttpConfigurer::disable => 기본값을 사용
     * authenticated => 인증 필수
     */
    @Bean // 스프링 컨테이너에 클래스를 등록
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security
                // UI기반의 인증차앙이 뜨는것을 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                // form 로그인 기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                // csrf 보안이 필요없으므로 비활성화 처리
                .csrf(AbstractHttpConfigurer::disable)
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Filter에서 발생한 예외 핸들링
                .exceptionHandling(
                        exception -> exception
                                // 접근 가능 권한 확인 후 접근 불가능한 요청을 했을 때 동작
                                .accessDeniedHandler(new JwtCustomAccessDeniedHandler())
                                // 인증되지 않은 유저가 요청했을 때
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                )
                // 인증 사용하는 페이지 설정
                .authorizeHttpRequests(
                        requests -> requests
                                // 로그인과 회원가입 페이지에서는 인증을 사용하지 않음
                                .requestMatchers( "/user").permitAll()
                                .requestMatchers( "/members/join", "/members/login", "/auth/**").permitAll()
                                .requestMatchers("/v3/**", "/swagger-ui/**").permitAll()
                                // 그 외 모든 요청들은 인증 요구
                                .anyRequest().authenticated()
                )

                // CORS Err 방지, cors 필터가 springSecurityFilter보다 먼저 실행
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .with(new JwtSecurityConfig(tokenProvider), customizer -> {});


        return security.build();
    }


}
