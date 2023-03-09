package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UploadResponse {
    private final String fileName;
    private final LocalDate fileUploadDate;

}
