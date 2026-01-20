package com.itwillbs.LaClave.delivery;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDeliveryServiceImpl implements MyDeliveryService{
	
	private final DeliveryRepository deliveryRepository;
	
	// 주문별 배송 내역 조회
    @Override
    public List<MyDelivery> getDeliveryByOrder(Long orderIdx) {
        return deliveryRepository.findByOrderIdx(orderIdx);
    }
	
    
    // 회원별 배송 내역 조회 예시
     @Override
     public List<MyDelivery> getMyDeliveryListByMember(Long memberIdx) {
         return deliveryRepository.findByMemberIdx(memberIdx);
     }

  
	
	
}
