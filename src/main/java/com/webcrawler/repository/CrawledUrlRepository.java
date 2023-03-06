package com.webcrawler.repository;

import com.webcrawler.entities.CrawledUrl;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface CrawledUrlRepository extends CassandraRepository<CrawledUrl, UUID> {
    @AllowFiltering
    Set<String> findExtractedUrlsByUrl(String url);
}
