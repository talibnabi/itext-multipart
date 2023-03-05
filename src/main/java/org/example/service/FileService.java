package org.example.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.InputStream;

public interface FileService {
    String storeFile(MultipartFile file);

    boolean deleteFile(String fileName);

    Resource loadFile(String filename);

    void generateAndStorePDF();

    void storeGeneratedFile(InputStream bis);

}

