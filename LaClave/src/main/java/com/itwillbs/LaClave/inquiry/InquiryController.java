package com.itwillbs.LaClave.inquiry;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.config.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController {

		private final InquiryService inquiryService;
		
		//로그인 한 사용자 문의 내역
		@GetMapping("/my")
		public ResponseEntity<List<Inquiry>> getMyInquiry(
		        @AuthenticationPrincipal CustomUserDetails user) {

		    return ResponseEntity.ok(
		            inquiryService.getMyInquiryList(user)
		    );
		}
		//임시 작성
		@PostMapping("/create")
		public ResponseEntity<Void> createInquiry(@AuthenticationPrincipal CustomUserDetails user,@RequestBody InquiryCreateRequest request) {
		    System.out.println("문의작성 요청 들어옴: " + request.getInquiryContent());
		    // 로그인한 사용자 객체를 그대로 서비스로 전달
		    inquiryService.createInquiry(user, request);
		    
		    return ResponseEntity.ok().build();
		}
		
		
		// 문의수정 UpdateRequest라는 DTO사용하여 보안신경씀
		@PutMapping("/{inquiryIdx}")
		public ResponseEntity<Void> updateInquiry(
				@PathVariable("inquiryIdx") Long inquiryIdx,
				@RequestBody InquiryUpdateRequest request){
			
			inquiryService.updateInquiry(inquiryIdx, request);
			
			
			return ResponseEntity.noContent().build();
		}
		
		// 문의 삭제
		@DeleteMapping("/{inquiryIdx}")
		public ResponseEntity<Void> deleteInquiry(
				@PathVariable("inquiryIdx") Long inquiryIdx){
			
			inquiryService.deleteInquiry(inquiryIdx);
			
			return ResponseEntity.noContent().build(); // 204
			
		}
		
		
}
