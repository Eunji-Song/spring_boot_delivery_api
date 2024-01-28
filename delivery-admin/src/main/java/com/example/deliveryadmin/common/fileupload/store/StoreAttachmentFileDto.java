package com.example.deliveryadmin.common.fileupload.store;

import com.example.deliveryadmin.common.fileupload.attachment.AttachmentFile;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
        public Thumbnail(StoreAttachmentFile storeAttachmentFile) {
            this.id = storeAttachmentFile.getId();
            this.originFileName = storeAttachmentFile.getOriginFileName();
            this.fileName = storeAttachmentFile.getFileName();
            this.filePath = storeAttachmentFile.getFilePath();
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
        public DetailImages(StoreAttachmentFile storeAttachmentFile) {
            this.id = storeAttachmentFile.getId();
            this.originFileName = storeAttachmentFile.getOriginFileName();
            this.fileName = storeAttachmentFile.getFileName();
            this.filePath = storeAttachmentFile.getFilePath();
            this.fileSize = storeAttachmentFile.getFileSize();
        }


    }
}
