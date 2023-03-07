package com.webcrawler.repository;

import com.webcrawler.entities.CrawledUrl;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CrawledUrlRepository extends CassandraRepository<CrawledUrl, UUID> {
    @Query("SELECT extracted_urls FROM CrawledUrl WHERE url = ?0")
    CrawledUrl findCrawledUrlByUrl(String url);
}
