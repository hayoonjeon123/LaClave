package com.itwillbs.LaClave.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import com.itwillbs.LaClave.Cart.ItemImage;
import com.itwillbs.LaClave.Category.Item;
import com.itwillbs.LaClave.Member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "REVIEW")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_IDX")
    private Integer reviewIdx;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_IDX", nullable = false)
    private Member member;

    // 상품
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_IDX", nullable = false)
    private Item product;

    // 주문 (지금은 FK만)
    @Column(name = "ORDERS_IDX", nullable = false)
    private Integer ordersIdx;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "CONTENT", length = 1000)
    private String content;

    @Column(name = "SCORE", nullable = false)
    private Double score;

    @Column(name = "STATUS", nullable = false)
    private String status;

    /* ===== 생성 메서드 ===== */
    public static Review create(
            Member member,
            Item product,   
            Integer ordersIdx,
            Double score,
            String content) {

        return Review.builder()
                .member(member)
                .product(product)
                .ordersIdx(ordersIdx)
                .score(score)
                .content(content)
                .status("ACTIVE")
                .build();
    }

    public void update(Double score, String content) {
        this.score = score;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.status = "DELETED";
        this.updatedAt = LocalDateTime.now();
    }
}
