package com.itwillbs.LaClave.orders;

import java.time.LocalDateTime;
import java.util.List;

import com.itwillbs.LaClave.category.Item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDERS_DETAIL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "PRODUCT_NAME", length = 200)
    private String productName;

    @Column(name = "COLOR_CODE", length = 30)
    private Long colorCode;

    @Column(name = "SIZE_CODE", length = 30)
    private Long sizeCode;

    @Column(name = "QUANTITY")
    private Long quantity;

    @Column(name = "PRICE")
    private Long price;

//    @Column(name = "DISCOUNT_PRICE")
//    private Long discountPrice;

    @Column(name = "TOTAL_PRICE", insertable = false, updatable = false)
    private Long totalPrice;

    // 추가: 상세 상태 (예: 결제완료, 배송중, 반품신청 등)
    @Builder.Default
    @Column(name = "DETAIL_STATUS", length = 30)
    private String detailStatus = "pay_01";

    // 추가: 리뷰 작성 여부 (Y/N)
    @Builder.Default
    @Column(name = "REVIEW_STATUS", insertable = false, updatable = false)
    private String reviewStatus = "N";
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_IDX", insertable = false, updatable = false)
    private Item product;
    
 
}
