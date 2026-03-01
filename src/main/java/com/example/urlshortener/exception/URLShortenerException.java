package com.example.urlshortener.exception;

import com.example.urlshortener.constant.ErrorCodeDetails;

public class URLShortenerException extends RuntimeException {

    private final ErrorCodeDetails errorCodeDetails;

    public URLShortenerException(ErrorCodeDetails errorCodeDetails) {
        super(errorCodeDetails.getMessage());
        this.errorCodeDetails = errorCodeDetails;
    }

    public ErrorCodeDetails getErrorCodeDetails() {
        return errorCodeDetails;
    }
}