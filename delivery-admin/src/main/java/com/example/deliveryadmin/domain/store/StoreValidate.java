package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.exception.ConflictException;
import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.common.exception.store.StoreNotFoundException;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import com.example.deliverycore.embeded.Address;
import com.example.deliverycore.embeded.OpeningHours;
import com.example.deliverycore.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class StoreValidate {
    private final StoreRepository storeRepository;


    /**
     * 매장 정보 Null 여부 확인
     */
    public void isEmptyData(Long storeId, StoreDto.DetailInfo detailInfo) {
        if (detailInfo == null) {
            throw new StoreNotFoundException(storeId);
        }
    }

    public void isEmptyData(Long storeId, Store detailInfo) {
        if (detailInfo == null) {
            throw new StoreNotFoundException(storeId);
        }
    }



    private static boolean isNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 매장 운영 시간
     * : NOT NULL, 모든 필드 값 입력 필수
     */
    public static void validAddress(Address address) {
        if (isNull(address, address.getCity(), address.getDetail(), address.getStreet(), address.getZipCode())) {
            throw new IllegalArgumentException("주소를 입력해 주세요.");
        }
    }


    /**
     * 매장 운영 시간
     * : NOT NULL, 모든 필드 값 입력 필수
     */
    public static void validOpeningHours(OpeningHours openingHours) {
        if (isNull(openingHours, openingHours.getOpenTime(), openingHours.getCloseTime())) {
            throw new IllegalArgumentException("매장 운영 시간을 입력해 주세요.");
        }
    }


    /**
     * 매장 정보 데이터 존재 여부 확인
     */
    public boolean isExistStore(Long storeId) {
        boolean isExist = storeRepository.isExists(storeId);
        if (!isExist) {
            throw new NotFoundException("매장 정보를 찾을 수 없습니다.");
        }
        return isExist;
    }


    /**
     * 매장명 중복 검사
     *
     * @param storeName
     * @param storeId   : 매장 수정시 사용
     */
    private void validStoreNameDuplicated(String storeName, Long storeId) {
        // 사용자 id 추출
        Long memberId = SecurityUtil.getCurrentAdminId();
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
    public void validateSaveDtoInputs(StoreDto.RequestSaveDto saveDto) {
        // 매장명 중복 검사
        validStoreNameDuplicated(saveDto.getName(), null);

        // == 필수 입력 데이터 검사 == //
        validRequestDtoCommonInputs(saveDto.getAddress(), saveDto.getOpeningHours());
    }





    /**
     * 매장 정보 수정 - 입력값 검사 메서드
     */
    public void validateUpdateDtoInput(StoreDto.RequestUpdateDto updateDto, Long storeId) {
        // 매장명 중복 검사
        validStoreNameDuplicated(updateDto.getName(), storeId);

        // == 필수 입력 데이터 검사 == //
        validRequestDtoCommonInputs(updateDto.getAddress(), updateDto.getOpeningHours());
    }




}
