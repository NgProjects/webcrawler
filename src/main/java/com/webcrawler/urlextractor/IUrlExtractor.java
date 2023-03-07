package com.webcrawler.urlextractor;

import java.util.Set;

public interface IUrlExtractor {
    public Set<String> extractUrl(String url);
}
