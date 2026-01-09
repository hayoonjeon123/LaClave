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
	    private Integer memberIdx;

	    @Column(name = "PRODUCT_IDX", nullable = false)
	    private Integer productIdx;

	    @Column(name = "VIEWED_AT", nullable = false)
	    private LocalDateTime viewedAt;
	
	
}
