package com.itwillbs.LaClave.delivery;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@RestController
//@Controller
@Log4j2
@RequestMapping(value = "/api/myDelivery")
@RequiredArgsConstructor //생성자 자동주입
public class MyDeliveryController {
	
	private final MyDeliveryService myDeliveryService;
	
	//배송 조회
	// http://localhost:8080/api/myDelivery/{deliveryIdx}
	@GetMapping("/{deliveryIdx}")
	public MyDelivery getMyDelivery(
	        @PathVariable("deliveryIdx") Integer deliveryIdx) {
	    log.info("배송 조회 요청 deliveryIdx={}", deliveryIdx);
	    return myDeliveryService.getMyDelivery(deliveryIdx);
	}
	
//	  @GetMapping("/member/{memberIdx}")
//	    public List<MyDelivery> getMyDeliveryList(
//	            @PathVariable Integer memberIdx) {
//
//	        log.info("마이페이지 배송 목록 조회 memberIdx={}", memberIdx);
//	        return myDeliveryService.getMyDeliveryListByMember(memberIdx);
//	    }
//	
}
