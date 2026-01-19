package com.itwillbs.LaClave.review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import com.itwillbs.LaClave.Cart.ItemImage;
import com.itwillbs.LaClave.Member.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "REVIEW")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_IDX")
    private Integer reviewIdx;

    @Column(name = "MEMBER_IDX", insertable = false, updatable = false)
    private Long memberIdx;

    // 작성자 정보 조인
    @ManyToOne
    @JoinColumn(name = "MEMBER_IDX")
    private Member member;

    @Column(name = "ORDERS_IDX", nullable = false)
    private Integer ordersIdx;

    @Column(name = "PRODUCT_IDX", nullable = false)
    private Integer productIdx;
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

    public static Review create(
            Long memberIdx,
            Integer productIdx,
            Integer ordersIdx,
            Double score,
            String content) {
        return Review.builder()
                .memberIdx(memberIdx)
                .productIdx(productIdx)
                .ordersIdx(ordersIdx)
                .score(score)
                .content(content)
                .status("ACTIVE")

                .build();
    }

    // @OneToMany(mappedBy = "review")
    // private List<ItemImage> images = new ArrayList<>();

}
