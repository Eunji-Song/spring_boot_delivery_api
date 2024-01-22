package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;
import com.example.deliveryadmin.common.enums.StoreCategory;
import com.example.deliveryadmin.common.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    // 소유주
    @Column(name = "admin_id", nullable = false)
    private Long adminId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private Address address;

    @Embedded
    private OpeningHours openingHours;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StoreStatus status = StoreStatus.OPERATING;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    private StoreCategory category;

    @Builder
    public Store(Long id, Long adminId, String name, String description, Address address, OpeningHours openingHours, StoreStatus status, StoreCategory category) {
        this.id = id;
        this.adminId = adminId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.openingHours = openingHours;
        this.status = status;
        this.category = category;
    }
}
