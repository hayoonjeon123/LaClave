package com.itwillbs.LaClave.review;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	
	@Column(name = "MEMBER_IDX", nullable = false)
    private Long memberIdx;

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
            String content
        ) {
            return Review.builder()
                .memberIdx(memberIdx) 
                .productIdx(productIdx)
                .ordersIdx(ordersIdx)
                .score(score)
                .content(content)
                .status("ACTIVE")
                
                .build();
        }
    
}
