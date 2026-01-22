package com.itwillbs.LaClave.delivery;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@RestController
@Log4j2
@RequestMapping(value = "/api/myDelivery")
@RequiredArgsConstructor //생성자 자동주입
public class MyDeliveryController {
	
	private final MyDeliveryService myDeliveryService;
	

	
	
//	  @GetMapping("/member/{memberIdx}")
//	    public List<MyDelivery> getMyDeliveryList(
//	            @PathVariable("memberIdx") Long memberIdx ,@AuthenticationPrincipal CustomUserDetails user) {
//
//	        log.info("마이페이지 배송 목록 조회 memberIdx={}", memberIdx);
//	        return myDeliveryService.getMyDeliveryListByMember(memberIdx);
//	    }
    @GetMapping("/{orderIdx}/delivery")
    public ResponseEntity<List<MyDeliveryDto>> getDeliveryByOrder(
            @PathVariable("orderIdx") Long orderIdx
    ) {
        return ResponseEntity.ok(
            myDeliveryService.getDeliveryByOrder(orderIdx)
        );
    }
	
}
	  
	
	  

