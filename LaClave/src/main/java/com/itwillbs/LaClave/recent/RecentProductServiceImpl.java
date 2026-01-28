package com.itwillbs.LaClave.recent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.image.Image;
import com.itwillbs.LaClave.image.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecentProductServiceImpl  implements RecentProductService{
	
	private final RecentProductRepository recentProductRepository;
	private final ImageRepository imageRepository;
	
	@Override
	public List<RecentProductDto> getRecentProductsBymember(Long memberIdx) {
	    return recentProductRepository.findRecentProductsWithPrice(memberIdx);
	}

	
    // ✅ 최근 본 상품 등록 / 갱신
    public void addOrUpdateRecent(Long memberIdx, Long productIdx) {

        RecentProduct recent = recentProductRepository
                .findByMemberIdxAndProductIdx(memberIdx, productIdx)
                .orElseGet(() -> {
                    RecentProduct r = new RecentProduct();
                    r.setMemberIdx(memberIdx);
                    r.setProductIdx(productIdx);
                    return r;
                });

        recent.setViewedAt(LocalDateTime.now());
        recentProductRepository.save(recent); // ✅ Entity만 save
    }
	// 최근본상품 조회?
    @Transactional(readOnly = true)
    public List<RecentProductDto> getRecentProducts(Long memberIdx) {

        List<RecentProductDto> list =
            recentProductRepository.findRecentProductsWithPrice(memberIdx);

        list.forEach(dto -> {
            String imageUrl = imageRepository
                .findFirstByTargetCodeAndTargetTypeAndTargetIdx(
                    "img_01",                    // targetCode
                    "PRODUCT",                  // targetType
                    dto.getProductIdx().intValue()
                )
                .map(Image::getImageUrl)
                .orElse("default_image_url");

            dto.setProductImageUrl(imageUrl);
        });

        return list;
    }
    
    @Transactional
    public void addRecentProduct(Long memberIdx, Long productIdx) {
        recentProductRepository
            .findByMemberIdxAndProductIdx(memberIdx, productIdx)
            .ifPresentOrElse(
                recent -> recent.updateViewedAt(),
                () -> recentProductRepository.save(
                    RecentProduct.create(memberIdx, productIdx)
                )
            );
    }
    
    
    
}
