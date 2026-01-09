package com.itwillbs.LaClave.inquiry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Integer>{
	
	// 회원별 문의 내역
	List<Inquiry> findByMemberIdx(Integer memberIdx);
	
	// 문의 상태별 조회
	List<Inquiry> findByInquiryStatus(String inquiryStatus);
	
	// 회원 + 상태 조건
	List<Inquiry> findByMemberIdxAndInquiryStatus(Integer memberIdx, String inquiryStatus);
	
}
