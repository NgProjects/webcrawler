package com.webcrawler.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CrawlerResponse {
    Set<String> extractedUrl;
}
