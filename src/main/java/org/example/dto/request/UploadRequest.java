package org.example.dto.request;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UploadRequest {
    private MultipartFile file;
    private LocalDate fileUploadDate;
}
