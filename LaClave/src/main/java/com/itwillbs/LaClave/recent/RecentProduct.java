package com.itwillbs.LaClave.recent;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="RECENT_PRODUCT")
public class RecentProduct {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "RECENT_IDX")
	    private Integer recentIdx;

	    @Column(name = "MEMBER_IDX", nullable = false)
	    private Long memberIdx;

	    @Column(name = "PRODUCT_IDX", nullable = false)
	    private Long productIdx;

	    @Column(name = "VIEWED_AT", nullable = false)
	    private LocalDateTime viewedAt;
	    

	    /* 생성 메서드 */
	    public static RecentProduct create(Long memberIdx, Long productIdx) {
	        RecentProduct rp = new RecentProduct();
	        rp.memberIdx = memberIdx;
	        rp.productIdx = productIdx;
	        rp.viewedAt = LocalDateTime.now();
	        return rp;
	    }

	    /* 최근 본 시간 갱신 */
	    public void updateViewedAt() {
	        this.viewedAt = LocalDateTime.now();
	    }
	
	
}
