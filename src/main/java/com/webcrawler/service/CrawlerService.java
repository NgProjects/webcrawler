package com.webcrawler.service;

import com.webcrawler.cache.ARedisCache;
import com.webcrawler.cache.ICache;
import com.webcrawler.config.AnAppConfig;
import com.webcrawler.config.IAppConfig;
import com.webcrawler.constants.WebCrawlerConstants;
import com.webcrawler.entities.CrawledUrl;
import com.webcrawler.enums.ConfigKey;
import com.webcrawler.repository.CrawledUrlRepository;
import com.webcrawler.urlextractor.AUrlExtractor;
import com.webcrawler.urlextractor.IUrlExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@ACrawlerService
public class CrawlerService implements ICrawlerService {
    private final ICache cache;
    private final IUrlExtractor urlExtractor;

    private final CrawledUrlRepository crawledUrlRepository;

    private final IAppConfig appConfig;

    @Autowired
    public CrawlerService(@ARedisCache ICache cache,
                          @AUrlExtractor IUrlExtractor urlExtractor,
                          @AnAppConfig IAppConfig appConfig,
                          CrawledUrlRepository crawledUrlRepository) {
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

        this.crawledUrlRepository.save(crawledUrl);
    }

    @Override
    public Set<String> retrieveCrawledUrlFromDb(String url) {

        CrawledUrl crawledUrl = crawledUrlRepository.findCrawledUrlByUrl(url);
        if(crawledUrl == null){
            return null;
        }
        return crawledUrl.getExtractedUrls();
    }

    @Override
    public void cacheCrawledUrls(String url, Set<String> crawledUrls) {
        cache.setItem(url, crawledUrls);
    }

    @Override
    public Set<String> retrieveCrawledUrlFromCache(String url) {
        return cache.getItem(url);
    }

    @Override
    public void cacheChildUrls(String url, Set<String> extractedUrl) {
        cache.setItem(WebCrawlerConstants.CHILD_CACHE_KEY + url, extractedUrl);
    }

    @Override
    public Set<String> getCachedChildUrls(String url) {
        return cache.getItem(WebCrawlerConstants.CHILD_CACHE_KEY + url);
    }

    @Override
    public int getMaxNoOfRequestsToDomain() {

        Integer maxRequest = cache.getItem(ConfigKey.MAX_REQUEST_TO_WEBSITE.getCacheKey());
        if(maxRequest != null && maxRequest > 0){
            return maxRequest;
        }

        maxRequest = appConfig.getConfigInt(ConfigKey.MAX_REQUEST_TO_WEBSITE);
        cache.setItem(ConfigKey.MAX_REQUEST_TO_WEBSITE.getCacheKey(), maxRequest);
        return maxRequest;
    }

    @Override
    public Set<String> extractUrl(String url) {
        return urlExtractor.extractUrl(url);
    }
}
