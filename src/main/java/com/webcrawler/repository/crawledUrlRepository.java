package com.webcrawler.repository;

import com.webcrawler.entities.CrawledUrl;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Set;
import java.util.UUID;

public interface crawledUrlRepository extends CassandraRepository<CrawledUrl, UUID> {
    @AllowFiltering
    Set<String> findExtractedUrlsByUrl(String url);
}
