package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;
import com.example.deliveryadmin.common.exception.ConflictException;
import com.example.deliveryadmin.common.exception.fileupload.FileUploadException;
import com.example.deliveryadmin.common.exception.store.StoreNotFoundException;
import com.example.deliveryadmin.common.fileupload.*;
import com.example.deliveryadmin.common.fileupload.dto.AttachmentFileDto;
import com.example.deliveryadmin.common.fileupload.entity.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.entity.StoreAttachmentFile;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;
    private final AttachmentFileService attachmentFileService;
    private final FileUpload fileUpload;


    /**
     * 매장 목록 전체 조회
     * 필터링 항목 : 카테고리, 상태
     */
    public void getAllStores(StoreDto.RequestSearchDto requestSearchDto, Pageable pageable) {

//        return Page<StoreDto.ListViewData>;
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
        // 사용자 id 추출
        Long memberId = SecurityUtil.getCurrentMemberId();
        boolean isExistStore = storeRepository.isExistStoreName(memberId, saveDto.getName(), null);
        if (isExistStore) {
            throw new ConflictException("이미 같은 이름의 매장이 등록되어 있습니다.");
        }

        // == 필수 입력 데이터 검사 == //
        // == 필수 입력 데이터 검사 == //
        validRequestDtoCommonInputs(saveDto.getAddress(), saveDto.getOpeningHours());


    }

    /**
     * 매장 정보 수정 - 입력값 검사 메서드
     */
    private void validateUpdateDtoInput(StoreDto.RequestUpdateDto updateDto, Long storeId) {
        // 사용자 id 추출
        Long memberId = SecurityUtil.getCurrentMemberId();
        boolean isExistStore = storeRepository.isExistStoreName(memberId, updateDto.getName(), storeId);
        if (isExistStore) {
            throw new ConflictException("이미 같은 이름의 매장이 등록되어 있습니다.");
        }

        // == 필수 입력 데이터 검사 == //
        validRequestDtoCommonInputs(updateDto.getAddress(), updateDto.getOpeningHours());
    }



    /**
     * 매장 신규 등록
     * - 매장명 중복 불가
     * - address, openingHours : null 허용하지 않음
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
        storeRepository.save(store);//

        //  ==== 썸네일 등록 ==== //

        // 외부로 데이터를 노출하지 않고 내부에서만 처리하기 때문에 엔티티로 선언
        AttachmentFile attachmentFile = null;

        // 썸네일은 한번에 한장만 업로드 가능
        if (thumbnail != null && thumbnail.getSize() > 0) {
            try {
                log.info("[StoreService:save] 썸네일 파일 업로드 시작 ");

                // 파일 업로드 후 ID값 리턴
                attachmentFile = fileUpload.uploadFile(thumbnail);
                attachmentFileService.saveInfo(attachmentFile);


                // store_attachment_file 데이터 생성
                StoreAttachmentFile storeAttachmentFile = StoreAttachmentFile.builder()
                                                            .store(store)
                                                            .attachmentFile(attachmentFile)
                                                            .isThumbnail(true)
                                                            .isDetailImage(false)
                                                            .build();
                attachmentFileService.saveStoreAttachmentFileInfo(storeAttachmentFile);

            } catch (Exception e) {
                log.error("file upload error : {}", e.getMessage());
                throw new FileUploadException("썸네일 파일 업로드에 실패하였습니다. == 플로우 종료 == ");
            }
        }


        // 상세 이미지 등록
        if (detailImages.length > 0) {
            try {
                log.info("[StoreService:save] 상세 이미지 파일 업로드 시작 ");

                for (MultipartFile detailImage : detailImages) {
                    AttachmentFile detailAttachment = fileUpload.uploadFile(detailImage);
                    StoreAttachmentFile detailStoreAttachment = StoreAttachmentFile.builder()
                            .store(store)
                            .attachmentFile(detailAttachment)
                            .isThumbnail(false)
                            .isDetailImage(true)
                            .build();
                    attachmentFileService.saveStoreAttachmentFileInfo(detailStoreAttachment);
                }


            } catch (Exception e) {
                log.error("file upload error : {}", e.getMessage());
                throw new FileUploadException("상세 이미지 파일 업로드에 실패하였습니다. == 플로우 종료 == ");
            }
        }



        return store.getId();
    }




    /**
     * 매장 정보 수정
     */
    @Transactional
    public void update(Long storeId, StoreDto.RequestUpdateDto requestUpdateDto, MultipartFile thumbnail) {
        // 매장 Id 데이터 확인
        Store store = storeRepository.findOneByIdToEntity(storeId);
        if (store == null) {
            throw new StoreNotFoundException(storeId);
        }


        //  ==== 매장 정보 유효성 검사 및  등록 ==== //

        // 매장명 중복 불가 : 관리자 id, 매장명, 매장 id
        validateUpdateDtoInput(requestUpdateDto, storeId);

        // 데이터 수정
        Store updateEntity = store.toBuilder()
                .name(requestUpdateDto.getName())
                .description(requestUpdateDto.getDescription())
                .address(requestUpdateDto.getAddress())
                .openingHours(requestUpdateDto.getOpeningHours())
                .status(requestUpdateDto.getStatus())
                .build();
        storeRepository.save(updateEntity);

        // ==== 썸네일 신규 등록 및 수정 ==== //
        AttachmentFileDto uploadDto = null;
        if (thumbnail != null && thumbnail.getSize() > 0) {
            try {
                log.info("[StoreService:update] 썸네일 업로드");

                // 파일 업로드
//                uploadDto = attachmentFileService.saveFile(thumbnail);

                // 업로드한 파일 데이터 DB에 저장 후 id 값 리턴
//                Long attachmentFileId = attachmentFileService.saveStoreAttachmentFileInfo(uploadDto, store);
//
//                if (store.getThumbnails() != null) {
//                    log.info("[StoreService:update] 기존 썸네일 삭제 처리");
//                    attachmentFileService.deleteFileInfo(store.getThumbnails().get(0).getId());
//                }
            } catch (Exception e) {
                throw new FileUploadException("파일 업로드에 실패하였습니다. == 플로우 종료");
            }
        }

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
        List<StoreAttachmentFile> storeAttachmentFiles = attachmentFileService.findChildImagesByStoreId(storeId);

        // 조회된 storeAttachmentFile에 연결된 AttachmentFile을 가져와서 is_del을 true로 업데이트
        for (StoreAttachmentFile storeAttachmentFile : storeAttachmentFiles) {
            storeAttachmentFile.setDel(true); // storeAttachmentFile 업데이트

            AttachmentFile attachmentFile = storeAttachmentFile.getAttachmentFile();
            attachmentFile.setDel(true); // AttachmentFile 업데이트
        }



        // 변경된 내용을 저장
        attachmentFileService.deleteStoreAttachmentFiles(storeAttachmentFiles);
        attachmentFileService.deleteAttachmentFiles(storeAttachmentFiles);


        storeRepository.deleteById(storeId);
    }

}
