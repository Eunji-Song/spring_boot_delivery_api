package com.example.deliveryadmin.common.fileupload;

import com.example.deliveryadmin.common.exception.fileupload.InvalidFileException;
import com.example.deliveryadmin.common.fileupload.repository.AttachmentFileRepository;
import com.example.deliveryadmin.domain.store.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentFileService {
    @Value("${file.dir}")
    private String uploadDir;

    private final AttachmentFileMapper attachmentFileMapper;
    private final AttachmentFileRepository attachmentFileRepository;


    // == 파일 유효성 검사 === //
    public void isValidFile(MultipartFile file) {
        // 파일 데이터 존재 여부 확인
        if (file.isEmpty() || file.getSize() < 1) {
            throw new InvalidFileException("파일 데이터가 존재하지 않습니다.");
        }

        // 확장자
    }


    // == 다중 파일 처리 === //
    public void saveFiles(List<MultipartFile> files) throws Exception {
        for (MultipartFile file : files) {
            upload(file);
        }
    }

    // == 단일 파일 처리 === //
    public AttachmentFileDto saveFile(MultipartFile multipartFile) throws Exception {
        AttachmentFileDto fileDto = upload(multipartFile);
        return fileDto;
    }

    // == 파일 업로드 === //
    private AttachmentFileDto upload(MultipartFile file) throws Exception {
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

        // DTO 생성
        String originFileName = file.getOriginalFilename();
        String originalFileExtension = originFileName.substring(originFileName.lastIndexOf("."));
        String fileType = file.getContentType();
        String filePath = uploadPath + "/" + fileName;
        Long fileSize = file.getSize(); // byte

        AttachmentFileDto fileDto = new AttachmentFileDto().builder()
                .originFileName(originFileName)
                .fileName(fileName)
                .filePath(filePath)
                .fileType(fileType)
                .fileSize(fileSize)
                .build();

        return fileDto;
    }


    // == 엔티티에 store 값 세팅 후 mapping == //
    public Long saveStoreAttachmentFileInfo(AttachmentFileDto attachmentFileDto, Store store) {
        log.error("[AttachmentFileService:setStoreAttachmentFileEntity] 파일 DTO mapping");
        attachmentFileDto.setStore(store);

        AttachmentFile entity = attachmentFileMapper.dtoToEntity(attachmentFileDto);
        attachmentFileRepository.save(entity);

        return entity.getId();
    }

    public void deleteFileInfo(Long attachmentFileId) {
        attachmentFileRepository.deleteAttachmentFileInfo(attachmentFileId);
    }
}
