
package com.example.urlshortener.repository;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;

@Repository
public class InMemoryRepository {

    private final Map<String, String> urlToCode = new ConcurrentHashMap<>();
    private final Map<String, String> codeToUrl = new ConcurrentHashMap<>();
    private final Map<String, Integer> domainCount = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public Map<String, String> getUrlToCode() { return urlToCode; }
    public Map<String, String> getCodeToUrl() { return codeToUrl; }
    public Map<String, Integer> getDomainCount() { return domainCount; }
    public AtomicLong getCounter() { return counter; }
}
