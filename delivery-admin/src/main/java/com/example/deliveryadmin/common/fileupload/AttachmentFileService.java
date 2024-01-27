package com.example.deliveryadmin.common.fileupload;

import com.example.deliveryadmin.common.fileupload.entity.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.entity.StoreAttachmentFile;
import com.example.deliveryadmin.common.fileupload.repository.attachment.AttachmentFileRepository;
import com.example.deliveryadmin.common.fileupload.repository.store.StoreFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
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
    private final StoreFileRepository storeFileRepository;

    // AttachmentFile
    public AttachmentFile saveInfo(AttachmentFile attachmentFile) {
        // Entity 생성

        return attachmentFileRepository.save(attachmentFile);
    }

    public void deleteAttachmentFiles(List<StoreAttachmentFile> storeAttachmentFiles) {
        attachmentFileRepository.saveAll(storeAttachmentFiles.stream().map(StoreAttachmentFile::getAttachmentFile).collect(Collectors.toList()));
    }



    // == 도메인별 첨부파일 데이터 처리  == //
    public Long saveStoreAttachmentFileInfo(StoreAttachmentFile storeAttachmentFile) {
        return storeFileRepository.save(storeAttachmentFile).getId();
    }

    public void deleteStoreAttachmentFiles(List<StoreAttachmentFile> storeAttachmentFiles) {
        storeFileRepository.saveAll(storeAttachmentFiles);
    }

    public List<StoreAttachmentFile> findChildImagesByStoreId(Long storeId) {
        storeFileRepository.findChildImagesByStoreId(storeId);
        return new ArrayList<>();
    }


}
