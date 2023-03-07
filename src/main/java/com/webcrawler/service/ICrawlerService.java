package com.webcrawler.service;

import java.util.Set;

public interface ICrawlerService {

    void saveCrawledUrlInDB(String url, Set<String> crawledUrls);

    Set<String> retrieveCrawledUrlFromDb(String url);

    void cacheCrawledUrls(String url, Set<String> crawledUrls);

    Set<String> retrieveCrawledUrlFromCache(String url);

    void cacheChildUrls(String url, Set<String> extractedUrl);

    Set<String> getCachedChildUrls(String url);

    int getMaxNoOfRequestsToDomain();
    Set<String> extractUrl(String url);

}
