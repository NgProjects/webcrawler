#!/bin/bash

if [[ ! -z "$CASSANDRA_KEYSPACE" && $1 = 'cassandra' ]]; then

  echo "Creating keyspace..."
  # Create default keyspace for single node cluster
  CQL="CREATE KEYSPACE IF NOT EXISTS $CASSANDRA_KEYSPACE WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};"
  until echo $CQL | cqlsh; do
    echo "cqlsh: Cassandra is unavailable: DO NOT PANIC, CASSANDRA IS STILL STARTING UP. Webcrawler will start up as soon as cassandra is up - will retry"
    sleep 2
  done &
fi

exec docker-entrypoint.sh "$@"