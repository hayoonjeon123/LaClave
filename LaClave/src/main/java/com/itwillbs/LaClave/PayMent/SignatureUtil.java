package com.itwillbs.LaClave.PayMent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * KG이니시스 결제 보안을 위한 암호화 클래스
 * 결제 요청 시 데이터 위변조 방지를 위해 Signature를 생성
 */
public class SignatureUtil {

    // 이니시스 결제 요청용 시그니처 생성
    // 공식 문서: oid=값&price=값&timestamp=값 형식으로 SHA256 해시
    public static String getSignature(String signKey, String oid, Integer price, String timestamp) {
        // signKey는 사용하지 않음 (이니시스 표준결제 방식)
        String target = "oid=" + oid + "&price=" + price + "&timestamp=" + timestamp;
        return encryptSHA256(target);
    }

    // SHA-256 암호화 로직
    public static String encryptSHA256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte[] byteData = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없습니다.", e);
        }
    }
}
