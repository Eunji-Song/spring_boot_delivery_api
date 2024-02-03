package com.example.deliveryadmin.domain.product;

import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.common.fileupload.service.ProductAttachmentServiceImpl;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliverycore.entity.Member;
import com.example.deliveryadmin.domain.product.repository.ProductRepository;
import com.example.deliverycore.entity.Product;
import com.example.deliverycore.entity.Store;
import com.example.deliverycore.entity.attachmentfile.ProductAttachmentFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidate productValidate;
    private final ProductAttachmentServiceImpl productAttachmentService;



    // == 메뉴 CURD 로직 == //

    /**
     * 메뉴 신규 등록
     */
    @Transactional
    public Long save(ProductDto.RequestSave requestSave, MultipartFile thumbnailFile)  {
        log.info("[ProductService:save] 메뉴 정보 저장 요청 dto : {} ", requestSave.toString());

        // == 사용자 정보 할당 == //
        Member member = SecurityUtil.getCurrentMemberInfo();
        requestSave.setMember(member);

        // == 메뉴 정보 유효성 검사 == //
        Long storeId = requestSave.getStoreId();
        productValidate.validRequestDtoInputs(storeId, requestSave.getName(), requestSave.getPrice(), null);


        // == 데이터 insert 처리 == //

        // 매장 정보 세팅
        Store store = new Store(storeId);

        // DTO -> Entity Mapping
        Product product = productMapper.saveDtoToEntity(requestSave).toBuilder().store(store).build();

        // 썸네일
        ProductAttachmentFile thumbnail = null;
        if (thumbnailFile != null && thumbnailFile.getSize() > 0) {
            thumbnail = productAttachmentService.uploadFile(thumbnailFile, true, product);
            thumbnail.setProduct(product);
            product.setThumbnail(thumbnail);
        }

        // product, 썸네일 이미지 데이터 동시 저장
        productRepository.save(product);

        return product.getId();
    }


    /**
     * 메뉴 수정
     * <p>
     * DTO 필드 데이터 Entity 업데이트 진행 시 주의사항
     * - 엔티티 클래스에 Setter를 선언하지 않아 Mapstruct 사용 불가
     * - 엔티티 클래스에 Setter를 선언하는 것은 데이터의 불변성에 위배되는 행위로 정말 DTO에서는 사용하지 않는 필드인데 엔티티로 변환할 때에는 데이터를 넣어줘야 하는 경우 아니면 setter 선언 x
     * - DTO의 필드를 하나씩 검사하면 빈값이 아닌경우 Entity 클래스에 update
     */
    @Transactional
    public void update(Long productId, ProductDto.RequestUpdate requestUpdate, MultipartFile thumbnailFile) {
        log.info("[ProductService:update] 메뉴 정보 수정 요청 dto : {} ", requestUpdate.toString());

        // 데이터 존재 여부 확인
        Product product = productRepository.findByIdAndStoreId(productId, requestUpdate.getStoreId()).orElseThrow(() -> new NotFoundException("메뉴를 찾을 수 없습니다."));

        // 데이터 유효성 검사 진행
        productValidate.validRequestDtoInputs(product.getStore().getId(), requestUpdate.getName(), requestUpdate.getPrice(), productId);

        // == 썸네일 처리 == //

        // 기존 썸네일 데이터 삭제 처리
        if (product.getThumbnail() != null && product.getThumbnail().getId() > 0) {
            // 기존 썸네일 데이터 삭제 처리
            product.getThumbnail().setDel(true);
        }

        // 신규 썸네일 이미지 등록
        ProductAttachmentFile thumbnail = null;
        if (thumbnailFile != null && thumbnailFile.getSize() > 0) {
            thumbnail = productAttachmentService.uploadFile(thumbnailFile, true, product);
            product.setThumbnail(thumbnail);
        }

        // == 데이터 update 처리 == //
        updateEntityDataFromDto(requestUpdate, product);

    }


    /**
     * DTO 필드 데이터 Entity 업데이트
     */
    private Product updateEntityDataFromDto(ProductDto.RequestUpdate requestUpdate, Product product) {
        // DTO에서 업데이트할 필드를 가져와서 엔티티에 대입
        Field[] fields = requestUpdate.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                if (field.getName().equals("storeId")) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(requestUpdate);
                if (value != null && !String.valueOf(value).isEmpty()) {
                    Field entityField = product.getClass().getDeclaredField(field.getName());
                    entityField.setAccessible(true);
                    entityField.set(product, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // 필드에 접근할 수 없는 경우 등의 예외 처리
                log.error("[ProductService:updateEntityDataFromDto] 데이터 처리 중 오류 발생  ex : {}", e.getMessage());
                throw new RuntimeException("데이터 처리 중 오류가 발생했습니다.");
            }
        }

        return product;

    }


    /**
     * 메뉴 삭제
     */
    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("메뉴를 찾을 수 없습니다."));
        product.setDel(true);
    }






    // == 매장에 등록된 메뉴 처리 로직 == //


    /**
     * 매장별 전체 메뉴 조회
     */
    public List<ProductDto.ListData> getAllProducts(Long storeId) {
        log.info("[ProductService:getAllProducts] 메뉴 전체 조회 매장 id : {}", storeId);

        return productRepository.getAllProducts(storeId);
    }

    /**
     * 메뉴 상세 조회
     */
    public ProductDto.DetailInfo getProduct(Long storeId, Long productId) {
        log.info("[ProductService:getProductById] 메뉴 상세 조회 메뉴 storeId : {}, productId: {}", storeId, productId);


        ProductDto.DetailInfo detailInfo = productRepository.getProductByIdAndStore(storeId, productId);
        if (detailInfo == null) {
            throw new NotFoundException("메뉴를 찾을 수 없습니다.");
        }


        return detailInfo;
    }



}
