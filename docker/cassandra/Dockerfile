FROM cassandra:4.1.0

ENV CASSANDRA_KEYSPACE webcrawler

COPY ../../create-cassandra-keyspace.sh create-cassandra-keyspace.sh
ENTRYPOINT ["/create-cassandra-keyspace.sh"]
RUN chmod +x create-cassandra-keyspace.sh
CMD ["cassandra", "-f"]