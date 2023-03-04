package com.webcrawler.enums;

import lombok.Getter;

@Getter
public enum ConfigKey {

    CRAWL_LIMIT("crawl.limit", "crawl_limit" ,"50");
    private final String key;

    private final String cacheKey;
    private final String defaultValue;

    ConfigKey(String key, String cacheKey, String defaultValue){
        this.cacheKey = cacheKey;
        this.key = key;
        this.defaultValue = defaultValue;
    }
}
