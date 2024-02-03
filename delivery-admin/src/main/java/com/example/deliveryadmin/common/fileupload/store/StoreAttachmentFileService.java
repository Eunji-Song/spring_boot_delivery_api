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

    // 파일 다중 삭제
    public void deleteFilesByIdList(List<Integer> deleteFilesIdList) {
        storeAttachmentFileRepository.deleteFilesByIdList(deleteFilesIdList);
    }

}
