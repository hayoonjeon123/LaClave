package com.itwillbs.LaClave.PayMent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PayMent, Long> {
    // 기본적으로 save(), findById() 등은 JpaRepository가 제공합니다.
}
