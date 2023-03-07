package com.webcrawler.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class CrawlerHelper {
    static Logger logger = LoggerFactory.getLogger(CrawlerHelper.class);

    /**
     *
     * @param url - url to be processed
     * @return - a url without last /
     */
    public static String removeLastSlashIfAny(String url) {

        int lastCharacterIndex = url.length() - 1;
        char lastChar = url.charAt(lastCharacterIndex);
        if(lastChar == '/'){
            return url.substring(0, lastCharacterIndex);
        }

        return url;
    }

    /**
     *
     * @param rootUrl - full url
     * @return - the domain of the root url
     */
    public static String getUrlDomain(String rootUrl) {
        String rootUrlDomain = "";
        try {
            URL url = new URL(rootUrl);
            rootUrlDomain = url.getHost();
        } catch (MalformedURLException e) {
            logger.error("Unable to validate url {}", rootUrl);
        }
        return rootUrlDomain;
    }


    public static boolean isValidUrl(String rootUrlDomain, String extractedUrl) {

        if(rootUrlDomain == null || extractedUrl == null){
            return false;
        }

        String extractedUrlDomain = getUrlDomain(extractedUrl);

        return rootUrlDomain.equals(extractedUrlDomain);
    }

}
