package com.itwillbs.LaClave.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    // 1. 사용자가 입력하는 필수 필드만 남깁니다.
	private String memberId;
    private String memberPw;
    private String memberName;
    private String email;
    private Integer gender;
    private String postCode;
    private String address;
    private String addressDetail;
    private LocalDate birth;
    private String nickname;
    private Integer marketingAgree;

}