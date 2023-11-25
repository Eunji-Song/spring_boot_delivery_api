package song.deliveryapi.store;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import song.deliveryapi.common.response.ApiResponse;
import song.deliveryapi.store.Dto.StoreRequestDto;
import song.deliveryapi.store.Dto.StoreResponseDto;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StoreService(StoreRepository storeRepository, ModelMapper modelMapper) {
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
    }


    /* DTO - Entity 변환 */
    // Entity List -> DTO List
    public List<StoreResponseDto> convertEntityListToDtoList(List<Store> storeList) {
        return storeList.stream().map(this::convertEntityToDto).toList();
    }

    // Entity -> DTO
    public StoreResponseDto convertEntityToDto(Store store) {
        StoreResponseDto response = modelMapper.map(store, StoreResponseDto.class);
        return response;
    }

    // DTO List -> Entity List
    public List<Store> convertDtoListToEntityList(List<StoreRequestDto> requestDtoList) {
        return requestDtoList.stream().map(this::convertDtoToEntity).toList();
    }

    // DTO -> Entity
    public Store convertDtoToEntity(StoreRequestDto requestDto) {
        Store store = modelMapper.map(requestDto, Store.class);
        return store;
    }

    // 전체 매장 목록 조회
    public ApiResponse getList() {
        List<Store> storeList = storeRepository.findAll();
        List<StoreResponseDto> dtoList = convertEntityListToDtoList(storeList);
        return ApiResponse.success(dtoList);
    }

    // 매장 상세 조회
    public ApiResponse<StoreResponseDto> getDetailInfo(Long id) {
        Store store = storeRepository.findById(id).get();

        // Entity -> DTO
        StoreResponseDto responseDto = convertEntityToDto(store);
        return ApiResponse.success(responseDto);
    }

    // 매장 등록
    public ApiResponse save(StoreRequestDto requestDto) {
        // Dto -> Entity
        Store store = convertDtoToEntity(requestDto);
        storeRepository.save(store);
        return ApiResponse.success(store.getId());
    }


    // 매장 정보 수정


    // 매장 삭제

}
