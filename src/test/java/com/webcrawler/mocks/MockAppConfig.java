package com.webcrawler.mocks;

import com.webcrawler.config.IAppConfig;
import com.webcrawler.configkeyenum.IConfigKey;

import java.util.HashMap;
import java.util.Map;

public class MockAppConfig implements IAppConfig {

    private final Map<String, String> mockConfig;

    public MockAppConfig(){
        mockConfig = new HashMap<>();
        mockConfig.put(ConfigKeyForTest.MAX_REQUEST_TO_WEBSITE.getKey(), "4");
    }
    @Override
    public Integer getConfigInt(IConfigKey key) {
        return Integer.valueOf(getConfig(key));
    }

    @Override
    public String getConfig(IConfigKey key) {
        return mockConfig.get(key.getKey()); //mocked value
    }
}
