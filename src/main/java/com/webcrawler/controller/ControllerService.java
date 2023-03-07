package com.webcrawler.controller;

import com.webcrawler.controller.response.CrawlerResponse;
import com.webcrawler.crawler.CoreWebCrawler;
import com.webcrawler.crawler.IWebCrawler;
import com.webcrawler.service.ICrawlerService;
import com.webcrawler.service.ACrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ControllerService {
    private final ICrawlerService crawlerService;

    @Autowired
    public ControllerService(@ACrawlerService ICrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    /**
     *
     * @param url - url to be crawled
     * @return - Crawler response containing the crawled url
     */
    public CrawlerResponse crawlUrl(String url) {
        IWebCrawler webCrawler = new CoreWebCrawler(url, crawlerService);
        Set<String> extractedUrl = webCrawler.crawlUrl();
        return new CrawlerResponse(extractedUrl);
    }
}
