package com.example.deliveryadmin.common.fileupload.product;

import com.example.deliveryadmin.common.fileupload.attachment.AttachmentFile;
import com.example.deliveryadmin.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;

/**
 * Product 첨부파일 클래스 엔티티
 *  첨부파일 용도
 *  - 썸네일 겸 상세 이미지 - 1장만 노출 가능
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE product_attachment_file SET is_del = 1 WHERE product_attachment_file_id = ?")
public class ProductAttachmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_attachment_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

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

    public ProductAttachmentFile(AttachmentFile attachmentFile) {
        this.originFileName = attachmentFile.getOriginFileName();
        this.fileName = attachmentFile.getFileName();
        this.filePath = attachmentFile.getFilePath();
        this.fileType = attachmentFile.getFileType();
        this.fileSize = attachmentFile.getFileSize();
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
