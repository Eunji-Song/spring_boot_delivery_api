package song.deliveryapi.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinRequestDto {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    @Override
    public String toString() {
        return "UserJoinRequestDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
