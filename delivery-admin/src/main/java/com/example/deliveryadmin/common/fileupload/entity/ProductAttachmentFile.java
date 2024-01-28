package com.example.deliveryadmin.common.fileupload.entity;

import com.example.deliveryadmin.common.fileupload.attachment.AttachmentFile;
import com.example.deliveryadmin.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Product 첨부파일 클래스 엔티티
 *  첨부파일 용도
 *  - 썸네일 겸 상세 이미지 - 1장만 노출 가능
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAttachmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_attachment_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_file_id", nullable = false)
    private AttachmentFile attachmentFile;

    // 모든 매개변수를 가지고 있는 생성자
    public ProductAttachmentFile(Long id, Product product, AttachmentFile attachmentFile) {
        this.id = id;
        this.product = product;
        this.attachmentFile = attachmentFile;
    }
}
