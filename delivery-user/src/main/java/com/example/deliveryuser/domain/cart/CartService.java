package com.example.deliveryuser.domain.cart;

import com.example.deliverycore.entity.*;
import com.example.deliveryuser.common.exception.ConflictException;
import com.example.deliveryuser.common.exception.NotFoundException;
import com.example.deliveryuser.common.exception.store.StoreNotFoundException;
import com.example.deliveryuser.common.util.SecurityUtil;
import com.example.deliveryuser.domain.cart.repository.CartRepository;
import com.example.deliveryuser.domain.cartitem.repository.CartItemRepository;
import com.example.deliveryuser.domain.product.repository.ProductRepository;
import com.example.deliveryuser.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartValidate cartValidate;
    private final CartMapper cartMapper;

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;


    /**
     * 다른 매장의 장바구니가 존재하는 경우 : Error
     * 같은 매장의 장바구니가 존재하는 경우 : idx
     * 생성된 장바구니가 존재하지 않는 경우 : 0
     */

    public Long isExistCart(Long storeId) {
        Long cartId = 0L;
        Long memberId = SecurityUtil.getCurrentMemberId();

        Cart cart = cartRepository.getCart(memberId);
        if (cart != null) {
            if (!cart.getStore().getId().equals(storeId)) {
                throw new ConflictException("다른 매장의 상품이 담긴 장바구니가 존재합니다.");
            } else {
                cartId = cart.getId();
            }
        }

        return cartId;
    }

    /**
     * 장바구니 조회
     */


    /**
     * 장바구니 신규 생성
     */
    @Transactional
    public Long saveCart(CartDto.RequestCartDto requestCartDto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Long storeId = requestCartDto.getStoreId();

        // 사용자 계정의 storeId값이 다른 장바구니 생성 여부 확인
        Long cartId = isExistCart(storeId);
        if (cartId != 0) {
            throw new ConflictException("이미 장바구니가 존재합니다.");
        }

        // 매장 정보 유효성 검사
        Store store = storeRepository.getStoreById(storeId);
        if (store == null) {
            throw new StoreNotFoundException(storeId);
        }

        // 매장 + 상품 유효성 검사
        Long productId = requestCartDto.getProductId();
        Product productPriceInfo = productRepository.getProductPrice(storeId, productId);
        if (productPriceInfo == null) {
            throw new NotFoundException("상품을 찾을 수 없습니다.");
        }

        // 장바구니 상품 합계 금액
        int totalPrice = productPriceInfo.getPrice() * requestCartDto.getQuantity();

        // cart item 생성
        CartItem cartItem = new CartItem(productPriceInfo, requestCartDto.getQuantity());


        // 해당 매장의 장바구니 생성(Cart Entity)
        Cart cartEntity = new Cart(new Store(storeId), new Member(memberId), totalPrice);
        cartEntity.getItems().add(cartItem);
        cartItem.setCart(cartEntity);
        cartRepository.save(cartEntity);

        return cartEntity.getId();
    }

    /**
     * 장바구니 > 메뉴 추가
     */
    @Transactional
    public Long updateCart(Long cartId, CartDto.RequestCartDto requestCartDto) {
        // 장바구니 정보 불러오기
        Cart cart = cartRepository.findCartById(cartId);
        int cartTotalPrice = cart.getTotalPrice();
        if (cart == null) {
            throw new NotFoundException("장바구니 정보를 찾을 수 없습니다.");
        }

        Product product = cartValidate.getProductPriceInfo(requestCartDto.getStoreId(), requestCartDto.getProductId());
        log.info("product id:{}", product.getId());


        // cart item 생성
        int productQuantity = requestCartDto.getQuantity();
        boolean isProductAlreadyInCart = false;
        CartItem cartItem = new CartItem(product, productQuantity);

        // 같은 제품을 추가 한 경우 quantity 를 업데이트
        List<CartItem> items = cart.getItems();
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                log.info("Du");
                int price = item.getProduct().getPrice();
                int originalQuantity = item.getQuantity();
                int totalQuantity = originalQuantity + productQuantity;

                item.setQuantity(totalQuantity);
                item.setTotalPrice(totalQuantity * price);
                isProductAlreadyInCart = true;


                cartTotalPrice += totalQuantity * price;

                cart.setTotalPrice();
            }
        }

        // 신규 제품 추가
        if (!isProductAlreadyInCart) {
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
            cart.setTotalPrice();
        }


        cartRepository.save(cart);
        return cart.getId();
    }

    /**
     * 장바구니 메뉴 삭제
     */
    @Transactional
    public void deleteCartItem(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findCartById(cartId);


        // 장바구니에서 해당 상품을 삭제하는 메서드 호출
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException("해당 상품이 장바구니에 존재하지 않습니다."));

        cartItemRepository.deleteById(cartItemId);
        cart.getItems().remove(cartItem);
        cart.setTotalPrice();

        if (cart.getItems().size() < 1) {
            deleteCart(cartId);
        }

    }

    /**
     * 장바구니 전체 삭제
     */
    @Transactional
    public void deleteCart(Long cartId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        cartRepository.deleteById(cartId);
    }
}
