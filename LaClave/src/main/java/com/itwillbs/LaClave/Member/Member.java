package com.itwillbs.LaClave.Member;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "MEMBER")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_IDX")
    private Integer memberIdx;
	
	@Column(name = "MEMBER_NAME, nullable = false")
	private String memberName;
	
	@Column(name = "MEMBER_ID, nullable = false, unique = true")
	private String memberId;
	
	@Column(name = "MEMBER_PW, nullable = false")
	private String memberPw;
	
	@Column(name = "MEMBER_GENDER")
	private String memberGender;
	
	@Column(name = "POST_CODE", length = 10)
    private String postCode;

    @Column(name = "ADDRESS", length = 100)
    private String address;

    @Column(name = "ADDRESS_DETAIL", length = 100)
    private String addressDetail;

    @Column(name = "BIRTH")
    private LocalDateTime birth;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "SIGNUP_DATE")
    private LocalDateTime signupDate;

    @Column(name = "MEMBER_STATUS", length = 30)
    private String memberStatus;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "MAIL_AUTH_STATUS")
    private Integer mailAuthStatus;

    @Column(name = "MARKETING_AGREE")
    private Integer marketingAgree;

    @Column(name = "POINT")
    private Integer point;

    @Column(name = "NICKNAME", length = 50)
    private String nickname;
	
	
	
	

}
