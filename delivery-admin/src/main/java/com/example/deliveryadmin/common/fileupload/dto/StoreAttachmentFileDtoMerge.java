package com.example.deliveryadmin.common.fileupload.dto;

import com.example.deliveryadmin.common.fileupload.entity.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.entity.StoreAttachmentFile;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 매장 상세 조회 > thumbnail 영역 합치기
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreAttachmentFileDtoMerge {
    // store_attachment_file
    private Long id;

    // attachment_file
    private String originFileName;
    private String fileName;
    private String filePath;

    @QueryProjection
    public StoreAttachmentFileDtoMerge(Long id, AttachmentFileDto attachmentFile) {
        this.id = id;
        this.originFileName = attachmentFile.getOriginFileName();
        this.fileName = attachmentFile.getFileName();
        this.filePath = attachmentFile.getFilePath();
    }

    @QueryProjection
    public StoreAttachmentFileDtoMerge(StoreAttachmentFile storeAttachmentFile, AttachmentFile attachmentFile) {
        this.id = storeAttachmentFile.getId();
        this.originFileName = attachmentFile.getOriginFileName();
        this.fileName = attachmentFile.getFileName();
        this.filePath = attachmentFile.getFilePath();
    }

}
