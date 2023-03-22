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

        result.add("https://testdomain.com");
        result.add("https://testdomain.com/i/business");
        result.add("https://testdomain.com/features/travel");
        result.add("https://community.testdomain.com");
        result.add("https://community.testdomain.com/contact");
        result.add("https://community.testdomain.com/faq");

        return result;
    }

    public static Set<String> mockSameDomainUrl() {

        Set<String> result = new HashSet<>();

        result.add("https://testdomain.com");
        result.add("https://testdomain.com/i/business");
        result.add("https://testdomain.com/features/travel");
        result.add("https://testdomain.com/contact");
        result.add("https://testdomain.com/faq");

        return result;
    }
}
