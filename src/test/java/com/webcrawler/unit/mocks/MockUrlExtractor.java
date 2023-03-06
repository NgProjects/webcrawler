package com.webcrawler.unit.mocks;

import com.webcrawler.urlextractor.interfaces.IUrlExtractor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MockUrlExtractor implements IUrlExtractor {

    private Map<String, Set<String>> mockUrlExtractor;

    private Set<String> mockExtractUrl() {

        Set<String> result = new HashSet<>();

        result.add("https://monzo.com");
        result.add("https://monzo.com/i/business/");
        result.add("https://monzo.com/features/travel/");
        result.add("https://community.monzo.com");
        result.add("https://community.monzo.com/contact");
        result.add("https://community.monzo.com/faq");
        result.add("https://help.monzo.com");
        result.add("https://help.monzo.com/faq");
        result.add("https://help.monzo.com/contact");

        return result;
    }

    @Override
    public Set<String> extractUrl(String url) {
        return mockExtractUrl();
    }
}
