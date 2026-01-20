package com.itwillbs.LaClave.memberaddress;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberAddressDto {
    private Long addressIdx;
    private String recipientName;
    private String addressName;
    private String phone;
    private String postCode;
    private String address;
    private String addressDetail;
}
