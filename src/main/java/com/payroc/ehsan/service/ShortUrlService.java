package com.payroc.ehsan.service;

public interface ShortUrlService {
    String getShortedUrl(String originalUrl, String suggestedUrl);
    String getFullUrl(String shortUrl);
}
