package com.webcrawler.urlextractor.interfaces;

import java.util.Set;

public interface IUrlExtractor {
    public Set<String> extractUrl(String url);
}
