package com.webcrawler.cache.interfaces;

public interface ICache {
    <T> void setItem(String key, T value);
    <T> T getItem(String key, Class<T> clazz);
}
