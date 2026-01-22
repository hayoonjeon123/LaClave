package com.itwillbs.LaClave.wishlist;

import org.springframework.web.bind.annotation.*;
import com.itwillbs.LaClave.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping({ "/Wishlist", "/wishlist", "/api/Wishlist", "/api/wishlist" })
@Log4j2
public class WishlistController {

    private final WishlistService wishlistService;

    // ✅ 로그인한 회원의 찜 목록
    @GetMapping
    public ResponseEntity<List<WishlistResponseDto>> getMyWishlist(
            @AuthenticationPrincipal CustomUserDetails user) {
        log.info("WishlistController - getMyWishlist 호출");
        if (user == null) {
            log.warn("인증 정보가 없습니다.");
            return ResponseEntity.status(401).build();
        }
        Long memberIdx = user.getMemberIdx();
        log.info("회원 번호: {}", memberIdx);
        List<WishlistResponseDto> list = wishlistService.getWishlistBymember(memberIdx);
        log.info("조회된 찜 목록 크기: {}", list.size());
        return ResponseEntity.ok(list);
    }

    // ✅ 찜 토글 (추가/삭제)
    @PostMapping("/toggle/{productIdx}")
    public ResponseEntity<Boolean> toggleWishlist(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("productIdx") Integer productIdx) {

        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        Long memberIdx = user.getMemberIdx();
        boolean result = wishlistService.toggleWishlist(memberIdx, productIdx);
        return ResponseEntity.ok(result);
    }

    // ✅ 찜 상태 확인
    @GetMapping("/status/{productIdx}")
    public ResponseEntity<Boolean> checkWishlistStatus(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("productIdx") Integer productIdx) {

        if (user == null) {
            return ResponseEntity.ok(false);
        }

        Long memberIdx = user.getMemberIdx();
        boolean isWished = wishlistService.checkStatus(memberIdx, productIdx);
        return ResponseEntity.ok(isWished);
    }

    // ✅ 찜 해제 (하트 취소)
    @DeleteMapping("/{productIdx}")
    public ResponseEntity<Void> removeWishlist(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("productIdx") Integer productIdx) {
        if (user == null)
            return ResponseEntity.status(401).build();
        Long memberIdx = user.getMemberIdx();
        wishlistService.removeWishlist(memberIdx, productIdx);
        return ResponseEntity.noContent().build();
    }
}
