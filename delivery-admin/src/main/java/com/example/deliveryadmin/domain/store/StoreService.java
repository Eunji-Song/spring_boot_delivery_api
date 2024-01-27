package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;
import com.example.deliveryadmin.common.exception.ApplicationException;
import com.example.deliveryadmin.common.exception.ConflictException;
import com.example.deliveryadmin.common.exception.fileupload.FileUploadException;
import com.example.deliveryadmin.common.exception.store.StoreNotFoundException;
import com.example.deliveryadmin.common.fileupload.*;
import com.example.deliveryadmin.common.fileupload.AttachmentFileDto;
import com.example.deliveryadmin.common.fileupload.repository.AttachmentFileRepository;
import com.example.deliveryadmin.common.response.ResultCode;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.repository.MemberRepository;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;
    private final AttachmentFileService attachmentFileService;
    private final AttachmentFileMapper attachmentFileMapper;
    private final AttachmentFileRepository attachmentFileRepository;

    /**
     * 매장 목록 전체 조회
     * 필터링 항목 : 카테고리, 상태
     */
    public List<StoreDto.DetailInfo> getAllStores(StoreDto.RequestSearchDto requestSearchDto) {
        log.info("[Store]");
        List<StoreDto.DetailInfo> storeList = storeRepository.findAllStore(requestSearchDto);
        return storeList;
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
    public Long save(StoreDto.RequestSaveDto requestSaveDto, MultipartFile thumbnail) {
        // == 사용자 정보 할당 == //
        Member member = SecurityUtil.getCurrentMemberInfo();
        requestSaveDto.setMember(member);

        //  ==== 매장 정보 유효성 검사 및  등록 ==== //

        // 매장명 중복 불가 : 관리자 id, 매장명
        validateSaveDtoInputs(requestSaveDto);

        // 데이터 저장
        Store store = storeMapper.saveDtoToEntity(requestSaveDto);
        storeRepository.save(store);//

        //  ==== 썸네일 등록 ==== //
        AttachmentFileDto uploadDto = null;
        if (thumbnail != null && thumbnail.getSize() > 0) {
            try {
                log.error("[StoreService:save] 파일 업로드 시작 ");

                uploadDto = attachmentFileService.saveFile(thumbnail);
                uploadDto.setStore(store);

                log.error("[StoreService:save] 파일 DTO mapping, save");
                AttachmentFile entity = attachmentFileMapper.dtoToEntity(uploadDto);
                attachmentFileRepository.save(entity);
            } catch (Exception e) {
                log.error("file upload error : {}", e.getMessage());
                throw new FileUploadException("파일 업로드에 실패하였습니다. == 플로우 종료");
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
                uploadDto = attachmentFileService.saveFile(thumbnail);

                // 업로드한 파일 데이터 DB에 저장 후 id 값 리턴
                Long attachmentFileId = attachmentFileService.saveStoreAttachmentFileInfo(uploadDto, store);

                if (store.getThumbnails() != null) {
                    log.info("[StoreService:update] 기존 썸네일 삭제 처리");
                    attachmentFileService.deleteFileInfo(store.getThumbnails().get(0).getId());
                }
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
        boolean isExistStore = storeRepository.existsById(storeId);
        if (isExistStore == false) {
            throw new StoreNotFoundException(storeId);
        }

        storeRepository.deleteById(storeId);
    }

}
