package com.example.deliveryadmin.common.fileupload.store.repository;

import java.util.List;

public interface StoreAttachmentFileRepositoryCustom {
    void deleteFilesByIdList(List<Integer> deleteFilesIdList);
}
