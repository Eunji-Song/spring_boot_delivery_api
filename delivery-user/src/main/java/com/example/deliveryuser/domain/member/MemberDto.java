package com.example.deliveryuser.domain.member;

import com.example.deliverycore.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class MemberDto {
    // === Request(역직렬화) === //

    /**
     * 회원가입
     */
    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestJoinDto {
        @NotBlank(message = "사용하실 계정의 id를 입력해주세요.")
        private String accountId;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @Builder(toBuilder = true)
        public RequestJoinDto(String accountId, String name, String password, String email) {
            this.accountId = accountId;
            this.name = name;
            this.password = password;
            this.email = email;
        }


        public void setPassword(String password) {
            this.password = password;
        }
    }

    /**
     * 로그인
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestLoginDto {
        @NotBlank(message = "id를 입력해주세요.")
        private String accountId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
    }


    // === Response(직렬화) === //

    /**
     * Login
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Login {
        private String accessToken;
        private String refreshToken;

        @Builder
        public Login(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }

    /**
     * 상세 정보 조회
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailInfo {
        private Long id;
        private String accountId;
        private String name;
        private String email;

        @Builder(toBuilder = true)
        @QueryProjection
        public DetailInfo(Long id, String accountId, String name, String email) {
            this.id = id;
            this.accountId = accountId;
            this.name = name;
            this.email = email;
        }

        // 매개변수를 Member 엔티티로 받은 경우 DTO로 변환하여 리턴
        public DetailInfo(Member member) {
            this.id = member.getId();
            this.accountId = member.getAccountId();
            this.name = member.getName();
            this.email = member.getEmail();
        }





    }

}
