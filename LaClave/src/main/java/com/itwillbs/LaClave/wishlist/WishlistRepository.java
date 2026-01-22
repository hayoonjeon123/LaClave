package com.itwillbs.LaClave.wishlist;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
	// 회원별 위시 리스트 조회
	List<Wishlist> findByMemberIdxOrderByWishlistDateDesc(Long memberIdx);

	// 위시 리스트 중복 체크
	boolean existsByMemberIdxAndProductIdx(Long memberIdx, Integer productIdx);

	// 단건 조회 (삭제 전 확실한 확인용)
	Optional<Wishlist> findByMemberIdxAndProductIdx(Long memberIdx, Integer productIdx);

	// 상품별 찜 개수 조회
	Integer countByProductIdx(Integer productIdx);
}
