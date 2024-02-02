package com.example.deliverycore.entity.attachmentfile;


import com.example.deliverycore.embeded.AttachmentFile;
import com.example.deliverycore.entity.BaseEntity;
import com.example.deliverycore.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;

/**
 * Store 첨부파일 클래스 엔티티
 * 첨부파일 용도
 * - 썸네일 : 1장
 * - 상세 이미지 : N장
 */

@Entity
@Getter
@SQLDelete(sql = "UPDATE store_attachment_file SET is_del = 1 WHERE store_attachment_file_id = ?")
public class StoreAttachmentFile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_attachment_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "is_thumbnail", nullable = false)
    private boolean isThumbnail;

    @Column(name = "is_detail_image", nullable = false)
    private boolean isDetailImage;

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

    public StoreAttachmentFile() {
    }

    public StoreAttachmentFile(String originFileName, String fileName, String filePath, String fileType, Long fileSize) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }


    @Builder
    public StoreAttachmentFile(Long id, Store store, boolean isThumbnail, boolean isDetailImage, String originFileName, String fileName, String filePath, String fileType, Long fileSize, boolean isDel) {
        this.id = id;
        this.store = store;
        this.isThumbnail = isThumbnail;
        this.isDetailImage = isDetailImage;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.isDel = isDel;
    }

    // 모든 매개변수를 가지고 있는 생성자
    @Builder
    public StoreAttachmentFile(Store store, boolean isThumbnail, AttachmentFile attachmentFile) {
        this.store = store;
        this.isThumbnail = isThumbnail;
        this.isDetailImage = !isThumbnail;
        this.originFileName = attachmentFile.getOriginFileName();
        this.fileName = attachmentFile.getFileName();
        this.filePath = attachmentFile.getFilePath();
        this.fileType = attachmentFile.getFileType();
        this.fileSize = attachmentFile.getFileSize();
    }

    public StoreAttachmentFile(AttachmentFile attachmentFile, boolean isThumbnail) {
        this.originFileName = attachmentFile.getOriginFileName();
        this.fileName = attachmentFile.getFileName();
        this.filePath = attachmentFile.getFilePath();
        this.fileType = attachmentFile.getFileType();
        this.fileSize = attachmentFile.getFileSize();
        this.isThumbnail = isThumbnail;
    }


    public void setDel(boolean del) {
        isDel = del;
    }


}
