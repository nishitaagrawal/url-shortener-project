package com.example.urlshortener.constant;

import org.springframework.http.HttpStatus;

public enum ErrorCodeDetails {

    URL_NOT_FOUND("URL_404", "Short URL does not exist", HttpStatus.NOT_FOUND),

    INVALID_URL("URL_400", "Invalid URL provided", HttpStatus.BAD_REQUEST),

    INTERNAL_SERVER_ERROR("URL_500", "Unexpected server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCodeDetails(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}