package com.itwillbs.LaClave.delivery;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MY_DELIVERY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyDelivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="DELIVERY_IDX")
	private Long deliveryIdx; // 배송지 idx
	
    @Column(name = "ORDER_IDX", nullable = false)//주문 FK
    private Long orderIdx;
	
    @Column(name = "MEMBER_IDX", nullable = false)
    private Integer memberIdx;
    
    @Column(name = "DELIVERY_STATUS_COMMON_IDX", nullable = false)
    private Long deliveryStatusCommonIdx; //공통코드 pk참조idx
	
    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate; //시작 날짜
	
    @Column(name = "END_DATE")
    private LocalDateTime endDate; //끝난 날짜
    
    @Column(name = "TRACKING_NO", length = 50)
	private String trackingNO; //운송장 번호
	
    @Column(name = "COURIER", length = 50)
	private String courier; //배달 서비스회사
    @Column(name = "UPDATED_AT")
    
	private LocalDateTime updatedAt; // 업데이트 날짜
	

}
