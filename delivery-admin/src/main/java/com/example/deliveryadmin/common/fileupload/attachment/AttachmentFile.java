package com.example.deliveryadmin.common.fileupload.attachment;

import com.example.deliveryadmin.common.entity.BaseEntity;
import com.example.deliveryadmin.domain.store.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

/**
 * 첨부파일의 기본 데이터를 포함하고 있는 테이블
 */

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachmentFile extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_file_id")
    private Long id;

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

    // 파일 정보 엔티티로 저장 시 사용
    public AttachmentFile(String originFileName, String fileName, String filePath, String fileType, Long fileSize) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
