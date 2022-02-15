---
title: 安装ElasticSearch
date: 2022-01-10 14:42:53
tags: Elasticsearch
categories: Elasticsearch
---

## docker-compose

使用docker-compose启动elasticsearch和kibana
`docker-compose up -d` -d 后台启动

```yml
version: '2.2'
services:
  elasticsearch:
    image: elasticsearch:7.16.2
    container_name: elasticsearch
    environment:
      - node.name=elasticsearch
      - cluster.name=es-docker-cluster
      - discovery.type=single-node
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms256m -Xmx256m"
      - TZ=Asia/Shanghaix
      - http.cors.enabled=true
      - http.cors.allow-origin=*
      - http.cors.allow-headers=Authorization,X-Requested-With,Content-Length,Content-Type
      - xpack.security.enabled=true
      - xpack.security.transport.ssl.enabled=true
      - xpack.security.audit.enabled=true
      - xpack.license.self_generated.type=basic
      - xpack.monitoring.collection.enabled=true
      - "ELASTIC_PASSWORD=123456"
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - ./data:/usr/share/elasticsearch/data
      - ./logs:/usr/share/elasticsearch/logs
    ports:
      - 9200:9200
      - 9300:9300

  kibana:
    depends_on: 
      - elasticsearch
    image: kibana:7.16.2
    container_name: kibana
    ports:
      - 5601:5601
    environment:
      - elasticsearch.url=http://elasticsearch:9200
      - elasticsearch.hosts=http://elasticsearch:9200
      - i18n.locale=zh-CN   
      - TZ=Asia/Shanghai
      - "ELASTICSEARCH_USERNAME=elastic"    
      - "ELASTICSEARCH_PASSWORD=123456"
    volumes:
      - ./kibana.yml:/usr/share/kibana/config/kibana.yml
      - /etc/localtime:/etc/localtime
```
