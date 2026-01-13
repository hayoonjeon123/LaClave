package com.itwillbs.LaClave.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "MEMBER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_IDX")
    private Integer memberIdx;
	
	@Column(name = "MEMBER_NAME", nullable = false)
	private String memberName;
	
	@Column(name = "MEMBER_ID", nullable = false, unique = true)
	private String memberId;
	
	@Column(name = "MEMBER_PW", nullable = false)
	private String memberPw;
	
	// Security 권한 관리를 위한 필드
	@Column(name = "MEMBER_ROLE")
	private String memberRole = "ROLE_USER";
	
	@Column(name = "GENDER")
	private Integer gender;
	
	@Column(name = "POST_CODE", length = 10)
    private String postCode;

    @Column(name = "ADDRESS", length = 100)
    private String address;

    @Column(name = "ADDRESS_DETAIL", length = 100)
    private String addressDetail;

    @Column(name = "BIRTH")
    private LocalDate birth;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "SIGNUP_DATE")
    private LocalDateTime signupDate;

    @Column(name = "MEMBER_STATUS")
    private Integer memberStatus;

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
	
    /* --- UserDetails 인터페이스 구현 메서드 --- */
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.memberRole));
    }

    @Override
    public String getPassword() {
    	return this.memberPw;
    }
    
    @Override
    public String getUsername() {
    	return this.memberId;
    }
    
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
	

}

