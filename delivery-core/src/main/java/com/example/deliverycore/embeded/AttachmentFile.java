package com.example.deliverycore.embeded;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
public class AttachmentFile {
    private String originFileName;

    private String fileName;

    private String filePath;

    private String fileType;

    private Long fileSize;

    public AttachmentFile() {
    }

    public AttachmentFile(String originFileName, String fileName, String filePath, String fileType, Long fileSize) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentFile that = (AttachmentFile) o;
        return Objects.equals(originFileName, that.originFileName) && Objects.equals(fileName, that.fileName) && Objects.equals(filePath, that.filePath) && Objects.equals(fileType, that.fileType) && Objects.equals(fileSize, that.fileSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originFileName, fileName, filePath, fileType, fileSize);
    }
}
