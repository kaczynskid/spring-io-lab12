#!/usr/bin/env bash
mkdir -p /tmp/elk-data
cp -u ./logstash-input.conf /tmp/logstash-input.conf
cp -u ./logstash-output.conf /tmp/logstash-output.conf
docker run -d --name sprio-elk -v "/tmp/logstash-input.conf:/etc/logstash/conf.d/03-logstash-input.conf:ro" -v "/tmp/logstash-output.conf:/etc/logstash/conf.d/30-output.conf:ro" -v "/tmp/elk-data:/var/lib/elasticsearch" -p 5601:5601 -p 10042:10042/udp sebp/elk:es232_l232_k450
