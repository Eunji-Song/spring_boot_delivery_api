package com.example.deliveryadmin.common.fileupload;

import com.example.deliveryadmin.common.entity.BaseEntity;
import com.example.deliveryadmin.domain.product.Product;
import com.example.deliveryadmin.domain.review.Review;
import com.example.deliveryadmin.domain.store.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AttachmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ColumnDefault(value = "false")
    private boolean isDel = false;

    @Builder

    public AttachmentFile(Long id, String originFileName, String fileName, String filePath, String fileType, Long fileSize, Store store, Product product, Review review, boolean isDel) {
        this.id = id;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.store = store;
        this.product = product;
        this.review = review;
        this.isDel = isDel;
    }
}
