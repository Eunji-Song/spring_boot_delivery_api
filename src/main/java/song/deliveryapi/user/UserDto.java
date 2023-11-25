package song.deliveryapi.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import song.deliveryapi.common.validation.ValidationGroups;

public class UserDto {
    @Getter
    @Setter
    @ToString
    public static class Request {
        @NotBlank(message = "사용자의 ID를 입력해주세요.", groups = ValidationGroups.NotNullGroup.class)
        private String userId;
        @NotBlank
        private String password;
        @Email
        private String email;
    }

    @Getter
    @ToString
    public static class Response {
        private String id;
        private String userId;
        private String email;
    }
}
