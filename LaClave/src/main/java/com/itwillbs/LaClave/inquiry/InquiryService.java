package com.itwillbs.LaClave.inquiry;

import java.util.List;

import com.itwillbs.LaClave.config.CustomUserDetails;

public interface InquiryService {

	// 회원별 문의 조회
	List<Inquiry> getMyInquiryList(CustomUserDetails user);
	
	// 문의 작성
	void createInquiry(CustomUserDetails user, InquiryCreateRequest request);
	
	// 문의 수정
	void updateInquiry(Long inquiryIdx,InquiryUpdateRequest request);
	
	// 문의 삭제
	void deleteInquiry(Long inquiryIdx);
	
	
}
