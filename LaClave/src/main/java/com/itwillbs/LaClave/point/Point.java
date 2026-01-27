package com.itwillbs.LaClave.point;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.itwillbs.LaClave.category.Item;
import com.itwillbs.LaClave.member.Member;

@Entity
@Table(name = "POINT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointIdx;

    @ManyToOne
    @JoinColumn(name = "MEMBER_IDX")
    private Member member;

    @Column(nullable = false)
    private Integer pointAmount; // 적립(+), 사용(-)

    @Column
    private Long orderIdx; // 사용시 연결 주문번호

    @Column(length = 200)
    private String description;

    @Column
    private LocalDate createdAt = LocalDate.now();
}