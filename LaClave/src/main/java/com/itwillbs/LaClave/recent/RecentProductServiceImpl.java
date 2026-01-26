package com.itwillbs.LaClave.recent;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecentProductServiceImpl  implements RecentProductService{
	
	private final RecentProductRepository recentProductRepository;
	
	@Override
	public List<RecentProduct> getRecentProductsBymember(Integer memberIdx){
		return recentProductRepository.findByMemberIdxOrderByViewedAtDesc(memberIdx);
	}
	

    // 회원 최근 본 상품 조회
    @Transactional(readOnly = true)
    public List<RecentProductDto> getRecentProducts(Integer memberIdx) {
        return recentProductRepository
                .findTop5ByMemberIdxOrderByViewedAtDesc(memberIdx)
                .stream()
                .map(r -> new RecentProductDto(r.getProductIdx(), r.getViewedAt()))
                .collect(Collectors.toList());
    }
	
    // 최근 본 상품 등록/업데이트
    @Transactional
    public void addOrUpdateRecent(Integer memberIdx, Integer productIdx) {
        // 이미 존재하는지 확인
        recentProductRepository.findByMemberIdxOrderByViewedAtDesc(memberIdx).stream()
            .filter(r -> r.getProductIdx().equals(productIdx))
            .findFirst()
            .ifPresentOrElse(
                r -> {
                    // 기존 상품이면 viewedAt 갱신
                    r.setViewedAt(java.time.LocalDateTime.now());
                    recentProductRepository.save(r);
                },
                () -> {
                    // 새 상품이면 추가
                    RecentProduct newRecent = new RecentProduct();
                    newRecent.setMemberIdx(memberIdx);
                    newRecent.setProductIdx(productIdx);
                    newRecent.setViewedAt(java.time.LocalDateTime.now());
                    recentProductRepository.save(newRecent);
                }
            );
    }
	
}
