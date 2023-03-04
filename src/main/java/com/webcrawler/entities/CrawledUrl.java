package com.webcrawler.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table
public class CrawledUrl implements Serializable {

    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID id;

    @PrimaryKeyColumn(name = "url", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String url;
    @Column
    private boolean deleted = false;

    @Column
    private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

    @Column
    private Timestamp dateModified = new Timestamp(System.currentTimeMillis());

    @Column
    private Set<String> extractedUrls = new HashSet<>();

}
