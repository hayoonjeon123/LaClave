package com.itwillbs.LaClave.wishlist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{ 
	
	//회원별 위시 리스트 조회 //SELECT * FROM wishlist WHERE member_idx = ? order by WishlistDate Desc
	List<Wishlist> findByMemberIdxOrderByWishlistDateDesc(Integer memberIdx);
	
	//위시 리스트 중복 체크
	boolean existsByMemberIdxAndProductIdx(Long memberIdx, Integer productIdx);
	
	/* 상품 기준 삭제 (옵션) */  
	// 하트 취소 버튼용
    void deleteByMemberIdxAndProductIdx(Integer memberIdx, Integer productIdx);
    
}
