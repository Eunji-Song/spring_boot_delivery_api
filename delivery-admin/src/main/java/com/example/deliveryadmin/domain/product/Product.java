package com.example.deliveryadmin.domain.product;

import com.example.deliveryadmin.common.entity.BaseEntity;
import com.example.deliveryadmin.common.enums.ProductCategory;
import com.example.deliveryadmin.common.fileupload.product.ProductAttachmentFile;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.store.Store;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_del = false")
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    ProductCategory category;

    @Column(nullable = false)
    private String name;

    @Lob
    @Size(max = 30)
    private String description;

    @Column(nullable = false)
    private int price;

    @ColumnDefault(value = "false")
    boolean isBest;

    @ColumnDefault(value = "false")
    boolean isDel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id",nullable = false, updatable = false)
    private Member member;

    @JoinColumn(name = "store_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "thumbnail_id", nullable = true)
    @JoinColumn(name = "thumbnail_id", referencedColumnName = "product_attachment_file_id", nullable = true)
    private ProductAttachmentFile thumbnail = null;


    @Builder(toBuilder = true)
    public Product(Long id, ProductCategory category, String name, String description, int price, boolean isBest, boolean isDel, Member member, Store store, ProductAttachmentFile thumbnail) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isBest = isBest;
        this.isDel = isDel;
        this.member = member;
        this.store = store;
        this.thumbnail = thumbnail;
    }


    public void setMember(Member member) {
        this.member = member;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setThumbnail(ProductAttachmentFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
