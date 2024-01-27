package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;
import com.example.deliveryadmin.common.fileupload.AttachmentFile;
import com.example.deliveryadmin.common.entity.BaseEntity;
import com.example.deliveryadmin.common.enums.StoreCategory;
import com.example.deliveryadmin.common.enums.StoreStatus;
import com.example.deliveryadmin.domain.member.Member;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", insertable = false, updatable = false)
    private Long id;

    // 소유주
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id",nullable = false, updatable = false)
    private Member member;

    @Column(nullable = false)
    private String name;

    @Lob
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private StoreStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private StoreCategory category;

    @Embedded
    private Address address;

    @Embedded
    private OpeningHours openingHours;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AttachmentFile> thumbnails = new ArrayList<>();

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isDel;

    public Store() {

    }

    @Builder(toBuilder = true)
    @QueryProjection
    public Store(Long id, Member member, String name, String description, StoreStatus status, StoreCategory category, Address address, OpeningHours openingHours, List<AttachmentFile> thumbnails, boolean isDel) {
        this.id = id;
        this.member = member;
        this.name = name;
        this.description = description;
        this.status = status;
        this.category = category;
        this.address = address;
        this.openingHours = openingHours;
        this.thumbnails = thumbnails;
        this.isDel = isDel;
    }



    @Builder
    public void setMember(Member member) {
        this.member = member;
    }

    public Member member() {
        return member;
    }


}
