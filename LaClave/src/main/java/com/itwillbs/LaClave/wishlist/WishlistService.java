package com.itwillbs.LaClave.wishlist;

import java.util.List;


public interface WishlistService {
	
	//회원별 찜 목록 리스트
	List<Wishlist> getWishlistBymember(Integer memberIdx);
	
	//위시리스트 삭제
	void removeWishlist(Integer memberIdx, Integer productIdx);

}
