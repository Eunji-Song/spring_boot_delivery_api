package com.example.deliveryadmin.common.fileupload.repository.attachment;

import com.example.deliveryadmin.common.fileupload.entity.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Long>, AttachmentFileRepositoryCustom {

}

