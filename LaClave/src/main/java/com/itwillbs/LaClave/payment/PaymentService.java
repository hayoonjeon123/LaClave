package com.itwillbs.LaClave.payment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwillbs.LaClave.delivery.DeliveryRepository;
import com.itwillbs.LaClave.delivery.MyDelivery;
import com.itwillbs.LaClave.member.Member;
import com.itwillbs.LaClave.memberaddress.MemberAddressRepository;
import com.itwillbs.LaClave.memberaddress.Memberaddress;
import com.itwillbs.LaClave.orders.Orders;
import com.itwillbs.LaClave.orders.OrdersDetail;
import com.itwillbs.LaClave.orders.OrdersRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final OrdersRepository ordersRepository;
    private final MemberAddressRepository memberAddressRepository;
    private final DeliveryRepository deliveryRepository;

    // 주문 생성
    public String createOrder(Member member, OrderCreateRequestDto dto) {
        Memberaddress addr = memberAddressRepository.findById(dto.getAddrIdx())
                .orElseThrow(() -> new RuntimeException("배송지 없음"));

        Orders order = dto.toOrderEntity(member, addr, generateOrderNo());

        List<OrdersDetail> details = dto.getOrderItems().stream()
                .map(item -> item.toDetailEntity(order))
                .collect(Collectors.toList());
        
        order.setOrderDetails(details);
        

        return ordersRepository.save(order).getOrderNo();
    }

    private String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) 
               + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
    

}
