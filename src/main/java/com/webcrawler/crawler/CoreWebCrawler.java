package com.webcrawler.crawler;

import com.webcrawler.exception.WebCrawlerRuntimeException;
import com.webcrawler.helper.CrawlHelper;
import com.webcrawler.service.ICrawlerService;

import java.util.Set;

public class CoreWebCrawler implements IWebCrawler {

    private final ICrawlerService crawlerService;

    private final WebCrawlerComponents webCrawlerComponents;

    private final String rootUrl;

    public CoreWebCrawler(String rootUrl, ICrawlerService crawlerService) {
        checkThatRootUrlIsNotNull(rootUrl);
        this.rootUrl = CrawlHelper.removeLastSlashIfAny(rootUrl);
        this.crawlerService = crawlerService;
        this.webCrawlerComponents = new WebCrawlerComponents();
    }

    /**
     *
     * @param rootUrl rootUrl
     */
    private void checkThatRootUrlIsNotNull(String rootUrl) {
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

        crawledUrls = crawlUrlFromWebsite();

        //save and cache if not null and empty
        if(crawledUrls != null && !crawledUrls.isEmpty()){
            crawlerService.cacheCrawledUrls(rootUrl, crawledUrls);
            crawlerService.saveCrawledUrlInDB(rootUrl, crawledUrls);
        }

        return crawledUrls;
    }

    /**
     *
     * @return set of crawled urls
     */
    private Set<String> crawlUrlFromWebsite() {

        int maxNoOfRequestToRootUrl = crawlerService.getMaxNoOfRequestsToDomain();
        int noOfRequestsCompleted = 0;

        webCrawlerComponents.getUrlFrontier().add(rootUrl);
        webCrawlerComponents.getVisitedUrls().add(rootUrl);

        while (!webCrawlerComponents.getUrlFrontier().isEmpty() && noOfRequestsCompleted < maxNoOfRequestToRootUrl){

            String currentUrl = CrawlHelper.removeLastSlashIfAny(webCrawlerComponents.getUrlFrontier().remove());
            Set<String> extractedUrl = crawlerService.getCachedChildUrls(currentUrl);
            if(extractedUrl == null || extractedUrl.isEmpty()){
                extractedUrl = crawlerService.extractUrl(currentUrl);
            }

            if(extractedUrl != null){

                //cache pending retrieved urls in case another url comes with a similar request
                crawlerService.cacheChildUrls(currentUrl, extractedUrl);
                webCrawlerComponents.getCrawledUrl().addAll(extractedUrl);

                Set<String> unvisitedUrls = removeVisitedUrls(extractedUrl);
                webCrawlerComponents.getUrlFrontier().addAll(unvisitedUrls);

                noOfRequestsCompleted++;
            }

        }

        return webCrawlerComponents.getCrawledUrl();
    }

    /**
     * @param extractedUrl that needs to be filtered
     * @return extractedUrl after removing visited url
     */
    private Set<String> removeVisitedUrls(Set<String> extractedUrl) {
        extractedUrl.removeAll(webCrawlerComponents.getVisitedUrls());
        return extractedUrl;
    }

}
