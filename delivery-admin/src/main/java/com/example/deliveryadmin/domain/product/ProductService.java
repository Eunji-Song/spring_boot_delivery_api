package com.example.deliveryadmin.domain.product;

import com.example.deliveryadmin.common.exception.ApplicationException;
import com.example.deliveryadmin.common.exception.ConflictException;
import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.common.exception.fileupload.FileUploadException;
import com.example.deliveryadmin.common.exception.fileupload.MultipartDataNotValidException;
import com.example.deliveryadmin.common.fileupload.FileUpload;
import com.example.deliveryadmin.common.fileupload.attachment.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.product.ProductAttachmentFile;
import com.example.deliveryadmin.common.fileupload.product.repository.ProductAttachmentFileRepositoryImpl;
import com.example.deliveryadmin.common.fileupload.product.repository.ProductAttachmentFileService;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.product.repository.ProductRepository;
import com.example.deliveryadmin.domain.store.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import com.example.deliveryadmin.domain.store.StoreService;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
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
    private final FileUpload fileUpload;

    // 첨부파일
    private final ProductAttachmentFileService productAttachmentFileService;

    // store
    private final StoreRepository storeRepository;



    // == 메뉴 CURD 로직 == //


    /**
     * 입력값 유효성 검사
     * - 매장 내 같은 이름의 상품 등록 불가
     * - store_id는 1 이상의 값만 입력 가능
     * - price 100원 단위로 입력 가능
     */
    private void validRequestDtoInputs(Long storeId, String name, int price, Long productId) {
        log.info("[ProductService:validRequestDtoInputs] 데이터 유효성 검증 시작 product name : {}", name);

        // storeId 데이터 존재 여부 확인
        boolean isExistStore = storeRepository.isExists(storeId);
        if (!isExistStore) {
            throw new NotFoundException("매장 정보가 존재하지 않습니다. ");
        }

        // 1보다 작은 값 입력 불가
        if (storeId < 1 || storeId == null) {
            throw new IllegalArgumentException("매장 정보를 입력해 주세요.");
        }

        // 매장 내 상품명 중복 불가
        boolean isExistProduct = productRepository.isExistName(storeId, name, productId);
        if (isExistProduct) {
            throw new ConflictException(name + "은 이미 매장 내 등록된 상품명 입니다.");
        }

        // 가격는 100원 단위로 입력 가능
        if (price % 100 != 0) {
            throw new IllegalArgumentException("가격은 100원 단위로 입력이 가능합니다.");
        }
    }


    private ProductAttachmentFile uploadThumbnail(MultipartFile thumbnail) {
        log.info("[ProductService:uploadThumbnail] 썸네일 업로드 시작");

        ProductAttachmentFile productAttachmentFile = null;
        try {
            if (thumbnail == null || thumbnail.getSize() < 1) {
                throw new MultipartDataNotValidException("썸네일이 업로드 되지 않았습니다.");
            }

            // 첨부파일 기본 데이터 구조로 리턴
            AttachmentFile attachmentFile = fileUpload.uploadFile(thumbnail);

            // 상품 이미지 파일 데이터로 변경
            productAttachmentFile = new ProductAttachmentFile(attachmentFile);
        } catch (Exception e) {
            log.error("[ProductService:uploadThumbnail] 파일 업로드 중 오류 발생: isthumbnail : {}", e.getMessage());
            throw new FileUploadException("파일 업로드 중 오류가 발생했습니다.");
        }

        return productAttachmentFile;
    }


    /**
     * 메뉴 신규 등록
     */
    @Transactional
    public Long save(ProductDto.RequestSave requestSave, MultipartFile thumbnail) {
        log.info("[ProductService:save] 메뉴 정보 저장 요청 dto : {} ", requestSave.toString());

        // == 사용자 정보 할당 == //
        Member member = SecurityUtil.getCurrentMemberInfo();
        requestSave.setMember(member);

        // == 메뉴 정보 유효성 검사 == //
        Long storeId = requestSave.getStoreId();
        validRequestDtoInputs(storeId, requestSave.getName(), requestSave.getPrice(), null);


        // == 데이터 insert 처리 == //

        // 매장 정보 세팅
        Store store = new Store(storeId);

        // DTO -> Entity Mapping
        Product product = productMapper.saveDtoToEntity(requestSave).toBuilder().store(store).build();

        // 썸네일
        ProductAttachmentFile productThumbnail = null;
        if (thumbnail != null && thumbnail.getSize() > 0) {
            productThumbnail = uploadThumbnail(thumbnail);
            productThumbnail.setProduct(product);
            product.setThumbnail(productThumbnail);
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
    public void update(Long productId, ProductDto.RequestUpdate requestUpdate, MultipartFile thumbnail) {
        log.info("[ProductService:update] 메뉴 정보 수정 요청 dto : {} ", requestUpdate.toString());

        // 데이터 존재 여부 확인
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("메뉴를 찾을 수 없습니다."));

        // 데이터 유효성 검사 진행
        validRequestDtoInputs(product.getStore().getId(), requestUpdate.getName(), requestUpdate.getPrice(), productId);

        // == 썸네일 처리 == //

        // 신규 썸네일 이미지 등록
        ProductAttachmentFile productAttachmentFile = null;
        if (thumbnail != null && thumbnail.getSize() > 0) {
            productAttachmentFile = uploadThumbnail(thumbnail);
            requestUpdate.setThumbnail(productAttachmentFile);
        }

        // 기존 썸네일 데이터 삭제 처리
        if (product.getThumbnail() != null && product.getThumbnail().getId() > 0) {
            // 기존 썸네일 데이터 삭제 처리
            productAttachmentFileService.delete(product.getThumbnail().getId());
        }

        // == 데이터 update 처리 == //
        Product updateProduct = updateEntityDataFromDto(requestUpdate, product);

        // 썸네일 데이터가 존재하는 경우 값 할당
        if (productAttachmentFile != null) {
            updateProduct.setThumbnail(productAttachmentFile);
            productAttachmentFile.setProduct(product);
        }
    }


    /**
     * DTO 필드 데이터 Entity 업데이트
     */
    private Product updateEntityDataFromDto(ProductDto.RequestUpdate requestUpdate, Product product) {
        // DTO에서 업데이트할 필드를 가져와서 엔티티에 대입
        Field[] fields = requestUpdate.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
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
    public Product getProductById(Long productId) {
        log.info("[ProductService:getProductById] 메뉴 상세 조회 메뉴 id : {}", productId);

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("메뉴를 찾을 수 없습니다."));

        return product;
    }



}
