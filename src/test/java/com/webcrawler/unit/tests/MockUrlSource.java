package com.webcrawler.unit.tests;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MockUrlSource {

    /**
     *
     * @return
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
}
