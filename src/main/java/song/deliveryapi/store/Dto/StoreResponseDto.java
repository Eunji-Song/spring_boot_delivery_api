package song.deliveryapi.store.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class StoreResponseDto {
    private Long id;

    private String name;

    private String address;

    private String addressDetail;

    private String delYn;

    private String ownerName;

    private String ownerPhoneNumber;

    private LocalDateTime createdAt;


    public StoreResponseDto() {

    }

    public StoreResponseDto(Long id, String name, String address, String addressDetail, String delYn, String ownerName, String ownerPhoneNumber, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.addressDetail = addressDetail;
        this.delYn = delYn;
        this.ownerName = ownerName;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
}
