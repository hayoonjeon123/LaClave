package com.itwillbs.LaClave.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApprovalRequestDto {
    private String orderNo;
    
    private String externalTransaction; 
    
    private String payWay; 
    
    private Integer amount;

}
