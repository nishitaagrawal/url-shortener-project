package com.example.urlshortener.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UrlService {

    public String shortenUrl(String url);
    public String getOriginalUrl(String code) ;
    public List<Map.Entry<String, Integer>> getTopDomains();
}