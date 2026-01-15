package com.itwillbs.LaClave.Member;

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
    private String memberId;
    private String email;
    private String postCode;
    private String address;
    private String addressDetail;
    private Integer point;
}
