package com.itwillbs.LaClave.PayMent;

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

import com.itwillbs.LaClave.Member.Member;
import com.itwillbs.LaClave.Member.MemberRepository;
import com.itwillbs.LaClave.Member.MemberService;
import com.itwillbs.LaClave.Orders.Orders;
import com.itwillbs.LaClave.Orders.OrdersService;
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

	// 1. 주문 생성 (결제창 띄우기 직전 호출)
	@PostMapping("/create")
	public ResponseEntity<?> createOrder(@AuthenticationPrincipal CustomUserDetails user,
			@RequestBody OrderCreateRequestDto requestDto) {

	    if (user == null) {
	        return ResponseEntity.status(401).body("로그인이 필요합니다.");
	    }

	    try {
	        // 2. 로그인한 사용자의 memberIdx로 실제 Member 객체 조회
	        // memberService가 없다면 memberRepository를 직접 주입받아 사용해도 됩니다.
	    	Member member = memberRepository.findById(user.getMemberIdx())
                    .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

	        // 3. 서비스 호출 시 member 객체를 첫 번째 인자로 전달
	        // 이제 'The method createOrder(Member, ...)' 규격과 일치하게 됩니다.
	        String orderNo = ordersService.createOrder(member, requestDto); 
	        
	        System.out.println("주문 생성 성공 - orderNo: " + orderNo);
	        return ResponseEntity.ok(orderNo);

	    } catch (Exception e) {
	        System.err.println("주문 생성 실패: " + e.getMessage());
	        return ResponseEntity.status(500).body("주문 생성 오류: " + e.getMessage());
	    }
	}
	
	@GetMapping("/payment/ini-request/{orderNo}")
	public ResponseEntity<?> getInicisData(@PathVariable("orderNo") String orderNo) {
	    // 1. DB에서 '대기' 상태인 주문 정보 조회
	    Orders order = ordersService.findByOrderNo(orderNo);
	    
	    // 2. 이니시스 필수 정보 (테스트 환경)
	    String mid = "INIpayTest"; 
	    String signKey = "SU5JTElURV9URVNUX0s0S09fU0lHTktFWV9QUklWQVRF"; 
	    String timestamp = String.valueOf(System.currentTimeMillis());
	    
	 // 🔍 디버깅 로그 추가
	    System.out.println("=== 주문 정보 ===");
	    System.out.println("orderNo: " + orderNo);
	    System.out.println("totalPrice: " + order.getTotalPrice());  // ← 이 값 확인!
	    System.out.println("timestamp: " + timestamp);
	    
	    // 3. 서명(Signature) 생성
	    String signature = SignatureUtil.getSignature(orderNo, order.getTotalPrice(), timestamp);
	    
	    // 4. 프론트로 보낼 데이터 구성
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

	    return ResponseEntity.ok(res);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
