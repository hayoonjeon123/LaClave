package com.itwillbs.LaClave.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	// 장바구니 정보를 조회
    Optional<Cart> findByMemberIdx(Long memberIdx);
}
