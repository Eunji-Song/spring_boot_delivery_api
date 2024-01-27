package com.example.deliveryadmin.common.fileupload.entity;


import com.example.deliveryadmin.domain.store.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

/**
 * Store 첨부파일 클래스 엔티티
 * 첨부파일 용도
 * - 썸네일 : 1장
 * - 상세 이미지 : N장
 */

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreAttachmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_attachment_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_file_id", nullable = false)
    private AttachmentFile attachmentFile;

    @Column(name = "is_thumbnail", nullable = false)
    private boolean isThumbnail;

    @Column(name = "is_detail_image", nullable = false)
    private boolean isDetailImage;

    @Column(name = "is_del", nullable = false)
    @ColumnDefault("false")
    private boolean isDel;


    // 모든 매개변수를 가지고 있는 생성자
    @Builder
    public StoreAttachmentFile(Long id, Store store, AttachmentFile attachmentFile, boolean isThumbnail, boolean isDetailImage) {
        this.id = id;
        this.store = store;
        this.attachmentFile = attachmentFile;
        this.isThumbnail = isThumbnail;
        this.isDetailImage = isDetailImage;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
