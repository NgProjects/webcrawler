package com.webcrawler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

//@Configuration
//@PropertySource(value = { "classpath:application.properties" })
//@EnableCassandraRepositories(basePackages = "com.webcrawler.repository")
public class CassandraConfig {

//    @Autowired
//    private Environment environment;
//    @Override
//    protected String getKeyspaceName() {
//        return environment.getProperty("cassandra.keyspace");
//    }

}
