package com.example.deliveryuser.common.attachment.dto;

import com.example.deliverycore.embeded.FileInfo;
import com.example.deliverycore.entity.Product;
import com.example.deliverycore.entity.attachmentfile.ProductAttachmentFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Product 첨부파일 클래스 엔티티
 *  첨부파일 용도
 *  - 썸네일 겸 상세 이미지 - 1장만 노출 가능
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAttachmentFileDto {
    private Long id;

    @JsonIgnore
    private Product product;

    FileInfo fileInfo;

    @JsonIgnore
    private boolean isDel;

    public ProductAttachmentFileDto(ProductAttachmentFile productAttachmentFile) {
        this.id = productAttachmentFile.getId();
        this.fileInfo = productAttachmentFile.getFileInfo();
    }
}
