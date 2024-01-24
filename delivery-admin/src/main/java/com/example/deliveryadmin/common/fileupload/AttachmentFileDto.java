package com.example.deliveryadmin.common.fileupload;

import com.example.deliveryadmin.domain.product.Product;
import com.example.deliveryadmin.domain.review.Review;
import com.example.deliveryadmin.domain.store.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import lombok.*;

@Getter
@ToString
public class AttachmentFileDto {
    private String originFileName;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;
    private StoreDto.DetailInfo store;
    private Product product;
    private Review review;

    @Builder(toBuilder = true)
    public AttachmentFileDto(String originFileName, String fileName, String filePath, String fileType, Long fileSize) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public void setStore(Store store) {
        this.store = new StoreDto.DetailInfo(store);
    }

    public AttachmentFileDto() {
    }


}
