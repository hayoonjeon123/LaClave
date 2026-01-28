package com.itwillbs.LaClave.image;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwillbs.LaClave.orders.Orders;

public interface ImageRepository extends JpaRepository<Image, Long> {
	
	
    Optional<Image> findByTargetTypeAndTargetIdx(String targetType, Long targetIdx);
    
    Optional<Image> findFirstByTargetCodeAndTargetTypeAndTargetIdx(
    	    String targetCode,
    	    String targetType,
    	    Integer targetIdx
    	);

}
