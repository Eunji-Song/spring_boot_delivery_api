package com.example.deliveryadmin.common.fileupload.dto;

import com.example.deliverycore.embeded.FileInfo;
import lombok.*;

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
    public AttachmentFileDto(FileInfo fileInfo) {
        this.originFileName = fileInfo.getOriginFileName();
        this.fileName = fileInfo.getFileName();
        this.filePath= fileInfo.getFilePath();
        this.fileType = fileInfo.getFileType();
        this.fileSize = fileInfo.getFileSize();
    }

}
