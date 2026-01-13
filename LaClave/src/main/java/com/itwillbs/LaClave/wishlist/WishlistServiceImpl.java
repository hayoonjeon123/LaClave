package com.itwillbs.LaClave.wishlist;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.LaClave.Category.ItemRepository;
import com.itwillbs.LaClave.Member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService{
	
	private final WishlistRepository wishlistRepository;
	
	@Override
	public List<Wishlist> getWishlistBymember(Integer memberIdx) {
		
		return wishlistRepository.findByMemberIdxOrderByWishlistDateDesc(memberIdx);
		
	}

	@Transactional
	@Override
	public void removeWishlist(Integer memberIdx, Integer productIdx) {
	    wishlistRepository.deleteByMemberIdxAndProductIdx(memberIdx, productIdx);
	}
}
