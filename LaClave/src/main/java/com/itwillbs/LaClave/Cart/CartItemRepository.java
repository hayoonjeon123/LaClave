package com.itwillbs.LaClave.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 장바구니 내에 같은 상품/옵션이 있는지 찾는 쿼리
    Optional<CartItem> findByCartAndProductIdxAndColorCodeAndSizeCode(
            Cart cart, Long productIdx, Long colorCode, Long sizeCode);
}