package com.itwillbs.LaClave.Mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    // 인증번호를 임시로 저장할 공간 (실무에선 Redis를 권장하지만, 우선 세션이나 Map으로 테스트하세요)
    private final Map<String, String> authCodeMap = new HashMap<>();

    public void sendAuthCode(String email) {
        // 1. 6자리 랜덤 인증번호 생성
        String authCode = String.valueOf((int)(Math.random() * 899999) + 100000);
        
        // 2. 메일 내용 작성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("LaClave 인증번호입니다.");
        message.setText("인증번호: " + authCode);
        
        // 3. 메일 발송 및 인증번호 저장
        mailSender.send(message);
        authCodeMap.put(email, authCode); 
    }

    public boolean verifyCode(String email, String code) {
        return code.equals(authCodeMap.get(email));
    }
}
