package com.webcrawler.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class CrawlHelper {
    static Logger logger = LoggerFactory.getLogger(CrawlHelper.class);

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
//            logger.error("Unable to validate url {}", rootUrl); //commented out to avoid excess logs, some URLs can actually be invalid
        }
        return rootUrlDomain;
    }


    public static boolean isTheSameDomain(String rootUrlDomain, String extractedUrl) {

        if(rootUrlDomain == null || extractedUrl == null){
            return false;
        }

        String extractedUrlDomain = getUrlDomain(extractedUrl);

        return rootUrlDomain.equals(extractedUrlDomain);
    }

}
