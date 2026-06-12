package com.roofiahmad.springstoreapp.storage;

import com.roofiahmad.springstoreapp.storage.minio.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UploadService {
    private final MinioService minioService;

    public Map<String, String> upload(MultipartFile file) throws Exception {
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String uniqueFileName = UUID.randomUUID().toString() + extension;

        minioService.uploadFile(file, uniqueFileName);

        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(uniqueFileName)
                .toUriString();

        Map<String, String> uploadResponse = new HashMap<>();
        uploadResponse.put("fileUrl", fileUrl);
        uploadResponse.put("uniqueName", uniqueFileName);
        uploadResponse.put("originalName", originalName);

        return uploadResponse;
    }
}
