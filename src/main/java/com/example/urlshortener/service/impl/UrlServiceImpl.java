package com.example.urlshortener.service.impl;

import com.example.urlshortener.constant.ErrorCodeDetails;
import com.example.urlshortener.exception.URLShortenerException;
import com.example.urlshortener.repository.InMemoryRepository;
import com.example.urlshortener.service.UrlService;
import com.example.urlshortener.util.Base62Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UrlServiceImpl implements UrlService {

    private final InMemoryRepository repository;

    public UrlServiceImpl(InMemoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public String shortenUrl(String url) {

        log.info("Received request to shorten URL: {}", url);

        try {
            URI.create(url);
        } catch (Exception e) {
            log.error("Invalid URL format: {}", url);
            throw new URLShortenerException(ErrorCodeDetails.INVALID_URL);
        }

        if (repository.getUrlToCode().containsKey(url)) {
            String existingCode = repository.getUrlToCode().get(url);
            log.info("URL already shortened. Returning existing code: {}", existingCode);
            return existingCode;
        }

        long id = repository.getCounter().incrementAndGet();
        String code = Base62Encoder.encode(id);

        log.debug("Generated ID: {} and encoded short code: {}", id, code);

        repository.getUrlToCode().put(url, code);
        repository.getCodeToUrl().put(code, url);

        String domain = URI.create(url).getHost();
        repository.getDomainCount().merge(domain, 1, Integer::sum);

        log.info("URL shortened successfully. Domain: {}, ShortCode: {}", domain, code);

        return code;
    }

    @Override
    public String getOriginalUrl(String code) {

        log.info("Fetching original URL for short code: {}", code);

        String originalUrl = repository.getCodeToUrl().get(code);

        if (originalUrl == null) {
            log.error("No URL mapping found for short code: {}", code);
            throw new URLShortenerException(ErrorCodeDetails.URL_NOT_FOUND);
        }

        log.info("Original URL found for code {} -> {}", code, originalUrl);

        return originalUrl;
    }

    @Override
    public List<Map.Entry<String, Integer>> getTopDomains() {

        log.info("Fetching top 3 domains by shortened URL count");

        List<Map.Entry<String, Integer>> topDomains = repository.getDomainCount()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toList());

        log.debug("Top domains result: {}", topDomains);

        return topDomains;
    }
}