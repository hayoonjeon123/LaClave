package com.itwillbs.LaClave.recent;

import java.util.List;

public interface RecentProductService {
	
	List<RecentProduct> getRecentProductsBymember(Integer memberIdx);

}
