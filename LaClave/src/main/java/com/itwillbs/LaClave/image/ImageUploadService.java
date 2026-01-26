package com.itwillbs.LaClave.image;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploadService {

    // ✅ 서버 전용 저장소 (다른 컴퓨터에서도 접근 가능하게 서버에만 저장)
    private static final String UPLOAD_DIR = "C:/LaClave/uploads/review/";

    /**
     * 이미지 업로드
     * - UUID + 원본 파일명으로 중복 방지
     * - 서버에 저장
     * - 프론트에서 접근 가능한 URL 반환
     */
    public String upload(MultipartFile file) {
        try {
            // 저장 폴더가 없으면 생성
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // UUID + 원본 파일명
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File saveFile = new File(dir, fileName);

            // 실제 서버 폴더에 저장
            file.transferTo(saveFile);

            // DB에는 이 URL만 저장
            return "/uploads/review/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }
}
