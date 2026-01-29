package com.itwillbs.LaClave.recent;

import java.util.List;

import com.itwillbs.LaClave.config.CustomUserDetails;

public interface RecentProductService {

    List<RecentProductDto> getRecentProductsBymember(Long memberIdx);

    void addOrUpdateRecent(Long memberIdx, Long productIdx);

    void addRecentProduct(Long memberIdx, Long productIdx);
    
}