package org.example.service.impl;

import lombok.SneakyThrows;
import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.example.service.FileService;
import org.example.util.GeneratePDF;
import org.example.util.MyMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.springframework.core.env.Environment;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private PersonRepository personRepository;


    private final Path fileStorageLocation;

    @Value("${app.file.upload-dir}")
    private String uploadDir;

    public FileServiceImpl(Environment environment) {
        this.fileStorageLocation = Paths.get(environment.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    @Override
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName =
                file.getResource().getFilename();

        try {
            // Check if the filename contains invalid characters
            assert fileName != null;
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public boolean deleteFile(String fileName) {
        Path filePath = Paths.get(uploadDir + File.separator + fileName);
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Resource loadFile(String filename) {
        Path filePath = fileStorageLocation.resolve(filename);

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Failed to load file: " + filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file: " + filename, e);
        }
    }

    @Override
    public void generateAndStorePDF(Long id) {
        Person person=personRepository.findById(id).orElseThrow();
        ByteArrayInputStream bis = GeneratePDF.generate(person);
        storeGeneratedFile(bis, person);
    }


    @SneakyThrows
    @Override
    public void storeGeneratedFile(InputStream bis, Person person) {
        String fileName = person.getName() + ".pdf";
        String contentType = "Content-Disposition";
        byte[] contentBytes = StreamUtils.copyToByteArray(bis);
        MultipartFile multipartFile = new MyMultipartFile(contentBytes, fileName, contentType);
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public void savePerson(Person person) { //TODO FOR DEMO
        personRepository.save(person);
    }
}

