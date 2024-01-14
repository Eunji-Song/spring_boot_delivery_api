package song.deliveryapi.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
