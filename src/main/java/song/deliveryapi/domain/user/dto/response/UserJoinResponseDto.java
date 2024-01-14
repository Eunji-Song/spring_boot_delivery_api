package song.deliveryapi.domain.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserJoinResponseDto {
    private final Long id;

    public UserJoinResponseDto(Long id) {
        this.id = id;
    }
}
