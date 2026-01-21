package com.itwillbs.LaClave.inquiry;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

// DTO 생성
@Data
@AllArgsConstructor
public class InquiryResponseDto {
    private Long inquiryIdx;
    private String inquiryTitle;
    private String inquiryContent;
    private String inquiryStatus; // "WAIT" or "답변완료"
    private String inquiryType;   // 필요시 공통코드 변환
    private LocalDateTime createdAt;
    private String answerContent; // 관리자 답변
    private LocalDateTime answeredAt;
}