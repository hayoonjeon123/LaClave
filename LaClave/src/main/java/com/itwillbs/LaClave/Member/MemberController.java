package com.itwillbs.LaClave.Member;

import java.time.LocalDateTime;
import java.util.Map;

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

import com.itwillbs.LaClave.Mail.MailService;

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

	private final MemberRepository memebRepository;
	
	private final MailService mailService;
	

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

	//회원가입
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
	
	// 1. 아이디 찾기 요청
    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@RequestBody MemberDTO dto) {
        try {
            // 이름과 이메일만 사용
            String foundId = memberService.findId(dto.getMemberName(), dto.getEmail());
            return ResponseEntity.ok("찾으신 아이디는 [" + foundId + "] 입니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. 비밀번호 찾기 요청 (임시 비번 발급)
    @PostMapping("/find-pw")
    public ResponseEntity<?> findPw(@RequestBody MemberDTO dto) {
        try {
            // 아이디, 이름, 이메일 모두 사용
            String tempPw = memberService.findPw(dto.getMemberId(), dto.getMemberName(), dto.getEmail());
            return ResponseEntity.ok("임시 비밀번호가 발급되었습니다: [" + tempPw + "]");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
 // 1. 인증번호 발송 버튼 클릭 시
    @PostMapping("/email-send")
    public ResponseEntity<?> sendEmail(@RequestBody MemberDTO dto) {
        mailService.sendAuthCode(dto.getEmail());
        return ResponseEntity.ok("인증번호가 발송되었습니다.");
    }

    // 2. 인증번호 확인 버튼 클릭 시
    @PostMapping("/email-verify")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> request) {
        boolean isOk = mailService.verifyCode(request.get("email"), request.get("authCode"));
        if (isOk) {
            return ResponseEntity.ok("인증 성공!");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 틀렸습니다.");
        }
    }
    
    
    @PostMapping("/save-ai-info")
    public String saveAiInfo(@RequestBody AiInfoRequest request) {
        memberService.saveOrUpdateAiProfile(request);
        return "저장 완료";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
