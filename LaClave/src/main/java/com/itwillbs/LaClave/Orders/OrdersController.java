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
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    //http://localhost:8080/api/my/orders?memberIdx=1
    // 마이페이지 주문내역 조회
    @GetMapping("/orders")
    public ResponseEntity<List<MyOrderResponseDto>> getMyOrders(
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        List<MyOrderResponseDto> orders = ordersService.getMyOrderList(user);
        return ResponseEntity.ok(orders);
    }
}