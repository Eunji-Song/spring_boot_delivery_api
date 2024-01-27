package com.example.deliveryadmin.common.fileupload;

import com.example.deliveryadmin.common.entity.BaseEntity;
import com.example.deliveryadmin.domain.product.Product;
import com.example.deliveryadmin.domain.review.Review;
import com.example.deliveryadmin.domain.store.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(uniqueConstraints = {})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @ColumnDefault(value = "false")
    private boolean isDel = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder(toBuilder = true)
    public AttachmentFile(Long id, String originFileName, String fileName, String filePath, String fileType, Long fileSize, boolean isDel, Store store, Review review, Product product) {
        this.id = id;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.isDel = isDel;
        this.store = store;
        this.review = review;
        this.product = product;
    }

    @Builder
    public AttachmentFile(Long id, String originFileName, String fileName, String filePath, String fileType, Long fileSize, boolean isDel, Store store) {
        this.id = id;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.isDel = isDel;
        this.store = store;
    }
}
