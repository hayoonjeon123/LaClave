package com.itwillbs.LaClave.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	@NotBlank(message = "아이디를 입력해주세요.")
	@Pattern(regexp = "^(?=.*\\d)[A-Za-z\\d@$!%*#?&]{4,20}$", 
    message = "아이디는 영문, 숫자, 특수문자를 포함하여 4~20자리여야 합니다.")
	private String memberId;
	
	@NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(message = "비밀번호는 최소 8자 이상이어야 합니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", 
    message = "비밀번호는 8자 이상, 영문, 숫자, 특수문자를 모두 포함해야 합니다.")
	private String memberPw;
    
	@NotBlank(message = "이름을 입력해주세요.") // 이름도 필수라면 추가!
    private String memberName;
    
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    
    @NotNull(message = "성별을 체크해주세요.") 
    private Integer gender;
    
    @NotBlank(message = "주소를 입력해주세요.")
    private String postCode;
    
    private String address;
    private String addressDetail;
    
    @NotNull(message = "생년월일을 입력해주세요.")
    private LocalDate birth;
    
    private String nickname;
    
    @NotNull(message = "마케팅 수신 동의 여부를 선택해주세요.") 
    private Integer marketingAgree;
    
    private Double height;
    
    private Double weight;
    
    private List<String> prefStyles; 


}