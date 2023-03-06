package com.webcrawler.crawler.impl;

import com.webcrawler.crawler.interfaces.ICrawler;
import com.webcrawler.crawler.pojos.CrawlerComponents;
import com.webcrawler.exception.WebCrawlerRuntimeException;
import com.webcrawler.service.interfaces.ICrawlerService;

import java.util.Set;

public class Crawler implements ICrawler {

    private final ICrawlerService crawlerService;
    private final CrawlerComponents crawlerComponents;

    private final String rootUrl;

    public Crawler(String rootUrl, ICrawlerService crawlerService) {
        checkThatRootUrlIsNotNull(rootUrl);
        this.rootUrl = rootUrl;
        this.crawlerService = crawlerService;
        this.crawlerComponents = new CrawlerComponents();
    }

    /**
     *
     * @param rootUrl
     */
    private static void checkThatRootUrlIsNotNull(String rootUrl) {
        if(rootUrl == null || rootUrl.isEmpty()){
            throw new WebCrawlerRuntimeException("Root url cannot be null");
        }
    }

    @Override
    public Set<String> crawlUrl() {

        //retrieve from the Cache if it exists to avoid sending request multiple times
        Set<String> crawledUrls = crawlerService.retrieveCrawledUrlFromCache(rootUrl);
        if(crawledUrls != null && !crawledUrls.isEmpty()){
            return crawledUrls;
        }

        //retrieve from the DB if it exists to avoid sending request multiple times
        crawledUrls = crawlerService.retrieveCrawledUrlFromDb(rootUrl);
        if(crawledUrls != null && !crawledUrls.isEmpty()){
            return crawledUrls;
        }

        crawledUrls = handleCrawl();

        crawlerService.cacheCrawledUrls(rootUrl, crawledUrls);
        crawlerService.saveCrawledUrlInDB(rootUrl, crawledUrls);

        return crawledUrls;
    }

    /**
     *
     * @return
     */
    private Set<String> handleCrawl() {

        int limitCounter = crawlerService.getCrawlLimit();
        int crawlCounter = 0;

        crawlerComponents.getUrlFrontier().add(rootUrl);
        crawlerComponents.getVisitedUrls().add(rootUrl);

        while (!crawlerComponents.getUrlFrontier().isEmpty()){

            if(crawlCounter == limitCounter){
                break;
            }

            String currentUrl = crawlerComponents.getUrlFrontier().remove();
            Set<String> extractedUrl = crawlerService.getCachedChildUrls(currentUrl);
            if(extractedUrl == null || extractedUrl.isEmpty()){
                extractedUrl = crawlerService.extractUrl(currentUrl);
            }

            if(extractedUrl != null){

                //cache pending retrieved urls incase another url comes with a similar request
                crawlerService.cacheChildUrls(currentUrl, extractedUrl);
                crawlerComponents.getCrawledUrl().addAll(extractedUrl);

                Set<String> unvisitedUrls = removeVisitedUrls(extractedUrl);

                crawlerComponents.getUrlFrontier().addAll(unvisitedUrls);

                crawlCounter++;
            }

        }

        return crawlerComponents.getCrawledUrl();
    }

    /**
     * @param extractedUrl that needs to be filtered
     * @return extractedUrl after removing visited url
     */
    private Set<String> removeVisitedUrls(Set<String> extractedUrl) {
        extractedUrl.removeAll(crawlerComponents.getVisitedUrls());
        return extractedUrl;
    }
}
