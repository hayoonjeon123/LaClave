package com.itwillbs.LaClave.Orders;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.memberaddress.MemberAddressRepository;
import com.itwillbs.LaClave.memberaddress.Memberaddress;
import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final MemberAddressRepository memberAddressRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MyOrderResponseDto> getMyOrderList(CustomUserDetails user) {
        Long memberIdx = user.getMemberIdx();

        // 1️⃣ 주문 + 상세 목록 fetch join
        List<Orders> orders = ordersRepository.findAllWithDetailsByMemberIdx(memberIdx);

        // 2️⃣ 회원 기본 배송지 조회
        Memberaddress delivery = memberAddressRepository.findDefaultByMemberIdx(memberIdx);

        // 3️⃣ DTO 변환
        return orders.stream()
                     .map(order -> new MyOrderResponseDto(order, order.getOrderDetails(), delivery))
                     .collect(Collectors.toList());
    }
}