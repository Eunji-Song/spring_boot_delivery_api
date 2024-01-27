package com.example.deliveryadmin.domain.product;

import com.example.deliveryadmin.common.entity.BaseEntity;
import com.example.deliveryadmin.common.enums.ProductCategory;
import com.example.deliveryadmin.domain.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @JoinColumn(name = "store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    ProductCategory category;

    @Column(nullable = false)
    private String name;

    @Lob
    private String description;

    @Column(nullable = false)
    private int price;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<AttachmentFile> attachmentFiles;

    @ColumnDefault(value = "false")
    boolean isBest;
}
