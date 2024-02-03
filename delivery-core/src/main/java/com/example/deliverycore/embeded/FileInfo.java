package com.example.deliverycore.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
public class FileInfo {
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

    public FileInfo() {
    }

    public FileInfo(String originFileName, String fileName, String filePath, String fileType, Long fileSize) {
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
        FileInfo that = (FileInfo) o;
        return Objects.equals(originFileName, that.originFileName) && Objects.equals(fileName, that.fileName) && Objects.equals(filePath, that.filePath) && Objects.equals(fileType, that.fileType) && Objects.equals(fileSize, that.fileSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originFileName, fileName, filePath, fileType, fileSize);
    }
}
