package com.roofiahmad.springstoreapp.common;

import com.roofiahmad.springstoreapp.services.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UploadService {
    private final MinioService minioService;

    public Map<String, String> upload(MultipartFile file) throws Exception {
        minioService.uploadFile(file);

        String fileName = file.getOriginalFilename();
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName)
                .toUriString();

        Map<String, String> uploadResponse = new HashMap<>();
        uploadResponse.put("fileUrl", fileUrl);
        uploadResponse.put("fileName", fileName);
        return uploadResponse;
    }
}
