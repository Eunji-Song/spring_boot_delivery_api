package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.fileupload.*;
import com.example.deliveryadmin.common.fileupload.service.StoreAttachmentServiceImpl;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.product.ProductDto;
import com.example.deliveryadmin.domain.product.ProductService;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.deliverycore.enums.StoreCategory;
import com.example.deliverycore.enums.StoreStatus;
import com.example.deliverycore.entity.Admin;
import com.example.deliverycore.entity.Store;
import com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile;
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
    private final StoreAttachmentServiceImpl storeAttachmentFileService;

    /**
     * 매장 목록 전체 조회
     * 필터링 항목 : 카테고리, 상태
     */
    public Page<StoreDto.ListViewData> getAllStores(StoreCategory storeCategory, StoreStatus storeStatus, String name, Pageable pageable) {
        log.info("[StoreService:getAllStores] 매장 목록 조회");
        return null;
//        return storeRepository.findStowre(storeCategory, storeStatus, name, pageable);
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
     * @param thumbnailFile      : 썸네일 이미지, 하나만 등록 가능
     * @param detailImages   : 상세 페이지, 여러개 등록 가능
     * @return : storeId
     */
    @Transactional
    public Long save(StoreDto.RequestSaveDto requestSaveDto
                    , MultipartFile thumbnailFile
                    , MultipartFile[] detailImages) {
        log.info("[StoreService:save] 매장 정보 저장 요청 dto : {} ", requestSaveDto.toString());


        // == 사용자 정보 할당 == //
        Admin Admin = SecurityUtil.getCurrentAdminInfo();
        requestSaveDto.setAdmin(Admin);


        // ==== 매장 정보 유효성 검사 및  등록 ==== //


        // 매장명 중복 및 입력 데이터 유효성 검증
        storeValidate.validateSaveDtoInputs(requestSaveDto);

        // 데이터 저장
        Store store = storeMapper.saveDtoToEntity(requestSaveDto);

        // 썸네일 생성
        if (thumbnailFile != null && thumbnailFile.getSize() > 0) {
            StoreAttachmentFile thumbnail = storeAttachmentFileService.uploadFile(thumbnailFile, true, store);
            store.setThumbnail(thumbnail);
        }

        // 상세 이미지 생성
        if (detailImages != null && detailImages.length > 0) {
            List<StoreAttachmentFile> detailImageList = storeAttachmentFileService.uploadMultiFile(detailImages, false, store);
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
                        , MultipartFile thumbnailFile
                        , MultipartFile[] detailImages
                        , List<Integer> deleteFilesIdList) {
        log.info("[StoreService:update] 매장 정보 수정 요청 dto : {} ", requestUpdateDto.toString());


        Store store = storeRepository.findOneByIdToEntity(storeId);
        storeValidate.isEmptyData(storeId, store);


        //  ==== 매장 정보 유효성 검사 및  등록 ==== //


        // 매장명 중복 불가 : 관리자 id, 매장명, 매장 id
        storeValidate.validateUpdateDtoInput(requestUpdateDto, storeId);


        // ==== 썸네일 신규 등록 및 수정 ==== //

        // 썸네일 생성
        if (thumbnailFile != null && thumbnailFile.getSize() > 0) {
            // 기존 썸네일 삭제 처리
            if (store.getThumbnail() != null) {
                store.getThumbnail().setDel(true);
            }

            // 신규 썸네일 등록
            StoreAttachmentFile thumbnail = storeAttachmentFileService.uploadFile(thumbnailFile, true, store);
            store.setThumbnail(thumbnail);

        }

        // 기존 상세 이미지 삭제
        if (deleteFilesIdList != null && deleteFilesIdList.size() > 0) {
            storeAttachmentFileService.deleteFiles(deleteFilesIdList);
        }

        // 상세 이미지 생성
        if (detailImages != null && detailImages.length > 0) {
            List<StoreAttachmentFile> detailImageList = storeAttachmentFileService.uploadMultiFile(detailImages, false, store);
            store.setDetailImages(detailImageList);
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
