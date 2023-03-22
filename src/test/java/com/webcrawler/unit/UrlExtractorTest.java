package com.webcrawler.unit;

import com.webcrawler.urlextractor.UrlExtractor;
import org.jetbrains.annotations.NotNull;
import org.jsoup.internal.NonnullByDefault;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UrlExtractorTest {

    private UrlExtractor urlExtractor;

    @BeforeEach
    public void beforeTest(){
        this.urlExtractor = Mockito.spy(UrlExtractor.class);
    }

    @Test
    public void testExtractUrl() {

        Mockito.doReturn(getMockElement()).when(urlExtractor).extractLinkElements(Mockito.anyString());
        Mockito.doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return getMockLink((Element) args[0]);
        }).when(urlExtractor).getLinkUrl(Mockito.any(Element.class));

        //Test 1 using root domains
        Set<String> extractedUrls = urlExtractor.extractUrl("https://testdomain.com/");

        Set<String> expectedResult = new HashSet<>();
        expectedResult.add("https://testdomain.com/i/business");
        expectedResult.add("https://testdomain.com/features/travel");
        expectedResult.add("https://testdomain.com");

        Assertions.assertEquals(expectedResult, extractedUrls, "Test that only URLs in the same domain with the root url are returned");

        //Test 2 using sub domains
        Set<String> extractedUrlsDomain = urlExtractor.extractUrl("https://community.testdomain.com/faq/");

        Set<String> expectedResultDomain = new HashSet<>();
        expectedResultDomain.add("https://community.testdomain.com");
        expectedResultDomain.add("https://community.testdomain.com/contact");
        expectedResultDomain.add("https://community.testdomain.com/faq");

        Assertions.assertEquals(expectedResultDomain, extractedUrlsDomain, "Test that only URLs in the same domain with the root url are returned");
    }


    //================ Mocks ====================
    private String getMockLink(Element element) {
        return element.baseUri();
    }

    private Elements getMockElement() {
        return new Elements((mockExtractUrl()));
    }

    private List<Element> mockExtractUrl() {

        ArrayList<Element> result = new ArrayList<>();

        result.add(new MockElement("https://testdomain.com"));
        result.add(new MockElement("https://testdomain.com/i/business"));
        result.add(new MockElement("https://testdomain.com/features/travel"));
        result.add(new MockElement("https://community.testdomain.com"));
        result.add(new MockElement("https://community.testdomain.com/contact"));
        result.add(new MockElement("https://community.testdomain.com/faq"));

        return result;
    }

    private static class MockElement extends Element {

        private final String url;
        public MockElement(String tag) {
            super(tag);
            this.url = tag;
        }

        @Override
        public @NotNull String baseUri() {
            return url;
        }
    }
}
