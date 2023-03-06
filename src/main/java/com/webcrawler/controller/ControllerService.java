package com.webcrawler.controller;

import com.webcrawler.controller.request.CrawlerRequest;
import com.webcrawler.controller.response.CrawlerResponse;
import com.webcrawler.crawler.impl.Crawler;
import com.webcrawler.service.impl.ACrawlerService;
import com.webcrawler.service.impl.CrawlerService;
import com.webcrawler.service.interfaces.ICrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ControllerService {
    private ICrawlerService crawlerService;

    @Autowired
    public ControllerService(@ACrawlerService ICrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    /**
     *
     * @param crawlerRequest
     * @return
     */
    public CrawlerResponse crawlUrl(CrawlerRequest crawlerRequest) {
        Crawler crawler = new Crawler(crawlerRequest.getUrl(), crawlerService);
        Set<String> extractedUrl = crawler.crawlUrl();
        return new CrawlerResponse(extractedUrl);
    }
}
