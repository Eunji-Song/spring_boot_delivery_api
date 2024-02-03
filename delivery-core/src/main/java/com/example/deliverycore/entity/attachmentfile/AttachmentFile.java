package com.example.deliverycore.entity.attachmentfile;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AttachmentFile {
    @Column(nullable = false)
    @NotNull
    private String originFileName;

    @Column(nullable = false)
    @NotNull
    private String fileName;

    @Column(nullable = false)
    @NotNull
    private String filePath;

    @Column(nullable = false)
    @NotNull
    private String fileType;

    @Column(nullable = false)
    @NotNull
    private Long fileSize;

    public AttachmentFile(String originFileName, String fileName, String filePath, String fileType, Long fileSize) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
