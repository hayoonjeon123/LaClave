package com.itwillbs.LaClave.inquiry;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController {

		private final InquiryService inquiryService;
		
		//회원별 문의 조회
		@GetMapping("/{memberIdx}")
		public ResponseEntity<List<Inquiry>> getInquiryByMember(
		        @PathVariable("memberIdx") Integer memberIdx) {

		    return ResponseEntity.ok(
		            inquiryService.getInquiryListByMember(memberIdx)
		    );
		}
		//임시 작성
		@PostMapping("/create")
		public ResponseEntity<Void> createInquiry(@RequestBody InquiryCreateRequest request) {
		    System.out.println("문의작성 요청 들어옴: " + request.getInquiryContent());
		    inquiryService.createInquiry(1, request);
		    return ResponseEntity.ok().build();
		}
		
		
		// 문의수정 UpdateRequest라는 DTO사용하여 보안신경씀
		@PutMapping("/{inquiryIdx}")
		public ResponseEntity<Void> updateInquiry(
				@PathVariable("inquiryIdx") Integer inquiryIdx,
				@RequestBody InquiryUpdateRequest request){
			
			inquiryService.updateInquiry(inquiryIdx, request);
			
			
			return ResponseEntity.noContent().build();
		}
		
		// 문의 삭제
		@DeleteMapping("/{inquiryIdx}")
		public ResponseEntity<Void> deleteInquiry(
				@PathVariable("inquiryIdx") Integer inquiryIdx){
			
			inquiryService.deleteInquiry(inquiryIdx);
			
			return ResponseEntity.noContent().build(); // 204
			
		}
		
		
}
