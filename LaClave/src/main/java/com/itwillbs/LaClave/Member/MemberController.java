package com.itwillbs.LaClave.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Controller
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

	
}
