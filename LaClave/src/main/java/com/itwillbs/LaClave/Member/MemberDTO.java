package com.itwillbs.LaClave.Member;

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
    private String memberId;
    private String memberPw;
    private String memberName;
    private String email;
    private Integer gender;
    private String postCode;
    private String address;
    private String addressDetail;
    private LocalDateTime birth;
    private LocalDateTime signupDate;
    private Integer memberStatus;
    private LocalDateTime updateAt;
    private Integer mailAuthStatus;
    private Integer marketingAgree;
    private Integer point;
    private String nickname;
    private Integer memberRole;
}