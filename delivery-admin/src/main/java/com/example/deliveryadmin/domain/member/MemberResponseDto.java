package com.example.deliveryadmin.domain.member;

import lombok.*;

public class MemberResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Login {
        private String accessToken;
        private String refreshToken;
    }
}
