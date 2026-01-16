package com.itwillbs.LaClave.wishlist;

import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Wishlist")
@Log4j2
public class WishlistController {
	
	private final WishlistService wishlistService;
	
    // ✅ 로그인한 회원의 찜 목록
	@GetMapping
	public ResponseEntity<List<WishlistResponseDto>> getMyWishlist(
	        @AuthenticationPrincipal CustomUserDetails user) {

	    Integer memberIdx = user.getMemberIdx().intValue();
	    return ResponseEntity.ok(
	        wishlistService.getWishlistBymember(memberIdx)
	    );
	}
	
	

    // ✅ 찜 해제 (하트 취소)
    @DeleteMapping("/{productIdx}")
    public ResponseEntity<Void> removeWishlist(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("productIdx") Integer productIdx) {

        Integer memberIdx = user.getMemberIdx().intValue();
        wishlistService.removeWishlist(memberIdx, productIdx);
        return ResponseEntity.noContent().build();
    }
}

