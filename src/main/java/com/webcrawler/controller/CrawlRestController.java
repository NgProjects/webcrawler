package com.webcrawler.controller;

import com.webcrawler.controller.response.CrawlerResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@Validated
public class CrawlRestController {

    private final ControllerService controllerService;

    @Autowired
    public CrawlRestController(ControllerService controllerService) {
        this.controllerService = controllerService;
    }

    @GetMapping("single")
    @Tag(name = "single", description = "Crawl Single Url")//for swagger documentation
    public CrawlerResponse crawlSingleUrl(@RequestParam String url) {
        return controllerService.crawlUrl(url);
    }

    @GetMapping("health")
    @Tag(name = "health", description = "Health Check Endpoint")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("Service is up and running",HttpStatus.OK);
    }

}
