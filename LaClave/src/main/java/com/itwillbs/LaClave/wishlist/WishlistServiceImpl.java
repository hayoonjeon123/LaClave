package com.itwillbs.LaClave.wishlist;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.Category.Item;
import com.itwillbs.LaClave.Category.ItemRepository;
import com.itwillbs.LaClave.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ItemRepository itemRepository;

    /**
     * 로그인 회원 찜 목록 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<WishlistResponseDto> getWishlistBymember(Integer memberIdx) {

        return wishlistRepository
            .findByMemberIdxOrderByWishlistDateDesc(memberIdx)
            .stream()
            .map(wishlist -> {

                Item item = itemRepository
                    .findById(wishlist.getProductIdx().longValue())
                    .orElse(null);

                return WishlistResponseDto.builder()
                    .wishlistIdx(wishlist.getWishlistIdx())
                    .productIdx(wishlist.getProductIdx())
                    .productName(
                        item != null ? item.getProductName() : "삭제된 상품"
                    )
                    .imageUrl(
                        item != null && !item.getImages().isEmpty()
                            ? item.getImages().iterator().next().getUrl()
                            : "/images/no-image.png"
                    )
                    .wishlistDate(wishlist.getWishlistDate())
                    .build();
            })
            .toList();
    }
    
    // 찜추가 
    @Override
    @Transactional
    public WishlistResponseDto addWishlist(
            Integer productIdx,
            CustomUserDetails user) {

        Long memberIdx = user.getMemberIdx();

        // 이미 찜했는지 체크
        if (wishlistRepository.existsByMemberIdxAndProductIdx(memberIdx, productIdx)) {
            throw new IllegalStateException("이미 찜한 상품입니다.");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setMemberIdx(memberIdx);
        wishlist.setProductIdx(productIdx);
        wishlist.setWishlistDate(LocalDateTime.now());

        Wishlist saved = wishlistRepository.save(wishlist);

        return WishlistResponseDto.from(saved);
    }

    /**
     * 찜 삭제 (하트 취소)
     */
    @Override
    @Transactional
    public void removeWishlist(Integer memberIdx, Integer productIdx) {
        wishlistRepository.deleteByMemberIdxAndProductIdx(memberIdx, productIdx);
    }
}
