package com.webcrawler.mocks;

import com.webcrawler.configkeyenum.IConfigKey;
import lombok.Getter;

@Getter
public enum ConfigKeyForTest implements IConfigKey {

    MAX_REQUEST_TO_WEBSITE("webcrawler-max-request", "webcrawler_max_request" ,"5");

    private final String key;
    private final String cacheKey;
    private final String defaultValue;

    ConfigKeyForTest(String key, String cacheKey, String defaultValue){
        this.cacheKey = cacheKey;
        this.key = key;
        this.defaultValue = defaultValue;
    }
}
