package com.example.deliverycore.entity.attachmentfile;


import com.example.deliverycore.embeded.FileInfo;
import com.example.deliverycore.entity.BaseEntity;
import com.example.deliverycore.entity.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
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

    @Embedded
    FileInfo fileInfo;

    @JsonIgnore
    @ColumnDefault(value = "false")
    private boolean isDel = false;

    public StoreAttachmentFile() {
    }

    @Builder(toBuilder = true)
    public StoreAttachmentFile(Long id, Store store, boolean isThumbnail, boolean isDetailImage, FileInfo fileInfo, boolean isDel) {
        this.id = id;
        this.store = store;
        this.isThumbnail = isThumbnail;
        this.isDetailImage = isDetailImage;
        this.fileInfo = fileInfo;
        this.isDel = isDel;
    }

    public StoreAttachmentFile(FileInfo fileInfo, boolean isThumbnail) {
        this.fileInfo = fileInfo;
        this.isThumbnail = isThumbnail;
        this.isDetailImage = !isThumbnail;
    }

    public StoreAttachmentFile(Store store, boolean isThumbnail, FileInfo fileInfo) {
        this.store = store;
        this.isThumbnail = isThumbnail;
        this.fileInfo = fileInfo;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
