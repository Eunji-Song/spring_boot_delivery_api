package com.example.deliveryadmin.domain.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Member;


public class AuthDto {
    // == 요청 ==  //
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestAuthenticate {
        @NotBlank(message = "게정을 입력해 주세요.")
        private String accountId;
        @NotBlank(message = "비밀번호를 입력해 주세요.")
        private String password;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestTokenValidation {
        @NotBlank(message = "access token을 입력해주세요.")
        private String accessToken;
        @NotBlank(message = "refresh token을 입력해주세요.")
        private String refreshToken;
    }


    // == 응답 == //
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Authenticate {
        private String accessToken;
        private String refreshtoken;

        public Authenticate(String accessToken, String refreshtoken) {
            this.accessToken = accessToken;
            this.refreshtoken = refreshtoken;
        }
    }
}
