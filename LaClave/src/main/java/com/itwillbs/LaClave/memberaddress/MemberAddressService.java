package com.itwillbs.LaClave.memberaddress;

import java.util.List;

import com.itwillbs.LaClave.Config.CustomUserDetails;

public interface MemberAddressService {

	List<MemberAddressDto> getMyAddressList(CustomUserDetails user);
	
	MemberAddressDto get(Long addressIdx, CustomUserDetails user);
	
	 void modify(Memberaddress updatedAddress, CustomUserDetails user);
	 
	 void remove(Long addressIdx, CustomUserDetails user);
	 
	  Long register(Memberaddress memberaddress, Long memberIdx); 

}
