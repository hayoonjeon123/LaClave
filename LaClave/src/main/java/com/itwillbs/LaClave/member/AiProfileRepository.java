package com.itwillbs.LaClave.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiProfileRepository extends JpaRepository<AiProfile, Long> {
	
}