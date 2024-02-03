package com.example.deliveryadmin.common.fileupload.service;

import com.example.deliveryadmin.common.exception.fileupload.InvalidFileException;
import com.example.deliverycore.embeded.FileInfo;
import com.example.deliverycore.entity.Product;
import com.example.deliverycore.entity.attachmentfile.ProductAttachmentFile;
import com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductAttachmentServiceImpl implements AttachmentService<ProductAttachmentFile, Product> {
    @Value("${file.dir}")
    private String uploadDir;

    private void isValidFile(MultipartFile file) {
        // 파일 데이터 존재 여부 확인
        if (file.isEmpty() || file.getSize() < 1) {
            throw new InvalidFileException("파일 데이터가 존재하지 않습니다.");
        }

        // 확장자 검사

    }

    @Override
    public ProductAttachmentFile uploadFile(MultipartFile file, boolean isThumbnail, Product product) {
        try {
            // 파일 유효성 검사
            isValidFile(file);

            // == 파일 경로 == //

            // 파일 저장할 경로 설정
            Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();

            // 디렉토리가 없다면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일명 중복 방지를 위해 UUID 사용
            String fileName = "product_" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // 파일 저장
            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // DTO 생성 후 리턴
            String originFileName = file.getOriginalFilename();
            String originalFileExtension = originFileName.substring(originFileName.lastIndexOf("."));
            String fileType = file.getContentType();
            String filePath = uploadPath + "/" + fileName;
            Long fileSize = file.getSize(); // byte

            FileInfo fileInfo = new FileInfo(originFileName, fileName, filePath, fileType, fileSize);

            ProductAttachmentFile productAttachmentFile = new ProductAttachmentFile(fileInfo);
            productAttachmentFile.setProduct(product);
            return productAttachmentFile;

        } catch (Exception e) {
            log.error("ProductAttachmentServiceImpl error : {}", e.getMessage());
        }

        return null;
    }

    @Override
    public List<ProductAttachmentFile> uploadMultiFile(MultipartFile[] files, boolean isThumbnail, Product product){
        return null;
    }

    @Override
    public void deleteFiles(List<Integer> deleteFilesIdList) {

    }
}
