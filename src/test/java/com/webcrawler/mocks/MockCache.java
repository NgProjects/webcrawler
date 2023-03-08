package com.webcrawler.mocks;

import com.webcrawler.cache.ICache;

import java.util.HashMap;
import java.util.Map;

public class MockCache implements ICache {

    private final Map<String, Object> mockCache;
    public MockCache(){
        this.mockCache = new HashMap<>();
    }
    @Override
    public <T> T setItem(String key, T value) {
        mockCache.put(key, value);
        return value;
    }

    @Override
    public <T> T getItem(String key) {
        return (T) mockCache.get(key);
    }

    @Override
    public void removeItem(String key) {
        mockCache.remove(key);
    }
}
