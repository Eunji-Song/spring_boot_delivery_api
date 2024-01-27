package com.example.deliveryadmin.common.fileupload.dto;

import com.example.deliveryadmin.common.fileupload.entity.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.entity.StoreAttachmentFile;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreAttachmentFileDto {
    /**
     * StoreAttachmentDto + Attachment ver.
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Thumbnail {
        // store_attachment_file
        private Long id;

        // attachment_file
        private String originFileName;
        private String fileName;
        private String filePath;

        @QueryProjection
        public Thumbnail(StoreAttachmentFile storeAttachmentFile, AttachmentFile attachmentFile) {
            this.id = storeAttachmentFile.getId();
            this.originFileName = attachmentFile.getOriginFileName();
            this.fileName = attachmentFile.getFileName();
            this.filePath = attachmentFile.getFilePath();
        }
    }


    /**
     * StoreAttachmentDto + Attachment ver.
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailImages {
        // store_attachment_file
        private Long id;

        // attachment_file
        private String originFileName;
        private String fileName;
        private String filePath;
        private Long fileSize;

        @QueryProjection
        public DetailImages(StoreAttachmentFile storeAttachmentFile, AttachmentFile attachmentFile) {
            this.id = storeAttachmentFile.getId();
            this.originFileName = attachmentFile.getOriginFileName();
            this.fileName = attachmentFile.getFileName();
            this.filePath = attachmentFile.getFilePath();
            this.fileSize = attachmentFile.getFileSize();
        }
    }
}
