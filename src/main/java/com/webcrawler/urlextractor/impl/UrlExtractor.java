package com.webcrawler.urlextractor.impl;

import com.webcrawler.constants.WebCrawlerConstants;
import com.webcrawler.urlextractor.interfaces.IUrlExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Service
public class UrlExtractor implements IUrlExtractor {

    Logger logger = LoggerFactory.getLogger(UrlExtractor.class);
    @Override
    public Set<String> extractUrl(String rootUrl) {

        Set<String> extractedUrls = new HashSet<>();

        String rootUrlDomain = getUrlDomain(rootUrl);

        Elements links = extracttLinkElements(rootUrl);
        if(links == null){
            return extractedUrls;
        }

        for (Element link : links) {
            String linkUrl = getLinkUrl(link);
            if(isValidUrl(rootUrlDomain, linkUrl)){
                extractedUrls.add(linkUrl);
            }
        }

        return extractedUrls;
    }

    /**
     *
     * @param link
     * @return
     */
    private static String getLinkUrl(Element link) {
        String linkHref = link.attr(WebCrawlerConstants.HREF_TAG);
        String linkUrl;
        if(linkHref.startsWith("/")){
            linkUrl = link.baseUri() + linkHref;
        } else {
            linkUrl = linkHref;
        }
        return linkUrl;
    }

    /**
     *
     * @param url
     * @return
     */
    private Elements extracttLinkElements(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(10 * 1000).userAgent
                    (WebCrawlerConstants.MOZILLA).ignoreHttpErrors(true).get();
        } catch (IOException e) {
            logger.error("Unable to extract doc in URL");
        }

        if(doc == null){
            return null;
        }

        return doc.select(WebCrawlerConstants.HREF_A_TAG);
    }

    /**
     *
     * @param rootUrl
     * @return
     */
    private String getUrlDomain(String rootUrl) {
        String rootUrlDomain = "";
        try {
            URL url = new URL(rootUrl);
            rootUrlDomain = url.getHost();
        } catch (MalformedURLException e) {
            logger.error("Unable to validate url {}", rootUrl);
        }
        return rootUrlDomain;
    }

    public boolean isValidUrl(String rootUrlDomain, String extractedUrl) {

        if(rootUrlDomain == null || extractedUrl == null){
            return false;
        }

        String extractedUrlDomain = getUrlDomain(extractedUrl);

        return rootUrlDomain.equals(extractedUrlDomain);
    }
}
