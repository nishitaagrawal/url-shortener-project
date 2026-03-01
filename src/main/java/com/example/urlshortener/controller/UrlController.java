package com.example.urlshortener.controller;

import com.example.urlshortener.dto.UrlRequest;
import com.example.urlshortener.dto.UrlResponse;
import com.example.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Slf4j
public class UrlController {

    private final UrlService service;

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/api/shorten")
    public ResponseEntity<UrlResponse> shorten(@Valid @RequestBody UrlRequest request) {

        log.info("Received shorten request for URL: {}", request.getUrl());

        String code = service.shortenUrl(request.getUrl());

        return ResponseEntity.ok(new UrlResponse(baseUrl + "/" + code));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {

        log.info("Redirect request for code: {}", code);

        String originalUrl = service.getOriginalUrl(code);

        return ResponseEntity.status(302)
                .location(URI.create(originalUrl))
                .build();
    }

    @GetMapping("/api/metrics/top-domains")
    public ResponseEntity<?> topDomains() {

        log.info("Fetching top domains metrics");

        return ResponseEntity.ok(service.getTopDomains());
    }
}