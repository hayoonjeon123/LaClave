package com.itwillbs.LaClave.Member;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

//@RestController("/auth") 
@Controller
@RequestMapping
@RequiredArgsConstructor
@Log4j2
public class MemberController {

	@Autowired
	private MemberService memberService;

	// 테스트용 회원 저장
	@GetMapping("/member/test")
	@ResponseBody
	public String insertTest() {

		Member m = new Member();
		m.setMemberName("컨트롤러테스트");
		m.setMemberId("controller01");
		m.setMemberPw("1234");

		memberService.saveMember(m);

		return "저장 완료! PK = " + m.getMemberIdx();
	}

	@GetMapping("/login")
	public String login() {
		return "Login";
	}

	@GetMapping("/signup")
	public String signup() {
		return "SignUp";
	}
	
	@PostMapping("/signup")
    public String signup(MemberDTO dto) {

		System.out.println("컨트롤러 진입 완료: " + dto.getMemberId());
	    
	    Member member = new Member();
	    member.setMemberId(dto.getMemberId());
	    member.setMemberPw(dto.getMemberPw());
	    member.setMemberName(dto.getMemberName());
	    member.setNickname(dto.getNickname());

	    member.setGender(dto.getGender());
	    member.setPostCode(dto.getPostCode());
	    member.setAddress(dto.getAddress());
	    member.setAddressDetail(dto.getAddressDetail());
	    member.setBirth(dto.getBirth());
	    member.setEmail(dto.getEmail());

	    member.setSignupDate(dto.getSignupDate());
	    member.setUpdatedAt(dto.getUpdateAt());

	    member.setMemberStatus(dto.getMemberStatus());
	    member.setMailAuthStatus(dto.getMailAuthStatus());
	    member.setMarketingAgree(dto.getMarketingAgree());
	    member.setPoint(dto.getPoint());
	    
	    member.setSignupDate(LocalDateTime.now()); 
	    member.setUpdatedAt(LocalDateTime.now());

	    member.setMemberRole("ROLE_USER");
	    
	    memberService.saveMember(member); 
		
		return "redirect:/login";
    }

}
