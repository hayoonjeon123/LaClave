package com.itwillbs.LaClave.delivery;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDeliveryServiceImpl implements MyDeliveryService{
	
	private final DeliveryRepository deliveryRepository;
	
	
	//회원 배송조회
//    @Override
//    public List<MyDelivery> getMyDeliveryListByMember(Integer memberIdx) {
//        return deliveryRepository.findByMemberIdx(memberIdx);
//    }
	
    // 배송 단건 조회
    @Override
    public MyDelivery getMyDelivery(Integer deliveryIdx) {
        return deliveryRepository.findById(deliveryIdx)
                .orElseThrow(() -> new IllegalArgumentException("배송 정보 없음"));
    }

  
	
	
}
