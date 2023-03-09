package org.example.exception;

public class FileCouldNotStoreException extends RuntimeException {
    public FileCouldNotStoreException(String message) {
        super(message);
    }
}
