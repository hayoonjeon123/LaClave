package com.itwillbs.LaClave.PayMent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwillbs.LaClave.Member.Member;
import com.itwillbs.LaClave.Orders.Orders;
import com.itwillbs.LaClave.Orders.OrdersDetail;
import com.itwillbs.LaClave.Orders.OrdersRepository;
import com.itwillbs.LaClave.memberaddress.MemberAddressRepository;
import com.itwillbs.LaClave.memberaddress.Memberaddress;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final OrdersRepository ordersRepository;
    private final MemberAddressRepository memberAddressRepository;

    /**
     * 주문 생성 (배송지 정보 복사 포함)
     */
    public String createOrder(Member member, OrderCreateRequestDto dto) {
        // 1. 배송지 조회
        Memberaddress addr = memberAddressRepository.findById(dto.getAddrIdx())
                .orElseThrow(() -> new RuntimeException("배송지 없음"));

        // 2. 주문 엔티티 생성 (Builder 활용)
        Orders order = dto.toOrderEntity(member, addr, generateOrderNo());

        // 3. 주문 상세 생성 및 연결
        List<OrdersDetail> details = dto.getOrderItems().stream()
                .map(item -> item.toDetailEntity(order))
                .collect(Collectors.toList());
        
        order.setOrderDetails(details);

        // 4. 저장 후 주문번호 반환
        return ordersRepository.save(order).getOrderNo();
    }

    private String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) 
               + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
