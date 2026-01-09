package com.itwillbs.LaClave.inquiry;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "INQUIRY")
public class Inquiry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INQUIRY_IDX")
	private Integer inquiryIdx;
	
	@Column(name = "MEMBER_IDX", nullable = false)
	private Integer memberIdx;
	
	@Column(name = "INQUIRY_TITLE",nullable = false, length = 50)
	private String inquiryTitle;
	
	@Column(name = "INQUIRY_CONTENT", nullable = false, length = 2000)
	private String inquiryContent;
	@Column(name = "INQUIRY_TYPE_COMMON_IDX", nullable = false)
	private Integer inquiryTypeCommonIdx;
	@Column(name = "INQUIRY_STATUS", nullable = false, length = 30)
	private String inquiryStatus;
	@Column(name = "CREATED_AT", nullable = false)
	private LocalDateTime createdAt;
	
	
	
}
