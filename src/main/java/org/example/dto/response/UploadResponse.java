package org.example.dto.response;


import java.time.LocalDate;

public record UploadResponse(String fileName, LocalDate fileUploadDate) {
}
