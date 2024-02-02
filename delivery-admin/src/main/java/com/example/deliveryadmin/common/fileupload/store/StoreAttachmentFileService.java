package com.example.deliveryadmin.common.fileupload.store;

import com.example.deliveryadmin.common.fileupload.store.repository.StoreAttachmentFileRepository;
import com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreAttachmentFileService {
    private final StoreAttachmentFileRepository storeAttachmentFileRepository;

    public Long saveInfo(com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile storeAttachmentFile) {
        return storeAttachmentFileRepository.save(storeAttachmentFile).getId();
    }

    public void saveList(List<com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile> detailImageList) {
        storeAttachmentFileRepository.saveAll(detailImageList);
    }

    public void deketeFiles(List<com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile> storeAttachmentFiles) {
        storeAttachmentFileRepository.saveAll(storeAttachmentFiles);
    }

    public List<StoreAttachmentFile> findChildImagesByStoreId(Long storeId) {
        storeAttachmentFileRepository.findChildImagesByStoreId(storeId);
        return new ArrayList<>();
    }



    // 파일 단일 삭제
    public void deleteFileById(Long thumbnailId) {
        storeAttachmentFileRepository.deleteFileById(thumbnailId);
    }

    // 파일 다중 삭제
    public void deleteFilesByIdList(List<Integer> deleteFilesIdList) {
        storeAttachmentFileRepository.deleteFilesByIdList(deleteFilesIdList);
    }
//
//    // 파일 다중 삭제 - id값 기준
//    public void deleteFilesByIdList(Integer[] filesIdList) {
//        storeAttachmentFileRepository.deleteFilesByIdList(filesIdList);
//    }

}
