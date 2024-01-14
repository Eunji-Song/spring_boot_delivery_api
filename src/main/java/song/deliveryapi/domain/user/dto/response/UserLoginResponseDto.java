package song.deliveryapi.domain.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
public class UserLoginResponseDto {
    private String accessToken;
    private String refreshToken;

}
