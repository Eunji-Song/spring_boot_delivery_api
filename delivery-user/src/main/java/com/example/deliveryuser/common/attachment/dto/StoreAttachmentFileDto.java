package com.example.deliveryuser.common.attachment.dto;

import com.example.deliverycore.embeded.FileInfo;
import com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

        private FileInfo fileInfo;

        @QueryProjection
        public Thumbnail(StoreAttachmentFile storeAttachmentFile) {
            this.id = storeAttachmentFile.getId();
            this.fileInfo = storeAttachmentFile.getFileInfo();
        }
    }


    /**
     * StoreAttachmentDto + Attachment ver.
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailImages {
        private FileInfo fileInfo;

        @QueryProjection
        public DetailImages(StoreAttachmentFile storeAttachmentFile) {
            this.fileInfo = storeAttachmentFile.getFileInfo();
        }


    }
}
