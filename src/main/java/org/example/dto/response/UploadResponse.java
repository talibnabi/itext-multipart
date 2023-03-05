package org.example.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UploadResponse {
    private final String fileName;
    private final String fullName;
    private final String dateOfBirth;

}
