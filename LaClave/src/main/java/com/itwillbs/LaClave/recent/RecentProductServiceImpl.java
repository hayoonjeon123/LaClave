package com.itwillbs.LaClave.recent;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecentProductServiceImpl  implements RecentProductService{
	
	private final RecentProductRepository recentProductRepository;
	
	@Override
	public List<RecentProduct> getRecentProductsBymember(Integer memberIdx){
		return recentProductRepository.findByMemberIdxOrderByViewedAtDesc(memberIdx);
	}
	
}
