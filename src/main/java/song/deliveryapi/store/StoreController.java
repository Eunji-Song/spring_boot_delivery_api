package song.deliveryapi.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import song.deliveryapi.common.response.ApiResponse;
import song.deliveryapi.store.Dto.StoreRequestDto;
import song.deliveryapi.store.Dto.StoreResponseDto;

import java.util.List;

@RestController
@Slf4j
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // 전체 매장 목록 조회
    @GetMapping("/stores")
    public ApiResponse getList() {
        return storeService.getList();
    }

    // 매장 상세 조회
    @GetMapping("/stores/{id}")
    public ApiResponse<StoreResponseDto> getDetailInfo(@PathVariable("id") Long id) {
        return storeService.getDetailInfo(id);
    }

    // 매장 등록
    @PostMapping("/stores")
    public ApiResponse save(@RequestBody StoreRequestDto requestDto) {
        return storeService.save(requestDto);
    }

    // 매장 정보 수정

    // 매장 삭제

}
