package org.example.service;

import org.example.model.Person;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.InputStream;

public interface FileService {
    String storeFile(MultipartFile file);

    Boolean deleteFile(String fileName);

    Resource loadFile(String filename);

    void generateAndStorePDF(Long id);

    void storeGeneratedFile(InputStream bis, Person person);


    void savePerson(Person person);

    Boolean isPDF(MultipartFile file);
}

