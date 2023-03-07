package com.webcrawler.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table
public class CrawledUrl implements Serializable {

    @Serial
    private static final long serialVersionUID = 7311593512278944531L;

    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID id = UUID.randomUUID();

    @PrimaryKeyColumn(name = "url", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String url;

    @Column("extracted_urls")
    private Set<String> extractedUrls = new HashSet<>();

    @CreatedDate
    @Column("create_date")
    private Instant createDate;

    @LastModifiedDate
    @Column("last_modified_date")
    private Instant lastModifiedDate;

}
