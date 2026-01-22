package com.itwillbs.LaClave.delivery;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyDeliveryDto {

    private Long deliveryIdx;
    private Long orderIdx;

    private Long deliveryStatusCommonIdx;
    private String deliveryStatusLabel;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String trackingNo;
    private String courier;

    public static MyDeliveryDto fromEntity(
            MyDelivery delivery,
            String deliveryStatusLabel
    ) {
        return MyDeliveryDto.builder()
                .deliveryIdx(delivery.getDeliveryIdx())
                .orderIdx(delivery.getOrderIdx())
                .deliveryStatusCommonIdx(delivery.getDeliveryStatusCommonIdx())
                .deliveryStatusLabel(deliveryStatusLabel)
                .startDate(delivery.getStartDate())
                .endDate(delivery.getEndDate())
                .trackingNo(delivery.getTrackingNO())
                .courier(delivery.getCourier())
                .build();
    }
}
