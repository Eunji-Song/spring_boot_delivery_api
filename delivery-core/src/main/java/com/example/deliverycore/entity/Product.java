package com.example.deliverycore.entity;

import com.example.deliverycore.entity.attachmentfile.ProductAttachmentFile;
import com.example.deliverycore.enums.ProductCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_del = false")
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

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

    @JoinColumn(name = "store_id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_id", referencedColumnName = "product_attachment_file_id", nullable = true)
    private ProductAttachmentFile thumbnail = null;


    @Builder(toBuilder = true)
    public Product(Long id, String name, String description, int price, boolean isBest, boolean isDel, Store store, ProductAttachmentFile thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isBest = isBest;
        this.isDel = isDel;
        this.store = store;
        this.thumbnail = thumbnail;
    }

    public Product(Long id, int price) {
        this.id = id;
        this.price = price;
    }

    public Product(Long id) {
        this.id = id;
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
