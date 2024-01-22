package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.config.jwt.TokenProvider;
import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.common.response.ResultCode;
import com.example.deliveryadmin.common.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StoreService {
    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;

    /**
     * 매장 목록 전체 조회
     */
    public void getAllStores() {

    }

    /**
     * 매장 정보 상세 조회
     */
    public void getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new NotFoundException(ResultCode.NOT_FOUND)
        );

        StoreResponseDto.StoreDetailDTO detailDTO = storeMapper.entityToDetailDto(store);
        log.info("store response : {}", detailDTO);
    }

    /**
     * 매장 신규 등록
     * - 매장명 중복 검사는 하지 않음
     * - address, openingHours : null 허용하지 않음
     */
    @Transactional(readOnly = true)
    public Long saveStore(StoreRequestDto.SaveDto saveDto) {
        // 입력 값 검사
        validateStoreInputs(saveDto);

        // saveDto의 값 중 adminId 값에 200을 입력
        Long memberId = SecurityUtil.getCurrentMemberId();
        StoreRequestDto.SaveDto buildDto = saveDto.toBuilder().adminId(memberId).build();

        // DTO -> Entity  & save
        Store store = storeMapper.saveDtoToEntity(buildDto);
        storeRepository.save(store);

        return store.getId();
    }

    private void validateStoreInputs(StoreRequestDto.SaveDto saveDto) {
        // 매장 주소 값 검사
        StoreValidate.validAddress(saveDto.getAddress());

        // 매장 운영 시간 값 검사
        StoreValidate.validOpeningHours(saveDto.getOpeningHours());
    }
}
