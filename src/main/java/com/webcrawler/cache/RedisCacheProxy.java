package com.webcrawler.cache;

import com.webcrawler.cache.rediscore.RedisCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;


/**
 * This is a proxy class so that we can neatly catch redis connection errors
 * also, Never Call Cacheable Method from the same class
 */
@Service
@ARedisCache
public class RedisCacheProxy implements ICache {

    Logger logger = LoggerFactory.getLogger(RedisCacheProxy.class);

    private final RedisCore redisCore;

    @Autowired
    public RedisCacheProxy(RedisCore redisCore) {
        this.redisCore = redisCore;
    }

    @Override
    public <T> T setItem(String cacheKey, T value) {
        try {
           return redisCore.setItemInCache(cacheKey, value);
        } catch (RedisConnectionFailureException e) {
            logger.error("Cannot connect to redis cache at the moment", e);
        }
        return value;
    }

    @Override
    public <T> T getItem(String cacheKey) {
        try {
            return redisCore.getItemFromCache(cacheKey);
        } catch (RedisConnectionFailureException e) {
            logger.error("Cannot connect to redis cache at the moment", e);
        }
        return null;
    }

    @Override
    @CacheEvict(key = "#cacheKey")
    public void removeItem(String cacheKey) {
        try {
            redisCore.removeItem(cacheKey);
        } catch (RedisConnectionFailureException e) {
            logger.error("Cannot connect to redis cache at the moment", e);
        }
    }

}
