package com.example.deliveryadmin.domain.auth;


import lombok.*;

public class AuthResponseDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CheckInfo {
        private String name;
        private String accountId;
        private String email;
    }
}
