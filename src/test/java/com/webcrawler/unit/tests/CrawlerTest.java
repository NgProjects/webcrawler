package com.webcrawler.unit.tests;

import com.webcrawler.crawler.impl.Crawler;
import com.webcrawler.exception.WebCrawlerRuntimeException;
import com.webcrawler.service.impl.CrawlerService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Set;

public class CrawlerTest {

    //mock database and cache


    private CrawlerService crawlerService;

    @BeforeEach
    public void beforeTest(){

        // This is a unit test, All the dependencies were mocked so we can safely pass in null
        this.crawlerService = Mockito.spy(new CrawlerService(null, null, null,null));

    }

    @Test
    public void testNullRootUrl(){
        Assert.assertThrows("Test that an exception is thrown when crawler is initialized with a null url", WebCrawlerRuntimeException.class, () -> {
            new Crawler(null, crawlerService);
        });
    }

    @Test
    public void testThatCrawlerTriesToRetrieveFromTheCacheFirst(){
        Set<String> resultFromCache = MockUrlSource.mockUrlSource();
        Mockito.doReturn(resultFromCache).when(crawlerService).retrieveCrawledUrlFromCache(Mockito.anyString());
        Crawler crawler = new Crawler("https://monzo.com", crawlerService);
        Set<String> crawlerResult = crawler.crawlUrl();

        Assertions.assertEquals(crawlerResult, resultFromCache, "Test that crawler tries to retrieve data from cache first");

    }

    @Test
    public void testThatCrawlerTriesToRetrieveFromTheDbAfterCache(){
        Set<String> resultFromDB = MockUrlSource.mockUrlSource();
        Mockito.doReturn(null).when(crawlerService).retrieveCrawledUrlFromCache(Mockito.anyString());
        Mockito.doReturn(resultFromDB).when(crawlerService).retrieveCrawledUrlFromDb(Mockito.anyString());

        Crawler crawler = new Crawler("https://monzo.com", crawlerService);
        Set<String> crawlerResult = crawler.crawlUrl();

        Assertions.assertEquals(crawlerResult, resultFromDB, "Test that crawler tries to retrieve data from cache first");

    }

    @Test
    public void testThatCrawlerFetchesUrl(){

        Set<String> mockExtractedUrls = MockUrlSource.mockUrlSource();

        Mockito.doReturn(null).when(crawlerService).retrieveCrawledUrlFromCache(Mockito.anyString());
        Mockito.doReturn(null).when(crawlerService).retrieveCrawledUrlFromDb(Mockito.anyString());
        Mockito.doReturn(null).when(crawlerService).getCachedChildUrls(Mockito.anyString());
        Mockito.doReturn(mockExtractedUrls).when(crawlerService).extractUrl(Mockito.anyString());
        Mockito.doReturn(3).when(crawlerService).getCrawlLimit();

        Mockito.doNothing().when(crawlerService).cacheChildUrls(Mockito.anyString(), Mockito.anySet());

        Mockito.doNothing().when(crawlerService).cacheCrawledUrls(Mockito.anyString(), Mockito.anySet());

        Mockito.doNothing().when(crawlerService).saveCrawledUrlInDB(Mockito.anyString(), Mockito.anySet());

        Crawler crawler = new Crawler("https://monzo.com", crawlerService);
        Set<String> crawlerResult = crawler.crawlUrl();

        // Verify that methods were called in this order
        InOrder inOrder = Mockito.inOrder(crawlerService);
        inOrder.verify(crawlerService).retrieveCrawledUrlFromCache(Mockito.anyString());
        inOrder.verify(crawlerService).retrieveCrawledUrlFromDb(Mockito.anyString());
        inOrder.verify(crawlerService).getCrawlLimit();
        inOrder.verify(crawlerService).getCachedChildUrls(Mockito.anyString());
        inOrder.verify(crawlerService).extractUrl(Mockito.anyString());
        inOrder.verify(crawlerService).cacheCrawledUrls(Mockito.anyString(), Mockito.anySet());
        inOrder.verify(crawlerService).saveCrawledUrlInDB(Mockito.anyString(), Mockito.anySet());

        //Extract URL should be called for the root URL and for every other content of the mocked urls
        // visited url check should prevent the mocked urls to be crawled more than once
        Mockito.verify(crawlerService, Mockito.atMost(mockExtractedUrls.size() + 1)).extractUrl(Mockito.anyString());

    }



}
