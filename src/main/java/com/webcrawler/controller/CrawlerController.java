package com.webcrawler.controller;

import com.webcrawler.controller.request.CrawlerRequest;
import com.webcrawler.controller.response.CrawlerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "webcrawler")
public class CrawlerController {

    @Autowired
    private final ControllerService controllerService;
    public CrawlerController(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    @GetMapping
    public CrawlerResponse crawlUrl(CrawlerRequest crawlerRequest) {
        return controllerService.crawlUrl(crawlerRequest);
    }

}
