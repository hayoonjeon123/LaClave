package com.itwillbs.LaClave.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiProfileRepository extends JpaRepository<AiProfile, Long> {
    // memberIdx가 PK이므로 기본 제공되는 findById, save 등을 그대로 사용하면 됩니다.
	
}