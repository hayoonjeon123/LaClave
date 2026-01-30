package com.itwillbs.LaClave.delivery;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.config.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@RestController
@Log4j2
@RequestMapping(value = "/api/myDelivery")
@RequiredArgsConstructor //생성자 자동주입
public class MyDeliveryController {
	
	private final MyDeliveryService myDeliveryService;
	

	
	
	// 회원별 배송 조회 (로그인 사용자 기준)
	@GetMapping("/member")
	public List<MyDelivery> getMyDeliveryList(@AuthenticationPrincipal CustomUserDetails user) {
	    // CustomUserDetails에서 memberIdx 꺼내서 조회
	    return myDeliveryService.getMyDeliveryListByMember(user.getMemberIdx());
	}
	  
	  
    @GetMapping("/{orderIdx}/delivery")
    public ResponseEntity<List<MyDeliveryDto>> getDeliveryByOrder(
            @PathVariable("orderIdx") Long orderIdx
    ) {
    	log.info("조회할 orderIdx={}", orderIdx);
    	List<MyDeliveryDto> list = myDeliveryService.getDeliveryByOrder(orderIdx);
    	log.info("조회 결과={}", list);
    	
        return ResponseEntity.ok(
            myDeliveryService.getDeliveryByOrder(orderIdx)
        );
    }
	
}
	  
	
	  

