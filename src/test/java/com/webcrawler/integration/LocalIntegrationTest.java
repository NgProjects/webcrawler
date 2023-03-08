package com.webcrawler.integration;

import com.webcrawler.cache.ICache;
import com.webcrawler.config.IAppConfig;
import com.webcrawler.controller.ControllerService;
import com.webcrawler.controller.response.CrawlerResponse;
import com.webcrawler.entities.CrawledUrl;
import com.webcrawler.mocks.MockAppConfig;
import com.webcrawler.mocks.MockCache;
import com.webcrawler.mocks.MockUrlExtractor;
import com.webcrawler.repository.CrawledUrlRepository;
import com.webcrawler.service.CrawlerService;
import com.webcrawler.service.ICrawlerService;
import com.webcrawler.urlextractor.IUrlExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LocalIntegrationTest {

    private CrawledUrlRepository crawledUrlRepository;
    private ICache cache;

    private ControllerService controllerService;

    private Map<String, Object> mockDB;

    @BeforeEach
    public void initializeClasses(){
        this.crawledUrlRepository = Mockito.spy(CrawledUrlRepository.class);
        mockCrawledUrlRepository();
        this.cache = new MockCache();
        IUrlExtractor urlExtractor = new MockUrlExtractor();
        IAppConfig appConfig = new MockAppConfig();
        ICrawlerService crawlerService = new CrawlerService(cache, urlExtractor, appConfig, crawledUrlRepository);
        this.controllerService = new ControllerService(crawlerService);
    }

    private void mockCrawledUrlRepository() {
        this.mockDB = new HashMap<>();

        //Mock Save
        Mockito.doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            CrawledUrl crawledUrl = (CrawledUrl) args[0];
            if(crawledUrl != null){
                mockDB.put(crawledUrl.getUrl(), crawledUrl);
            }
            return crawledUrl;
        }).when(crawledUrlRepository).save(Mockito.any(CrawledUrl.class));

        //Mock retrieve
        Mockito.doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            String url = (String) args[0];
            return mockDB.get(url);
        }).when(crawledUrlRepository).findCrawledUrlByUrl(Mockito.anyString());
    }

    @Test
    public void testThatCrawlInteractsWithComponents(){

        String url = "https://monzo.com";
        CrawlerResponse response = controllerService.crawlUrl(url);
        CrawledUrl crawledUrl = crawledUrlRepository.findCrawledUrlByUrl(url);
        Set<String> cachedUrls = cache.getItem(url);

        Assertions.assertEquals(response.getExtractedUrl(), crawledUrl.getExtractedUrls());
        Assertions.assertEquals(response.getExtractedUrl(), cachedUrls);
        Assertions.assertEquals(crawledUrl.getExtractedUrls(), cachedUrls);

    }

}
