package com.itwillbs.LaClave.delivery;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@RestController
//@Controller
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
	@GetMapping("/member")
	public List<MyDelivery> getMyDeliveryList(@AuthenticationPrincipal CustomUserDetails user) {
	    return myDeliveryService.getMyDeliveryListByMember(user.getMemberIdx());
	}
	
}
	  
	
	  

