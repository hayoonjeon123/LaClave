package com.itwillbs.LaClave.inquiry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryCreateRequest {
	private String inquiryTitle;
	private String inquiryContent;
	private Integer inquiryTypeCommonIdx;
	private String InquiryStatus;
}
