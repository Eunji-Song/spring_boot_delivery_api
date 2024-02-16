package com.example.deliveryadmin.domain.order;


import com.example.deliveryadmin.common.response.ApiResponse;
import com.example.deliveryadmin.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "order - 주문 관리")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 매장 별 전체 주문
     */
    @GetMapping("")
    @Operation(description = "매장 내 주문 전체 목록")
    public ApiResult getOrders(@RequestParam("storeId") Long storeId) {
        if (storeId == null || storeId < 1) {
            return ApiResponse.error("매장 Id를 입력해주세요.");
        }

        orderService.getOrders(storeId);

        return ApiResponse.success();
    }

    /**
     * 주문 상세 목록
     */
    @GetMapping("/{orderId}")
    @Operation(description = "주문 상세 조회")
    public ApiResult getOrder(@PathVariable Long orderId) {
        if (orderId == null || orderId < 1) {
            return ApiResponse.error("매장 Id를 입력해주세요.");
        }

        return ApiResponse.success();
    }

    /**
     * 주문 상태 변경
     */
    @PutMapping("/{orderId}")
    @Operation(description = "주문 상태 변경")
    public ApiResult updateOrderStatus(@PathVariable Long orderId) {
        if (orderId == null || orderId < 1) {
            return ApiResponse.error("매장 Id를 입력해주세요.");
        }

        return ApiResponse.success();
    }

    /**
     * 주문 거절(취소)
     */
    @DeleteMapping("/{orderId}")
    @Operation(description = "주문 거절")
    public ApiResult deleteOrder(@PathVariable Long orderId) {
        if (orderId == null || orderId < 1) {
            return ApiResponse.error("매장 Id를 입력해주세요.");
        }

        return ApiResponse.success();
    }
}
