package com.itwillbs.LaClave.Orders;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/my") // 프록시가 /api를 제거하므로
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    // http://localhost:8080/api/my/orders?memberIdx=1
    // 마이페이지 주문내역 조회
    // @GetMapping("/orders")
    // public ResponseEntity<List<MyOrderResponseDto>> getMyOrders(
    // @AuthenticationPrincipal CustomUserDetails user
    // ) {
    //
    // List<MyOrderResponseDto> orders = ordersService.getMyOrderList(user);
    // System.out.println("Logged in memberIdx: " + user.getMemberIdx());
    // System.out.println("user memberIdx = " + user.getMemberIdx());
    // return ResponseEntity.ok(orders);
    // }
    @GetMapping("/orders")
    public List<MyOrderResponseDto> getOrders(@AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) {
            System.out.println("CustomUserDetails is NULL!");
            return List.of(); // 빈 리스트
        }
        List<MyOrderResponseDto> orders = ordersService.getMyOrderList(user);
        System.out.println("Logged in memberIdx: " + user.getMemberIdx());
        // 여기서 몇 개 가져왔는지 확인
        System.out.println("orders.size() = " + orders.size());
        return ordersService.getMyOrderList(user);

    }

}