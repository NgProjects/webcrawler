package com.webcrawler.cache.impl;

import com.webcrawler.cache.interfaces.ICache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



@Service
@CacheConfig(cacheNames={"web_crawler_cache"})
@ACache
public class Cache implements ICache {

    @Override
    @CachePut(key = "#cacheKey")
    public <T> T setItem(String cacheKey, T value) {
        return value;
    }

    @Override
    @Cacheable(key = "#cacheKey")
    public <T> T getItem(String cacheKey) {
        return null; //it will return null if it is not in the cache
    }

    @Override
    @CacheEvict(key = "#cacheKey")
    public void removeItem(String cacheKey) {}
}
