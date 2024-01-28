package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;
import com.example.deliveryadmin.common.enums.StoreCategory;
import com.example.deliveryadmin.common.enums.StoreStatus;
import com.example.deliveryadmin.common.exception.ConflictException;
import com.example.deliveryadmin.common.exception.fileupload.FileUploadException;
import com.example.deliveryadmin.common.exception.store.StoreNotFoundException;
import com.example.deliveryadmin.common.fileupload.*;
import com.example.deliveryadmin.common.fileupload.attachment.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.attachment.AttachmentFileService;
import com.example.deliveryadmin.common.fileupload.store.StoreAttachmentFile;
import com.example.deliveryadmin.common.fileupload.store.StoreAttachmentFileDto;
import com.example.deliveryadmin.common.fileupload.store.StoreAttachmentFileService;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;

    private final FileUpload fileUpload;
    private final AttachmentFileService attachmentFileService;
    private final StoreAttachmentFileService storeAttachmentFileService;

    /* 중복 사용 코드 */


    /**
     * 매장 목록 전체 조회
     * 필터링 항목 : 카테고리, 상태
     */
    public List<StoreDto.ListViewData> getAllStores(StoreCategory storeCategory, StoreStatus storeStatus, String name, Pageable pageable) {
        return storeRepository.findStore(storeCategory, storeStatus, name, pageable);
    }

    /**
     * 매장 정보 상세 조회
     */
    public StoreDto.DetailInfo getStoreById(Long storeId) {
        log.info("[StoreService:getStoreById] 게시글 상세 조회");
        StoreDto.DetailInfo detailInfo = storeRepository.findOneById(storeId);
        if (detailInfo == null) {
            throw new StoreNotFoundException(storeId);
        }


        return detailInfo;
    }


    /**
     * 매장 신규 등록
     *
     * @param requestSaveDto : 매장 정보에 대한 입력값
     * @param thumbnail      : 썸네일 이미지, 하나만 등록 가능
     * @param detailImages   : 상세 페이지, 여러개 등록 가능
     * @return : storeId
     */
    @Transactional
    public Long save(StoreDto.RequestSaveDto requestSaveDto, MultipartFile thumbnail, MultipartFile[] detailImages) {
        log.info("[StoreService:save] 매장 정보 저장 요청 dto : {} ", requestSaveDto.toString());

        // == 사용자 정보 할당 == //
        Member member = SecurityUtil.getCurrentMemberInfo();
        requestSaveDto.setMember(member);

        // ==== 매장 정보 유효성 검사 및  등록 ==== //

        // 매장명 중복 불가 : 관리자 id, 매장명
        validateSaveDtoInputs(requestSaveDto);

        // 데이터 저장
        Store store = storeMapper.saveDtoToEntity(requestSaveDto);

        // 썸네일 생성
        if (thumbnail != null && thumbnail.getSize() > 0) {
            StoreAttachmentFile thumbnailInfo = getOneAttachmentFileInfo(store, thumbnail, true);
            store.setThumbnail(thumbnailInfo);
        }

        // 상세 이미지 생성
        if (detailImages != null && detailImages.length > 0) {
            List<StoreAttachmentFile> detailImageList = getMultiAttachmentFileInfo(store, detailImages, false);
            store.setDetailImages(detailImageList);
        }

        storeRepository.save(store);

        return store.getId();
    }


    /**
     * 매장 정보 수정
     */
    @Transactional
    public void update(Long storeId, StoreDto.RequestUpdateDto requestUpdateDto
                        , MultipartFile thumbnail
                        , MultipartFile[] detailImages
                        , List<Integer> deleteFilesIdList) {

        log.info("[StoreService:update] 매장 정보 수정 요청 dto : {} ", requestUpdateDto.toString());

        Store store = storeRepository.findOneByIdToEntity(storeId);
        if (store == null) {
            throw new StoreNotFoundException(storeId);
        }


        //  ==== 매장 정보 유효성 검사 및  등록 ==== //


        // 매장명 중복 불가 : 관리자 id, 매장명, 매장 id
        validateUpdateDtoInput(requestUpdateDto, storeId);


        // ==== 썸네일 신규 등록 및 수정 ==== //
        // 썸네일 생성
        if (thumbnail != null && thumbnail.getSize() > 0) {
            if (store.getThumbnail() != null) {
                storeAttachmentFileService.deleteFileById(store.getThumbnail().getId());
            }

            StoreAttachmentFile thumbnailInfo = getOneAttachmentFileInfo(store, thumbnail, true);
            store.setThumbnail(thumbnailInfo);
        }

        // 상세 이미지 생성
        if (detailImages != null && detailImages.length > 0) {
            List<StoreAttachmentFile> detailImageList = getMultiAttachmentFileInfo(store, detailImages, false);
            store.setDetailImages(detailImageList);
        }

        // 기존 상세 이미지 삭제
        if (deleteFilesIdList != null && deleteFilesIdList.size() > 0) {
            storeAttachmentFileService.deleteFilesByIdList(deleteFilesIdList);
        }


        // 데이터 수정
        Store updateEntity = store.toBuilder()
                .name(requestUpdateDto.getName())
                .description(requestUpdateDto.getDescription())
                .address(requestUpdateDto.getAddress())
                .openingHours(requestUpdateDto.getOpeningHours())
                .status(requestUpdateDto.getStatus()).build();

        storeRepository.save(updateEntity);


    }




    /* 입력값 유효성 검사 */

    /**
     * 매장명 중복 검사
     *
     * @param storeName
     * @param storeId   : 매장 수정시 사용
     */
    private void validStoreNameDuplicated(String storeName, Long storeId) {
        // 사용자 id 추출
        Long memberId = SecurityUtil.getCurrentMemberId();
        boolean isExistStore = storeRepository.isExistStoreName(memberId, storeName, storeId);
        if (isExistStore) {
            throw new ConflictException("이미 같은 이름의 매장이 등록되어 있습니다.");
        }
    }

    private void validRequestDtoCommonInputs(Address address, OpeningHours openingHours) {
        // 매장 주소 값 검사
        StoreValidate.validAddress(address);

        // 매장 운영 시간 값 검사
        StoreValidate.validOpeningHours(openingHours);
    }


    /**
     * 매장 신규 등록 - 입력값 검사 메서드
     */
    private void validateSaveDtoInputs(StoreDto.RequestSaveDto saveDto) {
        // 매장명 중복 검사
        validStoreNameDuplicated(saveDto.getName(), null);

        // == 필수 입력 데이터 검사 == //
        validRequestDtoCommonInputs(saveDto.getAddress(), saveDto.getOpeningHours());
    }


    /**
     * 매장 정보 수정 - 입력값 검사 메서드
     */
    private void validateUpdateDtoInput(StoreDto.RequestUpdateDto updateDto, Long storeId) {
        // 매장명 중복 검사
        validStoreNameDuplicated(updateDto.getName(), storeId);

        // == 필수 입력 데이터 검사 == //
        validRequestDtoCommonInputs(updateDto.getAddress(), updateDto.getOpeningHours());
    }


    /**
     * 파일 업로드 처리
     *
     * @param file        업로드할 파일
     * @param store       매장 정보
     * @param isThumbnail 썸네일 여부
     */
    private void uploadFile(MultipartFile file, Store store, boolean isThumbnail) {
        if (file != null && file.getSize() > 0) {
            try {
                log.info("[StoreService:uploadFile] 파일 업로드 시작 ");

                // 파일 업로드 후 AttachmentFile 생성 및 저장
                AttachmentFile attachmentFile = fileUpload.uploadFile(file);
//                attachmentFileService.saveInfo(attachmentFile);

                // StoreAttachmentFile 생성 및 저장
                StoreAttachmentFile storeAttachmentFile = StoreAttachmentFile.builder()
                        .store(store)
//                        .attachmentFile(attachmentFile)
                        .isThumbnail(isThumbnail)
                        .isDetailImage(!isThumbnail) // 썸네일이 아니면 상세 이미지
                        .fileName(attachmentFile.getFileName())
                        .filePath(attachmentFile.getFilePath())
                        .fileType(attachmentFile.getFileType())
                        .build();
                storeAttachmentFileService.saveInfo(storeAttachmentFile);
            } catch (Exception e) {
                log.error("[StoreService:uploadFile] 파일 업로드 중 오류 발생: isthumbnail : {} {}", isThumbnail, e.getMessage());
                throw new FileUploadException("파일 업로드 중 오류가 발생했습니다.");
            }
        }
    }

    private StoreAttachmentFile getOneAttachmentFileInfo(Store store, MultipartFile file, boolean isThumbnail) {
        StoreAttachmentFile storeAttachmentFile = null;
        try {
            log.info("[StoreService:uploadFile] 파일 업로드 시작 ");

            // 파일 업로드 후 AttachmentFile 생성 및 저장
            AttachmentFile attachmentFile = fileUpload.uploadFile(file);

            // StoreAttachmentFile 생성 및 저장
            storeAttachmentFile = new StoreAttachmentFile(store, isThumbnail, attachmentFile);
        } catch (Exception e) {
            log.error("[StoreService:uploadFile] 파일 업로드 중 오류 발생: isthumbnail : {} {}", isThumbnail, e.getMessage());
            throw new FileUploadException("파일 업로드 중 오류가 발생했습니다.");
        }

        return storeAttachmentFile;
    }

    private List<StoreAttachmentFile> getMultiAttachmentFileInfo(Store store, MultipartFile[] detailImages, boolean isThumbnail) {
        List<StoreAttachmentFile> list = new ArrayList<>();
        try {
            for (MultipartFile detailImage : detailImages) {
                StoreAttachmentFile info = getOneAttachmentFileInfo(store, detailImage, isThumbnail);
                list.add(info);
            }
        } catch (Exception e) {
            log.error("[StoreService:uploadFile] 파일 업로드 중 오류 발생: isthumbnail : {} {}", isThumbnail, e.getMessage());
            throw new FileUploadException("파일 업로드 중 오류가 발생했습니다.");
        }

        log.info("multi list : {}", list.toString());

        return list;
    }




    /**
     * 매장 삭제
     */
    @Transactional
    public void delete(Long storeId) {
        // 삭제 대상 데이터 존재 여부 확인
        boolean isExistStore = storeRepository.isExists(storeId);
        if (isExistStore == false) {
            throw new StoreNotFoundException(storeId);
        }

        // store_id가 특정 값인 storeAttachmentFile 조회
        List<StoreAttachmentFile> storeAttachmentFiles = storeAttachmentFileService.findChildImagesByStoreId(storeId);

        // 조회된 storeAttachmentFile에 연결된 AttachmentFile을 가져와서 is_del을 true로 업데이트
        for (StoreAttachmentFile storeAttachmentFile : storeAttachmentFiles) {
            storeAttachmentFile.setDel(true); // storeAttachmentFile 업데이트

//            AttachmentFile attachmentFile = storeAttachmentFile.getAttachmentFile();
//            attachmentFile.setDel(true); // AttachmentFile 업데이트
        }


//        // 변경된 내용을 저장
//        storeAttachmentFileService.deleteStoreAttachmentFiles(storeAttachmentFiles);
//        attachmentFileService.deleteAttachmentFiles(storeAttachmentFiles);
//

        storeRepository.deleteById(storeId);
    }

}
