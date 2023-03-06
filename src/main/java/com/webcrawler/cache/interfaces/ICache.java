package com.webcrawler.cache.interfaces;

public interface ICache {
    <T> T setItem(String key, T value);
    <T> T getItem(String key);
    void removeItem(String key);
}
