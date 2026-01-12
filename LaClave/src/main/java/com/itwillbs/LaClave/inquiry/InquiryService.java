package com.itwillbs.LaClave.inquiry;

import java.util.List;

public interface InquiryService {

	// 회원별 문의 조회
	List<Inquiry> getInquiryListByMember(Integer memberIdx);
	
	
	// 문의 수정
	void updateInquiry(Integer inquiryIdx,InquiryUpdateRequest request);
	
	// 문의 삭제
	void deleteInquiry(Integer inquiryIdx);
}
