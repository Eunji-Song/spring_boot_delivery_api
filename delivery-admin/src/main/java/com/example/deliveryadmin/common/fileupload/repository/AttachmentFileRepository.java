package com.example.deliveryadmin.common.fileupload.repository;

import com.example.deliveryadmin.common.fileupload.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Long>, AttachmentFileRepositoryCustom {

}
