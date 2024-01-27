package com.example.deliveryadmin.common.fileupload;

import com.example.deliveryadmin.common.exception.fileupload.InvalidFileException;
import com.example.deliveryadmin.common.fileupload.entity.AttachmentFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public  class FileUpload {
    @Value("${file.dir}")
    private String uploadDir;

    // == 파일 유효성 검사 === //
    public void isValidFile(MultipartFile file) {
        // 파일 데이터 존재 여부 확인
        if (file.isEmpty() || file.getSize() < 1) {
            throw new InvalidFileException("파일 데이터가 존재하지 않습니다.");
        }

        // 확장자 검사
    }

    // == 다중 파일 처리 === //
    public List<AttachmentFile> uploadFiles(MultipartFile[] files) throws Exception {
        List<AttachmentFile> fileInfoList = new ArrayList<>();
        for (MultipartFile file : files) {
            fileInfoList.add(upload(file));
        }
        return fileInfoList;
    }

    // == 단일 파일 처리 === //
    public AttachmentFile uploadFile(MultipartFile multipartFile) throws Exception {
        AttachmentFile attachmentFile = upload(multipartFile);
        return attachmentFile;
    }



    // == 파일 업로드 === //
    private AttachmentFile upload(MultipartFile file) throws Exception {
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
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // 파일 저장
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // DTO 생성 후 리턴
        String originFileName = file.getOriginalFilename();
        String originalFileExtension = originFileName.substring(originFileName.lastIndexOf("."));
        String fileType = file.getContentType();
        String filePath = uploadPath + "/" + fileName;
        Long fileSize = file.getSize(); // byte

        AttachmentFile attachmentFile = new AttachmentFile(originFileName, fileName, filePath, fileType, fileSize);
        return attachmentFile;
    }



}
