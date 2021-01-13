---
title: Redis Cluster
date: 2020-12-17 14:15:38
tags: redis
categories: redis
---
## 呼唤集群

### 为什么呼唤

1. 并发量
2. 数据量

## 分区

### 分布式数据库-数据分区

{% asset_img 数据分区.png 数据分区%}

### 顺序分区和哈希分区

{% asset_img 顺序分区和哈希分区.png 顺序分区和哈希分区%}

##### 对比

{% asset_img 数据对比.png 数据对比%}

## Redis Cluster架构

1. 节点     cluster有16384个节点 cluster-enabled:yes
2. meet     节点通讯的基础
3. 指派槽    每个节点都有自己的指派槽，用于存储hash计算后的数据
4. 复制、高可用、分片

### meet

`cluster meet ip port`
`redis-cli -h 127.0.0.1 -p 7000 cluster meet 127.0.0.1 7001`

### 分配槽

```redis
cluster addslots slot[slot...]
redis-cli -h 127.0.0.1 -p 7000 cluster addslots {0...5461}
redis-cli -h 127.0.0.1 -p 7001 cluster addslots {5462...10922}
redis-cli -h 127.0.0.1 -p 7002 cluster addslots {10923...16383}
```

### 设置主从

```redis
cluster replicate node-id
让cluster复制node节点
redis-cli -h 127.0.0.1 -p 7003 cluster replicate ${node-id-7000}
```
