package com.webcrawler.crawler.pojos;

import com.webcrawler.service.interfaces.ICrawlerService;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Getter
public class CrawlerComponents {
    private Queue<String> urlFrontier;
    private Set<String> visitedUrls;

    private Set<String> crawledUrl;

    public CrawlerComponents(){
        this.urlFrontier = new LinkedList<>();
        this.visitedUrls = new HashSet<>();
        this.crawledUrl = new HashSet<>();
    }
}
