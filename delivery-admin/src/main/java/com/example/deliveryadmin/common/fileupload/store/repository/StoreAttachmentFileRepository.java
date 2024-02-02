package com.example.deliveryadmin.common.fileupload.store.repository;


import com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreAttachmentFileRepository extends JpaRepository<StoreAttachmentFile, Long>, StoreAttachmentFileRepositoryCustom {
        List<StoreAttachmentFile> findChildImagesByStoreId(Long storeId);
}

