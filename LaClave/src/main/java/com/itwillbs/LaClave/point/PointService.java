package com.itwillbs.LaClave.point;

import java.util.List;

import com.itwillbs.LaClave.Config.CustomUserDetails;

public interface PointService {
	
    List<PointDto> getMyPointList(CustomUserDetails user);
	

}
