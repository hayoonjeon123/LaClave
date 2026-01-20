package com.itwillbs.LaClave.memberaddress;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberAddressRepository extends JpaRepository<Memberaddress, Long> {
	
    List<Memberaddress> findByMemberIdxOrderByAddressIdxDesc(Long memberIdx);
    
    Optional<Memberaddress> findByAddressIdxAndMemberIdx(Long addressIdx, Long memberIdx);
    
    int deleteByAddressIdxAndMemberIdx(Long addressIdx, Long memberIdx);
    
    

}
