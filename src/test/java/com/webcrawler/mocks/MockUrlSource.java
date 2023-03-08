package com.webcrawler.mocks;

import java.util.HashSet;
import java.util.Set;

public class MockUrlSource {

    /**
     *
     * @return mocked data
     */
    public static Set<String> mockUrlSource() {

        Set<String> result = new HashSet<>();

        result.add("https://monzo.com");
        result.add("https://monzo.com/i/business");
        result.add("https://monzo.com/features/travel");
        result.add("https://community.monzo.com");
        result.add("https://community.monzo.com/contact");
        result.add("https://community.monzo.com/faq");

        return result;
    }

    public static Set<String> mockSameDomainUrl() {

        Set<String> result = new HashSet<>();

        result.add("https://monzo.com");
        result.add("https://monzo.com/i/business");
        result.add("https://monzo.com/features/travel");
        result.add("https://monzo.com/contact");
        result.add("https://monzo.com/faq");

        return result;
    }
}
