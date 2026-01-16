package com.itwillbs.LaClave.Orders;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.PayMent.OrderCreateRequestDto;
import com.itwillbs.LaClave.PayMent.PayMent;
import com.itwillbs.LaClave.PayMent.PaymentApprovalRequestDto;
import com.itwillbs.LaClave.PayMent.PaymentRepository;
import com.itwillbs.LaClave.memberaddress.MemberAddressRepository;
import com.itwillbs.LaClave.memberaddress.Memberaddress;
import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    private final MemberAddressRepository memberAddressRepository;

    private final PaymentRepository paymentRepository;
    
    // 주문 내역 조회
    @Override
    @Transactional(readOnly = true)
    public List<MyOrderResponseDto> getMyOrderList(CustomUserDetails user) {
        Long memberIdx = user.getMemberIdx();

        // 1️⃣ 주문 + 상세 목록 fetch join
        List<Orders> orders = ordersRepository.findAllByMemberIdxNative(memberIdx);

        // 2️⃣ DTO 변환 (배송 정보는 Orders에서 직접 가져오기)
        return orders.stream()
                .map(MyOrderResponseDto::new) // 이제 생성자가 Orders만 받음
                .collect(Collectors.toList());
    }

    public String createOrder(Long memberIdx, OrderCreateRequestDto dto) {
        try {
            System.out.println("=== 주문 생성 시작 ===");
            System.out.println("memberIdx: " + memberIdx);
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

            Orders order = dto.toOrderEntity(memberIdx, addr, orderNo);
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

            return savedOrder.getOrderNo();
        } catch (Exception e) {
            System.err.println("=== 주문 생성 실패 ===");
            System.err.println("에러 메시지: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // 2. 결제 승인 메서드 구현
    @Override
    @Transactional
    public boolean approvePayment(PaymentApprovalRequestDto dto) {
        Orders order = ordersRepository.findByOrderNo(dto.getOrderNo())
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        PayMent payment = PayMent.builder()
                .order(order)
                .memberIdx(order.getMemberIdx())
                .totalPrice(dto.getAmount())
                .payStatus("74")
                .payWay("157") //
                .payType("158")
                .payReference("159")
                .externalTransaction(dto.getExternalTransaction()) //
                .build();

        paymentRepository.save(payment);
        order.setOrdersStatus(75L); // 결제 완료 상태 (공통코드 PK)

        return true;
    }

    // 주문번호 생성 로직
    private String generateOrderNo() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "-" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }

}