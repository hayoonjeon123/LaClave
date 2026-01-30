package com.itwillbs.LaClave.delivery;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwillbs.LaClave.commoncode.CommonCodeService;
import com.itwillbs.LaClave.image.Image;
import com.itwillbs.LaClave.image.ImageRepository;
import com.itwillbs.LaClave.orders.OrdersDetail;
import com.itwillbs.LaClave.orders.OrdersDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyDeliveryServiceImpl implements MyDeliveryService{
	
	private final DeliveryRepository deliveryRepository;
	private final CommonCodeService commonCodeService;
	private final ImageRepository imageRepository;
	private final OrdersDetailRepository ordersDetailRepository;
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

                     Long productIdx = ordersDetailRepository
                    	        .findFirstByOrder_OrdersIdxOrderByOrdersDetailIdxAsc(orderIdx)
                    	        .map(OrdersDetail::getProductIdx)
                    	        .orElse(null);

                     String productImageUrl = null;
                     if (productIdx != null) {
                         productImageUrl = imageRepository
                                 .findFirstByTargetCodeAndTargetTypeAndTargetIdx(
                                         "img_01",
                                         "PRODUCT",
                                         productIdx.intValue()
                                 )
                                 .map(Image::getImageUrl)
                                 .orElse("default_image_url");
                     }

                     return MyDeliveryDto.fromEntity(d, label, productImageUrl);
                 })
                 .collect(Collectors.toList());
     }


  
	
	
}
