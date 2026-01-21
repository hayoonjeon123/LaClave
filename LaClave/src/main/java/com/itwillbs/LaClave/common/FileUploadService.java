package com.itwillbs.LaClave.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    private final String uploadDir = "/path/to/upload/images"; // 서버 저장 경로, 실제 환경에 맞게 변경

    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String saveFileName = UUID.randomUUID().toString() + ext;

            Path path = Paths.get(uploadDir, saveFileName);
            Files.createDirectories(path.getParent());

            file.transferTo(path.toFile());

            // 반환 URL (브라우저 접근 가능하게 매핑 필요)
            return "/images/" + saveFileName;

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }
    
}
