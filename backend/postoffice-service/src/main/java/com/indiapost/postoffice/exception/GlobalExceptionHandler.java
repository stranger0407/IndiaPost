package com.indiapost.postoffice.exception;

import com.indiapost.postoffice.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Post Office microservice.
 * <p>
 * Catches all unhandled exceptions and validation errors,
 * returning standardized {@link ApiResponse} error objects
 * so the frontend always receives a consistent JSON structure.
 * </p>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles constraint validation errors (e.g., invalid PIN code format).
     *
     * @param ex the validation exception
     * @return 400 Bad Request with error details
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(ConstraintViolationException ex) {
        log.warn("Validation error: {}", ex.getMessage());
        String message = ex.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .findFirst()
                .orElse("Invalid request parameters");
        return ResponseEntity.badRequest().body(ApiResponse.error(message));
    }

    /**
     * Catch-all handler for unexpected exceptions.
     *
     * @param ex the unhandled exception
     * @return 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred. Please try again later."));
    }
}
