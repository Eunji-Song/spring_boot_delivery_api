package com.example.deliveryadmin.common.fileupload;

import com.example.deliveryadmin.domain.product.Product;
import com.example.deliveryadmin.domain.review.Review;
import com.example.deliveryadmin.domain.store.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class AttachmentFileDto {
    private String originFileName;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    @JsonIgnore
    private StoreDto.DetailInfo store;
    @JsonIgnore
    private Product product;
    @JsonIgnore
    private Review review;

    @Builder(toBuilder = true)
    public AttachmentFileDto(String originFileName, String fileName, String filePath, String fileType, Long fileSize) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public void setStore(Store store) {
        this.store = new StoreDto.DetailInfo(store);
    }

    public AttachmentFileDto() {
    }

    public static AttachmentFileDto convertAttachmentFile(AttachmentFile attachmentFile) {
        return AttachmentFileDto.builder()
                .originFileName(attachmentFile.getOriginFileName())
                .fileName(attachmentFile.getFileName())
                .filePath(attachmentFile.getFilePath())
                .fileType(attachmentFile.getFileType())
                .fileSize(attachmentFile.getFileSize())
                .build();
    }

    public static List<AttachmentFileDto> convertAttachmentFilesToDto(List<AttachmentFile> attachmentFileList) {
        return attachmentFileList.stream()
                .map(AttachmentFileDto::convertAttachmentFile)
                .collect(Collectors.toList());

    }

}
