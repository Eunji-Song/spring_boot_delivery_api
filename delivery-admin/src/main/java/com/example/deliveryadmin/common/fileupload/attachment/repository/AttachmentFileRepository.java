package com.example.deliveryadmin.common.fileupload.attachment.repository;

import com.example.deliveryadmin.common.fileupload.attachment.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Long>, AttachmentFileRepositoryCustom {

}

