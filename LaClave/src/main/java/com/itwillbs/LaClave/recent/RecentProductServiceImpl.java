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

    @Override
    @Transactional(readOnly = true)
    public List<RecentProductDto> getRecentProductsBymember(Long memberIdx) {

        List<RecentProductDto> list =
            recentProductRepository.findRecentProductsWithPrice(memberIdx);

        list.forEach(dto -> {
            String imageUrl = imageRepository
                .findFirstByTargetCodeAndTargetTypeAndTargetIdx(
                    "img_01",
                    "PRODUCT",
                    dto.getProductIdx().intValue()
                )
                .map(Image::getImageUrl)
                .orElse(null);

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
