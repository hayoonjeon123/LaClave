package com.itwillbs.LaClave.inquiry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwillbs.LaClave.security.CustomUserDetails;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>{
	
	// 회원별 문의 내역
	List<Inquiry> findByMemberIdxOrderByCreatedAtDesc(Long  memberIdx);
	
	// 문의 상태별 조회
	List<Inquiry> findByInquiryStatus(String inquiryStatus);
	
	// 회원 + 상태 조건
	List<Inquiry> findByMemberIdxAndInquiryStatus(Long  memberIdx, String inquiryStatus);
	
}
