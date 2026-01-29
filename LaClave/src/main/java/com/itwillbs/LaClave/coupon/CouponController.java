package com.itwillbs.LaClave.coupon;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.Config.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/Coupon")
@RequiredArgsConstructor
public class CouponController {
	private final CouponService couponService;
	
	@GetMapping("/my")
	public ResponseEntity<List<CouponDto>> getMyCouponList( @AuthenticationPrincipal CustomUserDetails user){
		
		 return ResponseEntity.ok(
				 couponService.getMyCouponList(user)
			);
		
	}

}
