package com.itwillbs.LaClave.payment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.member.Member;
import com.itwillbs.LaClave.member.MemberRepository;
import com.itwillbs.LaClave.member.MemberService;
import com.itwillbs.LaClave.orders.Orders;
import com.itwillbs.LaClave.orders.OrdersService;
import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {

	@Autowired
	private final OrdersService ordersService;

	@Autowired
	private MemberRepository memberRepository;

	// 1. 주문 생성 (결제 대기)
	@PostMapping("/create")
	public ResponseEntity<?> createOrder(@AuthenticationPrincipal CustomUserDetails user,
			@RequestBody OrderCreateRequestDto requestDto) {

		if (user == null) {
			return ResponseEntity.status(401).body("로그인이 필요합니다.");
		}

		try {
			Member member = memberRepository.findById(user.getMemberIdx())
					.orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

			String orderNo = ordersService.createOrder(member, requestDto);
			log.info("주문 생성 성공 - orderNo:", orderNo);
			return ResponseEntity.ok(orderNo);

		} catch (Exception e) {
			log.info("주문 생성 실패:",e.getMessage());
			return ResponseEntity.status(500).body("주문 생성 오류: " + e.getMessage());
		}
	}

	//결제 중
	@GetMapping("/payment/ini-request/{orderNo}")
	public ResponseEntity<?> getInicisData(@PathVariable("orderNo") String orderNo) {
		Orders order = ordersService.findByOrderNo(orderNo);

		//이니시스 필수 정보 
		String mid = "INIpayTest";
		String signKey = "SU5JTElURV9UUklQTEVERVNfS0VZU1RS"; 
		String timestamp = String.valueOf(System.currentTimeMillis());

		log.info("주문 정보");
		log.info("orderNo:", orderNo);
		log.info("totalPrice:", order.getTotalPrice());
		log.info("timestamp:", timestamp);

		String signature = SignatureUtil.getSignature(signKey, orderNo, order.getTotalPrice(), timestamp);

		// 화면 보낼 데이터 
		Map<String, Object> res = new HashMap<>();
		res.put("mid", mid);
		res.put("orderNo", orderNo);
		res.put("price", order.getTotalPrice());
		res.put("timestamp", timestamp);
		res.put("signature", signature);
		res.put("mKey", SignatureUtil.encryptSHA256(signKey));
		res.put("productName", order.getOrderDetails().get(0).getProductName() + " 외");
		res.put("buyerName", order.getMember().getMemberName());
		res.put("buyerEmail", order.getMember().getEmail());
		res.put("buyerTel", "01000000000"); 

		return ResponseEntity.ok(res);
	}

	// 이니시스 결제 완료 콜백
	@PostMapping("/payment/callback")
	public String paymentCallback(@RequestBody Map<String, String> params) {
		log.info("=== 이니시스 결제 콜백 수신 ===");
		log.info("결제 결과: {}", params);

		String resultCode = params.get("resultCode");
		String orderNo = params.get("MOID");

		if ("0000".equals(resultCode)) {
			// 결제 성공 - 주문 상태 업데이트
			log.info("결제 성공 - 주문번호: {}", orderNo);
			return "redirect:http://localhost:5173/order-complete?orderNo=" + orderNo;
		} else {
			// 결제 실패
			log.error("결제 실패 - 주문번호: {}, 에러코드: {}", orderNo, resultCode);
			return "redirect:http://localhost:5173/order-failed";
		}
	}

	// 2. 결제 승인 요청 (포트원용)
	@PostMapping("/approve")
	public ResponseEntity<?> approvePayment(@RequestBody PaymentApprovalRequestDto requestDto) {
		log.info("=== 결제 승인 요청 수신 (단순화된 경로) ===");
		log.info("주문번호: {}, imp_uid: {}", requestDto.getOrderNo(), requestDto.getExternalTransaction());

		try {
			boolean result = ordersService.approvePayment(requestDto);
			if (result) {
				return ResponseEntity.ok("결제 승인 성공");
			} else {
				return ResponseEntity.status(500).body("결제 승인 실패");
			}
		} catch (Exception e) {
			log.error("결제 승인 처리 중 에러: {}", e.getMessage());
			return ResponseEntity.status(500).body("결제 승인 처리 중 에러: " + e.getMessage());
		}
	}

}
