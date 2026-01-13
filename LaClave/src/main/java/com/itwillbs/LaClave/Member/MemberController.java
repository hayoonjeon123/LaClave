package com.itwillbs.LaClave.Member;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController("/")
@RequestMapping
@RequiredArgsConstructor
@Log4j2
public class MemberController {

	@Autowired
	private final MemberService memberService;

	private final BCryptPasswordEncoder passwordEncoder;
	
	private final ModelMapper modelMapper;

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
	public ResponseEntity<?> signup(@RequestBody MemberDTO dto) {
		log.info("회원가입 시도 아이디: {}", dto.getMemberId());
        
        try {
            // 1. DTO -> Entity 자동 변환 (ModelMapper)
            Member member = modelMapper.map(dto, Member.class);

            // 2. 서비스 호출 및 저장
            memberService.saveMember(member);

            return ResponseEntity.ok("회원가입 성공");
            
        } catch (RuntimeException e) {
            // 중복 아이디 등 비즈니스 예외 처리
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("서버 오류: ", e);
            return ResponseEntity.internalServerError().body("서버 오류가 발생했습니다.");
        }
    }
}
