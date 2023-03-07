package org.example.util;

import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class MyMultipartFile implements MultipartFile {

    private final byte[] bytes;

    private final String filename;

    private final String contentType;

    public MyMultipartFile(byte[] bytes, String filename, String contentType) {
        this.bytes = bytes;
        this.filename = filename;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return filename;
    }

    @Override
    public String getOriginalFilename() {
        return filename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return bytes == null || bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

    @SneakyThrows
    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @SneakyThrows
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    @SneakyThrows
    @Override
    public void transferTo(File file) {
        Files.write(file.toPath(), bytes);
    }

}