package com.itwillbs.LaClave.orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.commoncode.CommonCodeService;
import com.itwillbs.LaClave.image.Image;
import com.itwillbs.LaClave.image.ImageRepository;
import com.itwillbs.LaClave.member.Member;
import com.itwillbs.LaClave.memberaddress.MemberAddressRepository;
import com.itwillbs.LaClave.memberaddress.Memberaddress;
import com.itwillbs.LaClave.payment.OrderCreateRequestDto;
import com.itwillbs.LaClave.payment.PayMent;
import com.itwillbs.LaClave.payment.PaymentApprovalRequestDto;
import com.itwillbs.LaClave.payment.PaymentRepository;
import com.itwillbs.LaClave.config.CustomUserDetails;
import com.itwillbs.LaClave.delivery.DeliveryRepository;
import com.itwillbs.LaClave.delivery.MyDelivery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    private final MemberAddressRepository memberAddressRepository;

    private final PaymentRepository paymentRepository;
    
    private final CommonCodeService commonCodeService;
    
    private final ImageRepository imageRepository;
    
    private final DeliveryRepository deliveryRepository;
    
    
    private String generateTrackingNo() {
        return "TN" + System.currentTimeMillis();
    }

    // 주문 내역 조회
    @Override
    @Transactional(readOnly = true)
    public List<MyOrderResponseDto> getMyOrderList(CustomUserDetails user) {

        Long memberIdx = user.getMemberIdx();
        Long COMPLETED_STATUS = 74L;

        List<Orders> orders = ordersRepository.findAllByMemberIdxAndOrdersStatusNative(memberIdx,COMPLETED_STATUS);

        List<MyOrderResponseDto> result = orders.stream()
                .map(MyOrderResponseDto::new)
                .collect(Collectors.toList());


        // ⭐ 공통코드 변환
        result.forEach(order -> {

            order.getDetails().forEach(detail -> {

                // 옵션명
                detail.setColorName(commonCodeService.getLabel(detail.getColorCode()));
                detail.setSizeName(commonCodeService.getLabel(detail.getSizeCode()));

                // ⭐ 상품 대표 이미지
                String productImageUrl = imageRepository
                	    .findFirstByTargetCodeAndTargetTypeAndTargetIdx(
                	        "img_01",                                // targetCode
                	        "PRODUCT",                              // targetType
                	        detail.getProductIdx().intValue()      
                	    )
                	    .map(Image::getImageUrl)
                	    .orElse("default_image_url");

                detail.setProductImageUrl(productImageUrl);
            });

            // 결제 코드 변환
            order.getPayInfo().ifPresent(pay -> {
                pay.setPayWayName(pay.getPayWay() != null
                    ? commonCodeService.getLabel(pay.getPayWay().longValue()) : "-");
//                pay.setPayStatusName(pay.getPayStatus() != null
//                    ? commonCodeService.getLabel(pay.getPayStatus().longValue()) : "-");       
                pay.setPayTypeName(pay.getPayType() != null
                    ? commonCodeService.getLabel(pay.getPayType().longValue()) : "-");
            });
        });

        return result;
    }
    @Override
    public String createOrder(Member member, OrderCreateRequestDto dto) {
        try {
            System.out.println("=== 주문 생성 시작 ===");
            System.out.println("memberIdx: " + member);
            System.out.println("addrIdx: " + dto.getAddrIdx());
            System.out.println("totalPrice: " + dto.getTotalPrice());
            System.out.println("orderItems 개수: " + (dto.getOrderItems() != null ? dto.getOrderItems().size() : 0));

            // 배송지 조회
            Memberaddress addr = memberAddressRepository.findById(dto.getAddrIdx())
                    .orElseThrow(() -> {
                        System.err.println("배송지를 찾을 수 없습니다. addrIdx: " + dto.getAddrIdx());
                        return new IllegalArgumentException("배송지 정보를 찾을 수 없습니다.");
                    });

            System.out.println("배송지 조회 성공: " + addr.getAddress());

            // Orders 엔티티 생성 (빌더 사용)
            String orderNo = generateOrderNo();
            System.out.println("생성된 주문번호: " + orderNo);

            Orders order = dto.toOrderEntity(member, addr, orderNo);
            System.out.println("주문 엔티티 생성 완료 - ordersStatus: " + order.getOrdersStatus());

            // 상세 아이템 연결
            List<OrdersDetail> details = dto.getOrderItems().stream()
                    .map(item -> {
                        System.out.println("주문 상품: " + item.getProductName() + " (수량: " + item.getQuantity() + ")");
                        return item.toDetailEntity(order);
                    })
                    .collect(Collectors.toList());
            order.setOrderDetails(details);

            // 저장 후 주문번호 반환
            Orders savedOrder = ordersRepository.save(order);
            System.out.println("주문 저장 완료 - orderIdx: " + savedOrder.getOrdersIdx());
            
            
            ordersRepository.flush(); // ordersIdx 확보

            System.out.println("주문 저장 완료 - orderIdx: " + savedOrder.getOrdersIdx());
            
            

            // 🔥 배송 생성 (여기가 핵심)
            MyDelivery delivery = new MyDelivery();
            delivery.setOrderIdx(savedOrder.getOrdersIdx());
            delivery.setMemberIdx(member.getMemberIdx().intValue());
            delivery.setDeliveryStatusCommonIdx(79L);
            delivery.setStartDate(LocalDateTime.now());
            delivery.setCourier("CJ대한통운");
            delivery.setUpdatedAt(LocalDateTime.now());
            delivery.setTrackingNO(generateTrackingNo());

            System.out.println("🔥 배송 저장 직전");
            deliveryRepository.save(delivery);
            deliveryRepository.flush();
            System.out.println("🔥 배송 저장 완료");

            return savedOrder.getOrderNo();

            
        } catch (Exception e) {
            System.err.println("=== 주문 생성 실패 ===");
            System.err.println("에러 메시지: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // 결제 주문번호 찾기
    @Override
    public Orders findByOrderNo(String orderNo) {
        return ordersRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문번호입니다: " + orderNo));
    }

    // 2. 결제 승인 메서드 구현
    @Override
    @Transactional
    public boolean approvePayment(PaymentApprovalRequestDto dto) {
        System.out.println("=== approvePayment 시작: orderNo = " + dto.getOrderNo());
        try {
            Orders order = ordersRepository.findByOrderNo(dto.getOrderNo())
                    .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

            System.out.println("주문 조회 성공: ordersIdx = " + order.getOrdersIdx());

            // 결제 정보 생성 및 저장
            PayMent payment = PayMent.builder()
                    .order(order)
                    .member(order.getMember())
                    .totalPrice(dto.getAmount())
                    .payStatus(74) 
                    .payWay(157) 
                    .payType(158) 
                    .payReference(159) 
                    .externalTransaction(dto.getExternalTransaction())
                    .paymentDate(java.time.LocalDateTime.now())
                    .build();

            PayMent savedPayment = paymentRepository.save(payment);
            System.out.println("결제 정보 저장 성공: paymentIdx = " + savedPayment.getPaymentIdx());

            order.setOrdersStatus(74L); 
            ordersRepository.save(order);
            System.out.println("주문 상태 업데이트 완료: 75L");

            return true;
        } catch (Exception e) {
            System.err.println("=== 결제 승인 중 치명적 오류 발생 ===");
            System.err.println("오류 메시지: " + e.getMessage());
            e.printStackTrace();
            throw e; // 500 에러를 발생시켜 프론트에서 인지하게 함
        }
    }

    // 주문번호 생성 로직
    private String generateOrderNo() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "-" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }
    
    
    

}