package com.example.deliveryadmin.common.fileupload.service;

import com.example.deliverycore.embeded.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface AttachmentService<T, U> {
    T uploadFile(MultipartFile file, boolean isThumbnail, U target);
//    T uploadOneFile(MultipartFile file, boolean isThumbnail);
    List<T> uploadMultiFile(MultipartFile[] files, boolean isThumbnail,  U target);

    void deleteFiles(List<Integer> deleteFilesIdList);
}
