package com.itwillbs.LaClave.PayMent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApprovalRequestDto {
    private String orderNo; // 우리가 만든 주문번호
    private String externalTransaction; // 이니시스 거래번호(TID)
    private String payWay; // 결제 수단 (CARD, KAKAOPAY 등)
    private Integer amount; // 실제 결제된 금액

}
