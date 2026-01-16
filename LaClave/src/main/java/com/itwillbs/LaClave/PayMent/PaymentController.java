package com.itwillbs.LaClave.PayMent;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.Orders.Orders;
import com.itwillbs.LaClave.Orders.OrdersService;
import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class PaymentController {

	private final OrdersService ordersService;

	// 1. 주문 생성 (결제창 띄우기 직전 호출)
	@PostMapping("/create")
	public ResponseEntity<?> createOrder(@AuthenticationPrincipal CustomUserDetails user,
			@RequestBody OrderCreateRequestDto requestDto) {

		// 로그인 확인
		if (user == null) {
			System.err.println("로그인되지 않은 사용자의 주문 시도");
			return ResponseEntity.status(401).body("로그인이 필요합니다.");
		}

		Long memberIdx = user.getMemberIdx();
		System.out.println("=== 주문 생성 요청 ===");
		System.out.println("로그인 사용자: " + user.getUsername() + " (memberIdx: " + memberIdx + ")");

		try {
			// 로직 실행
			String orderNo = ordersService.createOrder(memberIdx, requestDto);
			System.out.println("주문 생성 성공 - orderNo: " + orderNo);
			return ResponseEntity.ok(orderNo);
		} catch (Exception e) {
			System.err.println("주문 생성 실패: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(500).body("주문 생성 중 오류가 발생했습니다: " + e.getMessage());
		}
	}
}
