package org.zerock.univFood.contoller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.zerock.upload.path}") // application.properties의 변수
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public void uploadFile(MultipartFile[] uploadfiles){    // 동시에 여러 개의 파일 정보 처리 위해 MultipartFile[]
        for(MultipartFile uploadFile: uploadfiles){         // 실제 파일 이름(IE나 Edge는 전체 경로가 들어옴)
            String originalName = uploadFile.getOriginalFilename();     // 업로드 하는 파일의 이름 파악
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
            log.info("fileName: " + fileName);
        }//for
    }

    // 파일 저장 시 고려할 점
    // 업로드된 확장자가 이미지만 가능하도록 검사
    // 동일한 이름의 파일이 업로드 된다면 기존 파일을 덮어쓰는 문제
    // 업로드된 파일을 저장하는 폴더의 용량
}
