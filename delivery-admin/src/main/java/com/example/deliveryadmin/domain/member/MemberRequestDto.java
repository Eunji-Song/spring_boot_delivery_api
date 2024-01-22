package com.example.deliveryadmin.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Service;


public class MemberRequestDto {
    @Getter
    @RequiredArgsConstructor
    public static class Join {
        @NotBlank(message = "사용하실 계정의 id를 입력해주세요.")
        private String accountId;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;




        // 암호화 비밀번호 할당
        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "Join{" +
                    "accountId='" + accountId + '\'' +
                    ", name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }

    }

    @Getter
    @RequiredArgsConstructor
    public static class Login {
        @NotBlank(message = "id를 입력해주세요.")
        private String accountId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;

        @Override
        public String toString() {
            return "Login{" +
                    "accountId='" + accountId + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
