package com.itwillbs.LaClave.Member;

import lombok.Data;

@Data
public class PasswordUpdateDto {
	
	   private String currentPassword; // 현재 비밀번호 확인용
	   private String newPassword;

}
