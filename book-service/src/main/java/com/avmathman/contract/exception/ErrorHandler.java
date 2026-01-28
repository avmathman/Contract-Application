package com.avmathman.contract.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation() {
        return Map.of(
                "error", "VALIDATION_ERROR",
                "message", "Validation failed"
        );
    }
}
