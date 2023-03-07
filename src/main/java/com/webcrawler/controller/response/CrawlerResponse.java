package com.webcrawler.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CrawlerResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -2262027618757125115L;

    private String responseCode;

    private String responseDescription;

    private Set<String> extractedUrl;
}
