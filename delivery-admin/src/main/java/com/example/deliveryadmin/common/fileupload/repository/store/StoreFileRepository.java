package com.example.deliveryadmin.common.fileupload.repository.store;

import com.example.deliveryadmin.common.fileupload.entity.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.entity.StoreAttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreFileRepository extends JpaRepository<StoreAttachmentFile, Long>, StoreFileRepositoryCustom {
        List<StoreAttachmentFile> findChildImagesByStoreId(Long storeId);
}

