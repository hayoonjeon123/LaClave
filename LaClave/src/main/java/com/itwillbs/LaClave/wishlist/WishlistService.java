package com.itwillbs.LaClave.wishlist;

import java.util.List;

import com.itwillbs.LaClave.security.CustomUserDetails;

public interface WishlistService {
    List<WishlistResponseDto> getWishlistBymember(Long memberIdx);

    void removeWishlist(Long memberIdx, Integer productIdx);

    WishlistResponseDto addWishlist(Integer productIdx, CustomUserDetails user);

    // ✅ 찜 토글 (추가/삭제)
    boolean toggleWishlist(Long memberIdx, Integer productIdx);

    // ✅ 찜 여부 확인
    boolean checkStatus(Long memberIdx, Integer productIdx);
}
