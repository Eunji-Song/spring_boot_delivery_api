package song.deliveryapi.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String addressDetail;

    @Column(columnDefinition = "CHAR(1) default 'N'")
    private String delYn;

    private String ownerName;

    private String ownerPhoneNumber;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    public Store(Long id, String name, String address, String addressDetail, String delYn, String ownerName, String ownerPhoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.addressDetail = addressDetail;
        this.delYn = delYn;
        this.ownerName = ownerName;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



    public Long getId() {
        return id;
    }
}