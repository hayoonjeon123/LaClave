package com.itwillbs.LaClave.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "AIPROFILE")
public class AiProfile {
    
	@Id
	@Column(name = "MEMBER_IDX")
    private Long memberIdx; // Member 테이블의 PK를 그대로 사용

	@Column(name = "HEIGHT")
    private Double height; // NULL 허용 (Double 사용)
	
	@Column(name = "WEIGHT")
    private Double weight; // NULL 허용

    @Column(name = "PREF_STYLES")
    private String prefStyles; // ["데일리", "시크"] 형태의 JSON 문자열 저장
    

}