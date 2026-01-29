package com.itwillbs.LaClave.recent;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.Config.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/RecentProduct")
@RequiredArgsConstructor
@Log4j2
public class RecentProductController {
	
	private final RecentProductService recentProductService;
	private final RecentProductRepository recentProductRepository;
	
//	@GetMapping("/recent/{memberIdx}")
//	public ResponseEntity<List<RecentProductDto>> getRecentProductsBymember(
//	        @PathVariable("memberIdx") Long memberIdx) {
//	    return ResponseEntity.ok(
//	        recentProductService.getRecentProductsBymember(memberIdx)
//	    );
//	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<RecentProductDto>> getMyRecentProducts(
	        @AuthenticationPrincipal CustomUserDetails user
	) {
	    if (user == null) {
	        return ResponseEntity.status(401).build();
	    }

	    return ResponseEntity.ok(
	        recentProductService.getRecentProductsBymember(user.getMemberIdx())
	    );
	}
	

	
	@PostMapping("/add/{productIdx}")
	public ResponseEntity<Void> addRecentProduct(
	        @AuthenticationPrincipal CustomUserDetails user,
	        @PathVariable("productIdx") Long productIdx
	) {
	    // ✅ 로그인 안 한 경우 그냥 무시 or 401
	    if (user == null) {
	        return ResponseEntity.status(401).build();
	        // 또는 return ResponseEntity.ok().build(); (아예 무시 전략)
	    }

	    recentProductService.addRecentProduct(
	        user.getMemberIdx(),
	        productIdx
	    );

	    return ResponseEntity.ok().build();
	}

}	
