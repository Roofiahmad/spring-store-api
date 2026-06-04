package com.roofiahmad.springstoreapp.common;

import com.roofiahmad.springstoreapp.services.MinioService;
import io.minio.StatObjectResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/files")
public class UploadController {

    private final MinioService minioService;
    private final UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        try {
            var uploadResponse = uploadService.upload(file);
            var fileUrl = uploadResponse.get("fileUrl");
            var fileName = uploadResponse.get("fileName");

            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            response.put("fileName", fileName);
            response.put("status", "success");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("error", "Upload failed: " + e.getMessage()));
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename) {
        try {
            InputStream stream = minioService.getFileStream(filename);
            StatObjectResponse metadata = minioService.getFileMetadata(filename);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;")
                    .contentType(MediaType.parseMediaType(metadata.contentType()))
                    .body(new InputStreamResource(stream));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ApiResponseWrapper(false,"File not found or storage error: " + filename, false));
        }
    }
}
