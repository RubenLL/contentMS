package com.poc.ruben.controller.advice;

import com.poc.ruben.domain.exception.StorageException;
import com.poc.ruben.domain.exception.ValidationException;
import com.poc.ruben.dto.common.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorResponse> handleStorage(StorageException e) {
        return ResponseEntity.status(500).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse("Unexpected error"));
    }

    @ExceptionHandler(com.poc.ruben.domain.exception.NotFoundException.class)
    public ResponseEntity<com.poc.ruben.dto.common.ErrorResponse> handleNotFound(
            com.poc.ruben.domain.exception.NotFoundException e
    ) {
        return ResponseEntity.status(404).body(new com.poc.ruben.dto.common.ErrorResponse(e.getMessage()));
    }

}