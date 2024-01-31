package com.example.deliveryadmin.common.fileupload.product.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAttachmentFileService {
    private final ProductAttachmentFileRepository productAttachmentFileRepository;
    public void delete(Long id) {
        productAttachmentFileRepository.deleteById(id);
    }
}
