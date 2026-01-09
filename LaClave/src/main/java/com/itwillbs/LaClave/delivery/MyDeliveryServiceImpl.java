package com.itwillbs.LaClave.delivery;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDeliveryServiceImpl implements MyDeliveryService{
	
	private final DeliveryRepository deliveryRepository;
	
	@Override
	public MyDelivery getMyDelivery(Integer deliveryIdx) {
		return deliveryRepository.findById(deliveryIdx)
			.orElseThrow(() -> new IllegalArgumentException("배송 정보 없음"));
	}
	
	
	
}
