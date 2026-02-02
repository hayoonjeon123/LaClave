package com.itwillbs.LaClave.cart;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.itwillbs.LaClave.category.Category;
import com.itwillbs.LaClave.category.CategoryRepository;
import com.itwillbs.LaClave.category.Item;
import com.itwillbs.LaClave.category.ItemRepository;
import com.itwillbs.LaClave.category.ProductOption;
import com.itwillbs.LaClave.member.Member;
import com.itwillbs.LaClave.member.MemberRepository;

import com.itwillbs.LaClave.image.Image;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartService {
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    // 장바구니 담기
    @Transactional
    public void addCart(CartRequestDto dto, String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        Long mIdx = member.getMemberIdx();

        Cart cart = cartRepository.findByMemberIdx(mIdx)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setMemberIdx(mIdx);
                    return cartRepository.save(newCart);
                });

        Item item = itemRepository.findById(dto.getProductIdx())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Long foundColorCode = null;
        Long foundSizeCode = null;

        if (item.getOptions() != null) {
        	log.info("상품 옵션 개수:", item.getOptions().size() );

            for (ProductOption option : item.getOptions()) {
                boolean colorMatch = false;
                boolean sizeMatch = false;

                if (option.getColorCategory() != null) {
                    Category colorCat = option.getColorCategory();
                    String hex = colorCat.getCodeDesc();
                    String code = colorCat.getCode();
                    log.info("DB 색상 - code:",  code , ", hex: " , hex);

                    if ((hex != null && hex.equalsIgnoreCase(dto.getColor()))
                            || (code != null && code.equalsIgnoreCase(dto.getColor()))) {
                        colorMatch = true;
                    }
                }

                if (option.getSizeCategory() != null) {
                    Category sizeCat = option.getSizeCategory();
                    String code = sizeCat.getCode();
                    log.info("DB 사이즈 - code:", code);

                    if (code != null && code.equalsIgnoreCase(dto.getSize())) {
                        sizeMatch = true;
                    }
                }

                if (colorMatch && sizeMatch) {
                    foundColorCode = option.getColorCategory().getCommonIdx();
                    foundSizeCode = option.getSizeCategory().getCommonIdx();
                    log.info("옵션 매칭 완료 - colorCode:",foundColorCode , ", sizeCode: " , foundSizeCode);
                    break;
                }
            }
        }

        if (foundColorCode == null || foundSizeCode == null) {
            throw new RuntimeException("유효하지 않은 옵션입니다. [color: " + dto.getColor() + ", size: " + dto.getSize() + "]");
        }

        // 5. 중복 확인 및 저장
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartAndProductIdxAndColorCodeAndSizeCode(
                        cart, dto.getProductIdx(), foundColorCode, foundSizeCode);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .productIdx(dto.getProductIdx())
                    .colorCode(foundColorCode)
                    .sizeCode(foundSizeCode)
                    .quantity(dto.getQuantity())
                    .price(dto.getPrice())
                    .discountPrice(dto.getDiscountPrice())
                    .build();
            cartItemRepository.save(newItem);
        }
    }

    // 장바구니 목록 조회
    @Transactional
    public java.util.List<CartResponseDto> getCartItems(String memberId) {

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Cart cart = cartRepository.findByMemberIdx(member.getMemberIdx())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setMemberIdx(member.getMemberIdx());
                    return cartRepository.save(newCart);
                });

        return cart.getCartItems().stream().map(item -> {

            Item product = itemRepository.findById(item.getProductIdx()).orElse(null);
            Category color = categoryRepository.findById(item.getColorCode()).orElse(null);
            Category size = categoryRepository.findById(item.getSizeCode()).orElse(null);

            String imageUrl = "/images/no-image.png";
            if (product != null && product.getImages() != null && !product.getImages().isEmpty()) {
                String foundMainUrl = null;
                for (Image img : product.getImages()) {
                    String url = img.getUrl();
                    if (url == null || url.isEmpty()) continue;

                    String targetType = img.getTargetType();
                    String targetCode = img.getTargetCode();

                    // 1. 리뷰 이미지 필터링 (대소문자 무시 + URL 포함 여부 체크)
                    if ("REVIEW".equalsIgnoreCase(targetType) || 
                        "img_04".equalsIgnoreCase(targetCode) || 
                        url.toLowerCase().contains("/review/") ||
                        url.toLowerCase().contains("_review")) {
                        continue;
                    }

                    // /images/ 접두사 처리
                    if (!url.startsWith("http") && !url.startsWith("/images/")) {
                        url = "/images/" + url;
                    }

                    // 2. img_01(대표 이미지) 우선 순위
                    if ("img_01".equalsIgnoreCase(targetCode)) {
                        foundMainUrl = url;
                        break; // 대표 이미지 찾으면 즉시 종료
                    }
                    
                    // 3. 대표 이미지가 없으면 첫 번째 유효한 상품 이미지를 보관
                    if (foundMainUrl == null) {
                        foundMainUrl = url;
                    }
                }
                
                if (foundMainUrl != null) {
                    imageUrl = foundMainUrl;
                }
            }
            CartResponseDto dto = CartResponseDto.builder()
                    .cartItemIdx(item.getCartItemIdx())
                    .productIdx(item.getProductIdx())
                    .productName(product != null ? product.getProductName() : "Unknown")
                    .color(new CartResponseDto.OptionInfo(
                    	    color != null ? color.getCommonIdx() : null, 
                    	    	    color != null ? color.getCode() : "Unknown"
                    	    	)) 
                    	    	.size(new CartResponseDto.OptionInfo(
                    	    	    size != null ? size.getCommonIdx() : null, 
                    	    	    size != null ? size.getCode() : "Unknown"
                    	    	))
                    .price(item.getPrice())
                    .quantity(item.getQuantity())
                    .imageUrl(imageUrl)
                    .build();

            return dto;
        }).toList();
    }

    // 장바구니 삭제
    @Transactional
    public void deleteCartItem(Long cartItemIdx, String memberId) {
        CartItem cartItem = cartItemRepository.findById(cartItemIdx)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        // 회원 소유한 장바구니 품목인지 확인
        if (!cartItem.getCart().getMemberIdx().equals(member.getMemberIdx())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

    cartItemRepository.delete(cartItem);
    }

    // 장바구니 수량 수정
    @Transactional
    public void updateCartItemQuantity(Long cartItemIdx, int quantity, String memberId) {
        CartItem cartItem = cartItemRepository.findById(cartItemIdx)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        // 회원 소유한 장바구니 품목인지 확인
        if (!cartItem.getCart().getMemberIdx().equals(member.getMemberIdx())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        cartItem.setQuantity(quantity);
    }
}
