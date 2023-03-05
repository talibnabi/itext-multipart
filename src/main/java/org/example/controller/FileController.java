package org.example.controller;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.dto.request.UploadRequest;
import org.example.dto.response.UploadResponse;
import org.example.service.FileService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "file")
public class FileController {

    private final FileService fileService;


    @PostMapping("upload")
    public ResponseEntity<UploadResponse> uploadFile(UploadRequest uploadRequest) {
        String fileName = fileService.storeFile(uploadRequest.getFile());

        UploadResponse uploadResponse = new UploadResponse(fileName, uploadRequest.getFullName(), uploadRequest.getDateOfBirth());

        return ResponseEntity.ok().body(uploadResponse);
    }


    @PostMapping(value = "generate/upload")
    public ResponseEntity<?> uploadAndGenerateFile() {
        fileService.generateAndStorePDF();
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("/files/{filename}")
    public void downloadFile(@PathVariable String filename, HttpServletResponse response) {
        Resource fileResource = fileService.loadFile(filename);

        Resource resource = fileService.loadFile(filename);
        byte[] data = Files.readAllBytes(Paths.get(resource.getURI()));


        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileResource.getFilename() + "\"");
        response.setContentLength(data.length);

        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }

    @DeleteMapping("{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        boolean isDeleted = fileService.deleteFile(fileName);
        if (isDeleted) {
            return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("File not found or couldn't be deleted", HttpStatus.NOT_FOUND);
        }
    }

}
