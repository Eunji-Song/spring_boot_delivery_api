package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.exception.ConflictException;
import com.example.deliveryadmin.common.exception.NotFoundException;
import com.example.deliveryadmin.common.exception.member.MemberNotFoundException;
import com.example.deliveryadmin.common.exception.store.StoreNotFoundException;
import com.example.deliveryadmin.common.util.SecurityUtil;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.MemberDto;
import com.example.deliveryadmin.domain.member.MemberMapper;
import com.example.deliveryadmin.domain.member.repository.MemberRepository;
import com.example.deliveryadmin.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StoreService {
    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

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
        Store store = storeRepository.findById(storeId).orElseThrow(
            () ->  new StoreNotFoundException(storeId)
        );

        StoreDto.DetailInfo detailDto = storeMapper.storeToDetailDto(store);
        return detailDto;
    }

    /**
     * 매장 신규 등록
     * - 매장명 중복 불가
     * - address, openingHours : null 허용하지 않음
     */
    @Transactional
    public Long save(StoreDto.RequestSaveDto requestSaveDto) {
        // 로그인한 사용자의 Id값 추출
        Long memberId = SecurityUtil.getCurrentMemberId();

        // 사용자 정보 가져오기
        Member member = memberRepository.getMemberById(memberId);
        if (member == null) {
            throw new MemberNotFoundException();
        } else {
            requestSaveDto.setMember(member);
        }

        // 같은 관리자가 같은 이름의 매장명 등록 불가
        validateInputs(memberId, requestSaveDto);

        Store store = storeMapper.saveDtoToEntity(requestSaveDto);
        log.info(store.toString());
        storeRepository.save(store);

        return store.getId();
    }

    private void validateInputs(Long memberId, StoreDto.RequestSaveDto requestSaveDto) {
        // 같은 이름의 매장 등록 중복 검사
        boolean isExistStore = storeRepository.isExistStoreName(memberId, requestSaveDto.getName());
        if (isExistStore) {
            throw new ConflictException("이미 같은 이름의 매장이 등록되어 있습니다.");
        }

        // 매장 주소 값 검사
        StoreValidate.validAddress(requestSaveDto.getAddress());

        // 매장 운영 시간 값 검사
        StoreValidate.validOpeningHours(requestSaveDto.getOpeningHours());
    }

    /**
     * 매장 정보 수정
     */
    @Transactional
    public Long update(Long storeId, StoreDto.RequestUpdateDto requestUpdateDto) {
        Store store = storeRepository.findOneById(storeId);
        if (store == null) {
            throw new StoreNotFoundException(storeId);
        }

        // 데이터 대입
        Store updateEntity = store.toBuilder()
                .name(requestUpdateDto.getName())
                .description(requestUpdateDto.getDescription())
                .address(requestUpdateDto.getAddress())
                .openingHours(requestUpdateDto.getOpeningHours())
                .status(requestUpdateDto.getStatus())
                .build();

        storeRepository.save(updateEntity);

        return updateEntity.getId();
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
