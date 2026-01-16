package com.itwillbs.LaClave.wishlist;

import java.util.List;

import com.itwillbs.LaClave.security.CustomUserDetails;


public interface WishlistService {

    List<WishlistResponseDto> getWishlistBymember(Integer memberIdx);

    void removeWishlist(Integer memberIdx, Integer productIdx);

	WishlistResponseDto addWishlist(Integer productIdx, CustomUserDetails user);
}
