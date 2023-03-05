package org.example.dto.request;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadRequest {
    private MultipartFile file;
    private String fullName;
    private String dateOfBirth;
}
