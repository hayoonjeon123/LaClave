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
    private Long memberIdx; 

	@Column(name = "HEIGHT")
    private Double height;
	
	@Column(name = "WEIGHT")
    private Double weight; 

    @Column(name = "PREF_STYLES")
    private String prefStyles;
    

}