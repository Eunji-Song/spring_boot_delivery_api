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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAttachmentFileDto {
    private Long id;

    private Product product;

    private String originFileName;

    private String fileName;

    private String filePath;

    private String fileType;

    private Long fileSize;

    private boolean isDel = false;

    public ProductAttachmentFileDto(ProductAttachmentFile productAttachmentFile) {
        this.originFileName = productAttachmentFile.getOriginFileName();
        this.fileName = productAttachmentFile.getFileName();
        this.filePath = productAttachmentFile.getFilePath();
        this.fileType = productAttachmentFile.getFileType();
        this.fileSize = productAttachmentFile.getFileSize();
    }
}
