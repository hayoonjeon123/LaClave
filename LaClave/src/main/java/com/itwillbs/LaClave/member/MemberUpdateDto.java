package com.itwillbs.LaClave.member;

import java.time.LocalDate;
import lombok.Data;

@Data
public class MemberUpdateDto {
    private String memberName;      // 이름
    private String nickname;        // 닉네임
    private LocalDate birth;        // 생년월일
    private String postCode;        // 우편번호
    private String address;         // 주소
    private String addressDetail;   // 상세주소
}
