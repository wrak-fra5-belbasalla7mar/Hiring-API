package com.spring.hiring.exception;

public class JobApplicationNotFoundException extends RuntimeException {
    public JobApplicationNotFoundException(String message) {
        super(message);
    }
}
