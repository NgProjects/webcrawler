package com.webcrawler.urlextractor;

import com.webcrawler.constants.WebCrawlerConstants;
import com.webcrawler.helper.CrawlHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@AUrlExtractor
public class UrlExtractor implements IUrlExtractor {

    Logger logger = LoggerFactory.getLogger(UrlExtractor.class);
    @Override
    public Set<String> extractUrl(String rootUrl) {
        // check if url can be crawled
        if(!urlCanBeCrawled(rootUrl)){
            return null;
        }
        Set<String> extractedUrls = new HashSet<>();

        String rootUrlDomain = CrawlHelper.getUrlDomain(rootUrl);

        Elements links = extractLinkElements(rootUrl);
        if(links == null){
            return extractedUrls;
        }

        for (Element link : links) {
            String linkUrl = getLinkUrl(link);
            if(CrawlHelper.isTheSameDomain(rootUrlDomain, linkUrl)){
                extractedUrls.add(linkUrl);
            }
        }

        return extractedUrls;
    }

    /**
     *
     * @param rootUrl url
     * @return true if url can be crawled
     */
    private boolean urlCanBeCrawled(String rootUrl) {
        return rootUrl != null; //replace with checking for url robot.txt
    }

    /**
     *
     * @param link element link
     * @return link url
     */
    public String getLinkUrl(Element link) {
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
     * @param url url
     * @return elements
     */
    public Elements extractLinkElements(String url) {
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

}
