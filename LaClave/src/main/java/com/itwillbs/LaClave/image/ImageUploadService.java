package com.itwillbs.LaClave.image;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploadService {

    private static final String UPLOAD_DIR = "C:/upload/review/";

    public String upload(MultipartFile file) {
        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File saveFile = new File(dir, fileName);

            file.transferTo(saveFile);

            // 프론트에서 접근할 URL
            return "/uploads/review/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }
}
