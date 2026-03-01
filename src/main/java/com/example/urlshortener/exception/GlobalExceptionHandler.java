package com.example.urlshortener.exception;

import com.example.urlshortener.constant.ErrorCodeDetails;
import com.example.urlshortener.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(URLShortenerException.class)
    public ResponseEntity<ErrorResponse> handleUrlException(
            URLShortenerException ex,
            HttpServletRequest request) {

        ErrorCodeDetails error = ex.getErrorCodeDetails();

        ErrorResponse response = new ErrorResponse(
                error.getErrorCode(),
                error.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, error.getHttpStatus());
    }
}