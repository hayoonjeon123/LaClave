package com.itwillbs.LaClave.delivery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwillbs.LaClave.commoncode.CommonCodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDeliveryServiceImpl implements MyDeliveryService{
	
	private final DeliveryRepository deliveryRepository;
	private final CommonCodeService commonCodeService;
	
//	// 주문별 배송 내역 조회
//    @Override
//    public List<MyDelivery> getDeliveryByOrder(Long orderIdx) {
//        return deliveryRepository.findByOrderIdx(orderIdx);
//    }
//	
    
    // 회원별 배송 내역 조회 예시
     @Override
     public List<MyDelivery> getMyDeliveryListByMember(Long memberIdx) {
         return deliveryRepository.findByMemberIdx(memberIdx);
     }
     
     @Override
     public List<MyDeliveryDto> getDeliveryByOrder(Long orderIdx) {

         List<MyDelivery> deliveries =
                 deliveryRepository.findByOrderIdxOrderByStartDateDesc(orderIdx);

         return deliveries.stream()
                 .map(d -> {
                     String label = commonCodeService
                             .getLabelByCommonIdx(d.getDeliveryStatusCommonIdx());
                     return MyDeliveryDto.fromEntity(d, label);
                 })
                 .collect(Collectors.toList());
     }

  
	
	
}
