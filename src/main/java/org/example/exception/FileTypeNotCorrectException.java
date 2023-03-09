package org.example.exception;

public class FileTypeNotCorrectException extends RuntimeException {
    public FileTypeNotCorrectException(String message) {
        super(message);
    }
}
