package com.itwillbs.LaClave;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwillbs.LaClave.Orders.Orders;
import com.itwillbs.LaClave.Orders.OrdersRepository;

@SpringBootTest
class LaClaveApplicationTests {
	

    @Autowired
    private OrdersRepository ordersRepository;

	@Test
	void contextLoads() {
	}
	


}
