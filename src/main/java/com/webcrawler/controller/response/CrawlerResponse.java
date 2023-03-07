package com.webcrawler.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CrawlerResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -2262027618757125115L;
    private Set<String> extractedUrl;
}
