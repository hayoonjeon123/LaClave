package com.itwillbs.LaClave.wishlist;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("/{memberIdx}")
	public ResponseEntity<List<Wishlist>> getMethodName(@PathVariable("memberIdx") Integer memberIdx) {
		return ResponseEntity.ok(
				wishlistService.getWishlistBymember(memberIdx));
	}
	
	@DeleteMapping("/{memberIdx}/{productIdx}")
	public ResponseEntity<Void> removeWishlist(
	        @PathVariable("memberIdx") Integer memberIdx,
	        @PathVariable("productIdx") Integer productIdx) {

	    wishlistService.removeWishlist(memberIdx, productIdx);
	    return ResponseEntity.noContent().build(); // 204
	}
	
}
