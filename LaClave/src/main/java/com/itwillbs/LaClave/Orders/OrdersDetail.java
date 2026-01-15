package com.itwillbs.LaClave.Orders;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDERS_DETAIL")
@Getter
@Setter
@NoArgsConstructor
public class OrdersDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERS_DETAIL_IDX")
    private Long ordersDetailIdx;

    // 주문(Orders) 테이블과의 연관 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERS_IDX")
    private Orders order;

    @Column(name = "PRODUCT_IDX")
    private Long productIdx;

    @Column(name = "COLOR_CODE")
    private Integer colorCode;

    @Column(name = "SIZE_CODE")
    private Integer sizeCode;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "DISCOUNT_PRICE")
    private Integer discountPrice;

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;
}
