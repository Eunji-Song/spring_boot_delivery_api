package song.deliveryapi.store.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StoreRequestDto {
    @NotBlank(message = "매장명을 입력해주세요.")
    private String name;

    @NotBlank(message = "매장 주소 입력해주세요.")
    private String address;

    @NotBlank(message = "매장 상세 주소를 입력해주세요.")
    private String addressDetail;

    @NotBlank(message = "소유자의 이름을 입력해주세요.")
    private String ownerName;

    @NotBlank(message = "소유자의 연락처를 입력해주세요.")
    private String ownerPhoneNumber;

}
