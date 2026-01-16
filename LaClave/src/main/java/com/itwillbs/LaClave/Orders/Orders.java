package com.itwillbs.LaClave.Orders;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDERS")
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERS_IDX")
    private Long ordersIdx;

    // 비즈니스용 주문번호 (예: 20260116-0001)
    @Column(name = "ORDER_NO", length = 50)
    private String orderNo;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdersDetail> orderDetails;

    @Column(name = "MEMBER_IDX")
    private Long memberIdx;

    @Builder.Default
    @Column(name = "ORDERS_DATE")
    private LocalDateTime ordersDate = LocalDateTime.now();

    @Builder.Default
    @Column(name = "ORDERS_STATUS", nullable = false)
    private Long ordersStatus = 74L; // 기본값: 주문 완료 상태 (공통코드 PK)

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;

    @Column(name = "RECIPIENT_NAME", length = 100)
    private String recipientName;

    @Column(name = "PHONE", length = 20)
    private String phone;

    @Column(name = "POST_CODE", length = 10)
    private String postCode;

    @Column(name = "ADDRESS", length = 200)
    private String address;

    @Column(name = "ADDRESS_DETAIL", length = 200)
    private String addressDetail;

    @Column(name = "DELIVERY_MSG", length = 500)
    private String deliveryMsg;

    // --- 추가: 포인트 및 배송비 ---
    @Builder.Default
    @Column(name = "USED_POINT")
    private Integer usedPoint = 0;

    @Builder.Default
    @Column(name = "DELIVERY_FEE")
    private Integer deliveryFee = 0;
}