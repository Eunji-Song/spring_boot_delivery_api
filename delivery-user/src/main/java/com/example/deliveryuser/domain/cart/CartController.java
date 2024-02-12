package com.example.deliveryuser.domain.cart;


import com.example.deliverycore.entity.Member;
import com.example.deliveryuser.common.exception.BadRequestException;
import com.example.deliveryuser.common.response.ApiResponse;
import com.example.deliveryuser.common.response.ApiResult;
import com.example.deliveryuser.common.response.ResultCode;
import com.example.deliveryuser.common.util.SecurityUtil;
import com.example.deliveryuser.domain.product.ProductDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Cart - 장바구니")
@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    /**
     * 다른 매장의 장바구니가 존재하는 경우 : Error
     * 같은 매장의 장바구니가 존재하는 경우 : idx
     * 생성된 장바구니가 존재하지 않는 경우 : 0
     */
    @GetMapping("/check")
    public ApiResult isExistCart(@RequestParam Long storeId) {
        if (storeId == null || storeId < 1) {
            throw new BadRequestException(ResultCode.BAD_REQUEST);
        }
        Long cartId = cartService.isExistCart(storeId);
        return ApiResponse.success(cartId);
    }


    /**
     * 장바구니 조회
     * 계정당 하나의 장바구니 생성 가능
     */
    @GetMapping("")
    public void getCart() {

    }

    /**
     * 장바구니 생성
     */
    @PostMapping("")
    public ApiResult saveCart(@Valid @RequestBody CartDto.RequestCartDto requestCartProductDto) {
        Long cartId = cartService.saveCart(requestCartProductDto);
        return  ApiResponse.success(cartId);
    }

    /**
     * 장바구니 > 메뉴 추가
     */
    @PutMapping("/{cartId}")
    public ApiResult updateCart(@PathVariable Long cartId, @Valid @RequestBody CartDto.RequestCartDto requestCartDto) {
        if (cartId == null || cartId < 1) {
            throw new BadRequestException(ResultCode.BAD_REQUEST);
        }
        cartService.updateCart(cartId, requestCartDto);
        return ApiResponse.success(cartId);
    }


    /**
     * 장바구니 메뉴 삭제
     */
    @DeleteMapping("/{cartId}/products/{cartItemId}")
    public ApiResult deleteProduct(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        cartService.deleteCartItem(cartId, cartItemId);
        return ApiResponse.success();
    }

    /**
     * 장바구니 전체 삭제
     */
    @DeleteMapping("/{cartId}")
    public ApiResult deleteCart(@PathVariable Long cartId) {
        if (cartId == null || cartId < 1) {
            throw new BadRequestException(ResultCode.BAD_REQUEST);
        }
        cartService.deleteCart(cartId);
        return ApiResponse.success();
    }
}
