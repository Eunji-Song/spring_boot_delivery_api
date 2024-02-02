package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.exception.fileupload.FileUploadException;
import com.example.deliveryadmin.common.fileupload.*;
import com.example.deliveryadmin.common.fileupload.store.StoreAttachmentFileService;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.product.ProductDto;
import com.example.deliveryadmin.domain.product.ProductService;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.deliverycore.enums.StoreCategory;
import com.example.deliverycore.enums.StoreStatus;
import com.example.deliverycore.entity.Member;
import com.example.deliverycore.entity.Store;
import com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile;
import com.example.deliverycore.embeded.AttachmentFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;
    private final StoreValidate storeValidate; // 매장 유효성 검사
    private final FileUpload fileUpload;
    private final StoreAttachmentFileService storeAttachmentFileService;



    /**
     * 매장 목록 전체 조회
     * 필터링 항목 : 카테고리, 상태
     */
    public Page<StoreDto.ListViewData> getAllStores(StoreCategory storeCategory, StoreStatus storeStatus, String name, Pageable pageable) {
        log.info("[StoreService:getAllStores] 매장 목록 조회");

        return storeRepository.findStore(storeCategory, storeStatus, name, pageable);
    }


    /**
     * 매장 정보 상세 조회
     */
    public StoreDto.DetailInfo getStoreById(Long storeId) {
        log.info("[StoreService:getStoreById] 매장 상세 조회");

        StoreDto.DetailInfo detailInfo = storeRepository.findOneById(storeId);
        storeValidate.isEmptyData(storeId, detailInfo);

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
    public Long save(StoreDto.RequestSaveDto requestSaveDto
                    , MultipartFile thumbnail
                    , MultipartFile[] detailImages) {
        log.info("[StoreService:save] 매장 정보 저장 요청 dto : {} ", requestSaveDto.toString());


        // == 사용자 정보 할당 == //
        Member member = SecurityUtil.getCurrentMemberInfo();
        requestSaveDto.setMember(member);


        // ==== 매장 정보 유효성 검사 및  등록 ==== //


        // 매장명 중복 및 입력 데이터 유효성 검증
        storeValidate.validateSaveDtoInputs(requestSaveDto);

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

        log.info("store : {}", storeId);

        Store store = storeRepository.findOneByIdToEntity(storeId);
        storeValidate.isEmptyData(storeId, store);


        //  ==== 매장 정보 유효성 검사 및  등록 ==== //


        // 매장명 중복 불가 : 관리자 id, 매장명, 매장 id
        storeValidate.validateUpdateDtoInput(requestUpdateDto, storeId);


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
        Store store = storeRepository.findOneByIdToEntity(storeId);
        storeValidate.isEmptyData(storeId, store);

        storeRepository.delete(store);
    }


    // == 매장 내 메뉴 로직 == //

    private final ProductService productService;

    /**
     * 매장 내 등록된 전체 메뉴 리스트
     */
    public List<ProductDto.ListData> getAllProducts(Long storeId) {
        // 올바른 매장의 정보가 입력되었는지 확인
        storeValidate.isExistStore(storeId);

        // 매장 하위 메뉴 리턴
        return productService.getAllProducts(storeId);
    }

    /**
     * 매장 내 등록된 메뉴 상세 조회
     */
    public ProductDto.DetailInfo getProduct(Long storeId, Long productId) {
        // 올바른 매장의 정보가 입력되었는지 확인
        storeValidate.isExistStore(storeId);

        // 메뉴 상세 조회
        return productService.getProduct(storeId, productId);
    }


}
