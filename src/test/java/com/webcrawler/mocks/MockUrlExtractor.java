package com.webcrawler.mocks;

import com.webcrawler.urlextractor.IUrlExtractor;

import java.util.Set;

public class MockUrlExtractor implements IUrlExtractor {

    Set<String> extractedUrl;

    public MockUrlExtractor() {
        this.extractedUrl = MockUrlSource.mockSameDomainUrl();
    }

    @Override
    public Set<String> extractUrl(String url) {
        return extractedUrl;
    }
}
