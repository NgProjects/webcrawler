package com.webcrawler.cache.rediscore;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames={"web_crawler_cache"})
public class RedisCore {

    @CachePut(key = "#cacheKey")
    public <T> T setItemInCache(String cacheKey, T value) {
        return value;
    }

    @Cacheable(key = "#cacheKey")
    public <T> T getItemFromCache(String cacheKey) {
        return null;
    }

    @CacheEvict(key = "#cacheKey")
    public void removeItem(String cacheKey) {

    }

}
