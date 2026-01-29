package com.itwillbs.LaClave.point;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.Config.CustomUserDetails;
import com.itwillbs.LaClave.coupon.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController {
	
	   private final PointService pointService;
	
    @GetMapping("/my")
    public ResponseEntity<List<PointDto>> getMyPointList(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(
                pointService.getMyPointList(user)
        );
    }
	

}
