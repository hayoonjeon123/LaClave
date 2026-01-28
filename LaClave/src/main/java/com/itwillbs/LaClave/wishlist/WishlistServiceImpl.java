package com.itwillbs.LaClave.wishlist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.cart.ItemImage;
import com.itwillbs.LaClave.category.Item;
import com.itwillbs.LaClave.category.ItemRepository;
import com.itwillbs.LaClave.config.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WishlistResponseDto> getWishlistBymember(Long memberIdx) {
        log.info("WishlistServiceImpl - getWishlistBymember 호출: {}", memberIdx);
        List<Wishlist> wishlists = wishlistRepository.findByMemberIdxOrderByWishlistDateDesc(memberIdx);
        log.info("조회된 찜 개수: {}", wishlists.size());

        return wishlists.stream()
                .map(wishlist -> {
                    Item item = itemRepository
                            .findById(wishlist.getProductIdx().longValue())
                            .orElse(null);

                    String imageUrl = "/images/no-image.png";
                    if (item != null && item.getImages() != null && !item.getImages().isEmpty()) {
                        // 첫 번째 유효한 이미지 URL 찾기
                        for (ItemImage img : item.getImages()) {
                            if (img.getUrl() != null && !img.getUrl().isEmpty()) {
                                imageUrl = img.getUrl();
                                break;
                            }
                        }
                    }

                    return WishlistResponseDto.builder()
                            .wishlistIdx(wishlist.getWishlistIdx())
                            .productIdx(wishlist.getProductIdx())
                            .productName(item != null ? item.getProductName() : "삭제된 상품")
                            .imageUrl(imageUrl)
                            .wishlistDate(wishlist.getWishlistDate())
                            .build();
                })
                .toList();
    }

    @Override
    @Transactional
    public WishlistResponseDto addWishlist(Integer productIdx, CustomUserDetails user) {
        Long memberIdx = user.getMemberIdx();
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

    @Override
    @Transactional
    public void removeWishlist(Long memberIdx, Integer productIdx) {
        wishlistRepository.findByMemberIdxAndProductIdx(memberIdx, productIdx)
                .ifPresent(wishlist -> {
                    wishlistRepository.delete(wishlist);
                    wishlistRepository.flush(); 
                });
    }

    @Override
    @Transactional
    public boolean toggleWishlist(Long memberIdx, Integer productIdx) {
        Optional<Wishlist> existing = wishlistRepository.findByMemberIdxAndProductIdx(memberIdx, productIdx);
        if (existing.isPresent()) {
            // 이미 존재하면 DB에서 확실하게 삭제
            wishlistRepository.delete(existing.get());
            wishlistRepository.flush();
            return false;
        } else {
            // 없으면 새로 생성하여 저장
            Wishlist wishlist = new Wishlist();
            wishlist.setMemberIdx(memberIdx);
            wishlist.setProductIdx(productIdx);
            wishlist.setWishlistDate(LocalDateTime.now());
            wishlistRepository.save(wishlist);
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkStatus(Long memberIdx, Integer productIdx) {
        return wishlistRepository.existsByMemberIdxAndProductIdx(memberIdx, productIdx);
    }
}
