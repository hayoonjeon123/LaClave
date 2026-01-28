package com.itwillbs.LaClave.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {
    private String memberName;
    private String nickname;
    private String memberId;
    private String email;
    private LocalDate birth;
    private String postCode;
    private String address;
    private String addressDetail;
    private Integer point;
    private String phone;
    private Long addrIdx;
}
