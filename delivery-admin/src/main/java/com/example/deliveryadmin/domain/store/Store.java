package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;
import com.example.deliveryadmin.common.enums.StoreCategory;
import com.example.deliveryadmin.common.enums.StoreStatus;
import com.example.deliveryadmin.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "member")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    // 소유주
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false, updatable = false)
    private Member member;

    @Column(nullable = false)
    private String name;

    @Lob
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'OPERATING'")
    private StoreStatus status = StoreStatus.OPERATING;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private StoreCategory category;

    @Embedded
    private Address address;

    @Embedded
    private OpeningHours openingHours;


    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isDel = true;

    @Builder(toBuilder = true)
    public Store(Long id, Member member, String name, String description, StoreStatus status, StoreCategory category, Address address, OpeningHours openingHours, boolean isDel) {
        this.id = id;
        this.member = member;
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.address = address;
        this.openingHours = openingHours;
        this.isDel = isDel;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
