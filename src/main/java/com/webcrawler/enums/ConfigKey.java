package com.webcrawler.enums;

import lombok.Getter;

@Getter
public enum ConfigKey {

    MAX_REQUEST_TO_WEBSITE("webcrawler-max-request", "webcrawler_max_request" ,"50");
    private final String key;

    private final String cacheKey;
    private final String defaultValue;

    ConfigKey(String key, String cacheKey, String defaultValue){
        this.cacheKey = cacheKey;
        this.key = key;
        this.defaultValue = defaultValue;
    }
}
