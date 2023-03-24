package com.webcrawler.crawler;

import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Getter
public class WebCrawlerComponents {
    private final Queue<String> urlFrontier;
    private final Set<String> visitedUrls;
    private final Set<String> crawledUrl;

    public WebCrawlerComponents(){
        this.urlFrontier = new LinkedList<>();
        this.visitedUrls = new HashSet<>();
        this.crawledUrl = new HashSet<>();
    }
}
