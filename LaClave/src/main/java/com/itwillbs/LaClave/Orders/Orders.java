package com.itwillbs.LaClave.Orders;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDERS") 
@Getter
@Setter
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType
    .IDENTITY) 
    @Column(name = "ORDERS_IDX")
    private Long ordersIdx;

    @Column(name = "MEMBER_IDX")
    private Long memberIdx;

    @Column(name = "ORDERS_DATE")
    private LocalDateTime ordersDate;

    @Column(name = "ORDERS_STATUS")
    private Integer ordersStatus;

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;
}