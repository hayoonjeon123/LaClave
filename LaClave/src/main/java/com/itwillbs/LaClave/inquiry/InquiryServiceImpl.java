package com.itwillbs.LaClave.inquiry;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.config.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor //생성자 자동 생성
public class InquiryServiceImpl implements InquiryService{
	
	private final InquiryRepository inquiryRepository;
	

    // 회원별 문의 목록 조회 (로그인 사용자 기준)
    @Override
    public List<Inquiry> getMyInquiryList(CustomUserDetails user) {
    	Long  memberIdx = user.getMemberIdx();
        return inquiryRepository
                .findByMemberIdxOrderByCreatedAtDesc(memberIdx);
    }
    
    //문의작성
    public void createInquiry(CustomUserDetails user, InquiryCreateRequest request) {
        Inquiry inquiry = new Inquiry();
        inquiry.setMemberIdx(user.getMemberIdx());
        inquiry.setInquiryTitle(request.getInquiryTitle());
        inquiry.setInquiryContent(request.getInquiryContent());
        inquiry.setInquiryTypeCommonIdx(request.getInquiryTypeCommonIdx());
        inquiry.setInquiryStatus("WAIT");
        
        inquiryRepository.save(inquiry);
    }
    //문의 수정
    @Transactional
    @Override
    public void updateInquiry(Long inquiryIdx, InquiryUpdateRequest request) {
    	Inquiry inquiry = inquiryRepository.findById(inquiryIdx)
    		.orElseThrow(() -> new IllegalArgumentException("문의없음"));

    	inquiry.setInquiryTitle(request.getInquiryTitle());
    	inquiry.setInquiryContent(request.getInquiryContent());
    }
    
    //문의 삭제
    @Transactional
    @Override
    public void deleteInquiry(Long inquiryIdx) {
    	inquiryRepository.deleteById(inquiryIdx);
    }
    
}
