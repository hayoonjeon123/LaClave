package com.itwillbs.LaClave.coupon;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.itwillbs.LaClave.Member.Member;

@Entity
@Table(name = "COUPON")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_IDX", nullable = false)
    private Member member;

    @Column(nullable = false, length = 100)
    private String couponName;

    @Column(nullable = false)
    private Integer discountValue;

    @Column(nullable = false)
    private Integer minOrderPrice = 0;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(length = 1)
    private String usedStatus = "N"; // N: 사용전, Y: 사용함

    @Column
    private LocalDate createdAt = LocalDate.now();

    @Column
    private LocalDate updatedAt = LocalDate.now();
}