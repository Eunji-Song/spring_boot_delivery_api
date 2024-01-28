package com.example.deliveryadmin.common.fileupload.attachment;

import com.example.deliveryadmin.common.fileupload.store.StoreAttachmentFile;
import com.example.deliveryadmin.common.fileupload.attachment.repository.AttachmentFileRepository;
import com.example.deliveryadmin.common.fileupload.store.repository.StoreAttachmentFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentFileService {
    @Value("${file.dir}")
    private String uploadDir;

    private final AttachmentFileMapper attachmentFileMapper;
    private final AttachmentFileRepository attachmentFileRepository;

    // 도메인별 Repository
    private final StoreAttachmentFileRepository storeAttachmentFileRepository;

    // AttachmentFile
    public AttachmentFile saveInfo(AttachmentFile attachmentFile) {
        // Entity 생성

        return attachmentFileRepository.save(attachmentFile);
    }

    public void deleteAttachmentFiles(List<StoreAttachmentFile> storeAttachmentFiles) {
//        attachmentFileRepository.saveAll(storeAttachmentFiles.stream().map(StoreAttachmentFile::getAttachmentFile).collect(Collectors.toList()));
    }


}
