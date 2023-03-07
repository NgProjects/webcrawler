package com.webcrawler.integration;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.webcrawler.cache.ARedisCache;
import com.webcrawler.cache.ICache;
import com.webcrawler.config.IAppConfig;
import com.webcrawler.controller.ControllerService;
import com.webcrawler.controller.response.CrawlerResponse;
import com.webcrawler.entities.CrawledUrl;
import com.webcrawler.mocks.MockAppConfig;
import com.webcrawler.mocks.MockUrlExtractor;
import com.webcrawler.repository.CrawledUrlRepository;
import com.webcrawler.service.CrawlerService;
import com.webcrawler.service.ICrawlerService;
import com.webcrawler.urlextractor.IUrlExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.cassandra.core.cql.generator.CreateKeyspaceCqlGenerator;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.io.Resources;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    private static final String KEY_SPACE_NAME = "webcrawler";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    public static CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:4.1.0").withExposedPorts(9042);

    @Container
    public static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.0.9")).withExposedPorts(6379);

    @Autowired
    private CrawledUrlRepository crawledUrlRepository;

    @Autowired
    @ARedisCache
    private ICache cache;

    private ControllerService controllerService;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        initPropertyFiles(registry);

    }
    @BeforeEach
    public void initializeClasses(){
        IUrlExtractor urlExtractor = new MockUrlExtractor();
        IAppConfig appConfig = new MockAppConfig();
        ICrawlerService crawlerService = new CrawlerService(cache, urlExtractor, appConfig, crawledUrlRepository);
        this.controllerService = new ControllerService(crawlerService);
    }

    @BeforeAll
    public static void beforeAll() {
        initKeyspaceAndTables();
    }

    private static void initPropertyFiles(DynamicPropertyRegistry registry) {
        //set up cassandra
        registry.add("spring.cassandra.keyspace-name", () -> KEY_SPACE_NAME);
        registry.add("spring.data.cassandra.schema-action", () -> "CREATE_IF_NOT_EXISTS");
        registry.add("spring.cassandra.local-datacenter", cassandra::getLocalDatacenter);
        registry.add("spring.cassandra.contact-points", cassandra::getHost);
        registry.add("spring.cassandra.port", () -> String.valueOf(cassandra.getMappedPort(9042)));

        //setup redis
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", redis::getFirstMappedPort);
    }

    private static void initTables(CqlSession cqlSession) {
        String createTableScript;
        try {
            createTableScript = readFile("create_crawledurl_table.csql");//sql file can be found in the resource folder
            cqlSession.execute(createTableScript);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String readFile(final String fileName) throws IOException {
        final URL url = Resources.getResource(fileName);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }

    public static void initKeyspaceAndTables() {
        CqlSessionBuilder cqlSessionBuilder = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(cassandra.getHost(),cassandra.getMappedPort(9042)))
                .withLocalDatacenter(cassandra.getLocalDatacenter());
        try (CqlSession session =  cqlSessionBuilder.build()) {
            session.execute(CreateKeyspaceCqlGenerator.toCql(
                    CreateKeyspaceSpecification.createKeyspace(KEY_SPACE_NAME).ifNotExists()));
            initTables(session);//init tables
        }
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

    @Test
    public void testThatAnInvalidMessageIsReturnedWhenInvalidUrlIsPassedInRequest(){
        CrawlerResponse response = this.restTemplate.getForObject("http://localhost:" + port + "/webcrawler/single?url=https://monzo.com////", CrawlerResponse.class);
        Assertions.assertNull(response.getExtractedUrl());
        Assertions.assertEquals("-1", response.getResponseCode());
    }

}
