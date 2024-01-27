package com.example.deliveryadmin.common.fileupload.dto;

import com.example.deliveryadmin.common.fileupload.entity.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.entity.StoreAttachmentFile;
import com.example.deliveryadmin.domain.product.Product;
import com.example.deliveryadmin.domain.review.Review;
import com.example.deliveryadmin.domain.store.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AttachmentFileDto {
    private String originFileName;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;


    public AttachmentFileDto() {
    }

    @Builder(toBuilder = true)
    public AttachmentFileDto(String originFileName, String fileName, String filePath, String fileType, Long fileSize) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    // 매개변수를 엔티티로 받은 경우 DTO로 변환하여 리턴
    @Builder
    public AttachmentFileDto(AttachmentFile attachmentFile) {
        this.originFileName = attachmentFile.getOriginFileName();
        this.fileName = attachmentFile.getFileName();
        this.filePath= attachmentFile.getFilePath();
        this.fileType = attachmentFile.getFileType();
        this.fileSize = attachmentFile.getFileSize();
    }

}
