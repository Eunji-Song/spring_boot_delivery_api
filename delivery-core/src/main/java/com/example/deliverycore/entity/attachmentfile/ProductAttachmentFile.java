package com.example.deliverycore.entity.attachmentfile;

import com.example.deliverycore.embeded.FileInfo;
import com.example.deliverycore.entity.BaseEntity;
import com.example.deliverycore.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;

/**
 * Product 첨부파일 클래스 엔티티
 *  첨부파일 용도
 *  - 썸네일 겸 상세 이미지 - 1장만 노출 가능
 */

@Entity
@Getter
@AllArgsConstructor
@SQLDelete(sql = "UPDATE product_attachment_file SET is_del = 1 WHERE product_attachment_file_id = ?")
public class ProductAttachmentFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_attachment_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Embedded
    FileInfo fileInfo;

    @JsonIgnore
    @ColumnDefault(value = "false")
    private boolean isDel = false;

    public ProductAttachmentFile() {
    }

    public ProductAttachmentFile(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
