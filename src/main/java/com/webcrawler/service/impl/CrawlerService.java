package com.webcrawler.service.impl;

import com.webcrawler.cache.impl.Cache;
import com.webcrawler.cache.interfaces.ICache;
import com.webcrawler.config.impl.AppConfig;
import com.webcrawler.config.interfaces.IAppConfig;
import com.webcrawler.constants.WebCrawlerConstants;
import com.webcrawler.entities.CrawledUrl;
import com.webcrawler.enums.ConfigKey;
import com.webcrawler.repository.crawledUrlRepository;
import com.webcrawler.service.interfaces.ICrawlerService;
import com.webcrawler.urlextractor.impl.UrlExtractor;
import com.webcrawler.urlextractor.interfaces.IUrlExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CrawlerService implements ICrawlerService {

    private final ICache cache;
    private final IUrlExtractor urlExtractor;

    private final crawledUrlRepository crawledUrlRepository;

    private final IAppConfig appConfig;

    @Autowired
    public CrawlerService(Cache cache, UrlExtractor urlExtractor,
                          crawledUrlRepository crawledUrlRepository, AppConfig appConfig) {
        this.cache = cache;
        this.urlExtractor = urlExtractor;
        this.crawledUrlRepository = crawledUrlRepository;
        this.appConfig = appConfig;
    }

    @Override
    public void saveCrawledUrlInDB(String url, Set<String> crawledUrls) {

        CrawledUrl crawledUrl = new CrawledUrl();

        crawledUrl.setUrl(url);
        crawledUrl.setExtractedUrls(crawledUrls);

        crawledUrlRepository.save(crawledUrl);
    }

    @Override
    public Set<String> retrieveCrawledUrlFromDb(String url) {
        return crawledUrlRepository.findExtractedUrlsByUrl(url);
    }

    @Override
    public void cacheCrawledUrls(String url, Set<String> crawledUrls) {
        cache.setItem(url, crawledUrls);
    }

    @Override
    public Set<String> retrieveCrawledUrlFromCache(String url) {
        return cache.getItem(url, Set.class);
    }

    @Override
    public void cacheChildUrls(String url, Set<String> extractedUrl) {
        cache.setItem(WebCrawlerConstants.CHILD_CACHE_KEY + url, extractedUrl);
    }

    @Override
    public Set<String> getCachedChildUrls(String url) {
        return null;
    }

    @Override
    public int getCrawlLimit() {

        Integer crawlLimit = cache.getItem(ConfigKey.CRAWL_LIMIT.getCacheKey(), Integer.class);
        if(crawlLimit != null && crawlLimit > 0){
            return crawlLimit;
        }

        crawlLimit = appConfig.getConfigInt(ConfigKey.CRAWL_LIMIT);
        cache.setItem(ConfigKey.CRAWL_LIMIT.getCacheKey(), crawlLimit);
        return crawlLimit;
    }

    @Override
    public Set<String> extractUrl(String url) {
        return urlExtractor.extractUrl(url);
    }
}
