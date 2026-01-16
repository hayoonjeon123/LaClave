package com.itwillbs.LaClave.PayMent;


import java.time.LocalDateTime;
import java.util.List;

import com.itwillbs.LaClave.Member.Member;
import com.itwillbs.LaClave.Orders.Orders;
import com.itwillbs.LaClave.Orders.OrdersDetail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAYMENT")
@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class PayMent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_IDX")
    private Long paymentIdx;

    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;

    @Column(name = "PAY_STATUS", length = 30)
    private String payStatus;

    @Column(name = "PAY_WAY", length = 30)
    private String payWay;

    @Column(name = "PAY_TYPE", length = 30)
    private String payType;

    @Column(name = "PAY_REFERENCE", length = 30)
    private String payReference;

    @Column(name = "EXTERNAL_TRANSACTION", length = 100)
    private String externalTransaction;
    
    //멤버 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_IDX")
    private Member member;
    
    //주문 테이블 연결
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERS_IDX")
    private Orders order;
}
