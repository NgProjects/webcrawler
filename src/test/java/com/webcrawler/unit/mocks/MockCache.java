package com.webcrawler.unit.mocks;

import com.webcrawler.cache.interfaces.ICache;

import java.util.HashMap;
import java.util.Map;

public class MockCache implements ICache {

    private Map<String, Object> mockCache;
    public MockCache() {
        mockCache = new HashMap<>();
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
