---
title: Redis Sentinel
date: 2020-12-02 10:02:50
tags: redis
categories: redis
---
## 主从复制高可用

## redis sentinel架构

{% asset_img 故障转移.png 故障转移%}

可以使用一套sentinel监控多套maseter，slave
{% asset_img 多个.png 多个%}

## 安装与配置

### Redis主节点

启动`redis-server redis-7000.conf`

配置

```
port 7000
daemonize yes
pidfile /var/run/redis-7000.pid
logfile "7000.log"
dir "/opt/soft/redis/data/"
```

### Redis从节点

启动`redis-server redis-7001.conf`

配置

```
port 7001
daemonize yes
pidfile /var/run/redis-7001.pid
logfile "7001.log"
dir "/opt/soft/redis/data/"
slaveof 127.0.0.1 7000
```

### sentinel主要配置

```
port ${port}
dir "/opt/soft/redis/data/"
logfile "${port}.log"
sentinel monitor mymaster 127.0.0.1 7000 2
sentinel down-after-milliseconds mymaster 30000
sentinel parallel-syncs mymaster 1
sentinel failover-timeout mymaster 180000
```

## java客户端

### 客户端实现基本原理

{% asset_img step1.png step1%}
{% asset_img step2.png step2%}
{% asset_img step3.png step3%}
{% asset_img step4.png step4%}
{% asset_img 客户端实现基本原理.png 客户端实现基本原理%}

### 客户端接入流程

1. Sentinel地址集合
2. masterName
3. 不是代理模式

### 三个定时任务

1. 每10秒每个sentinel对master和slave执行info
    - 发现slave节点
    - 确认主从关系
    {% asset_img 任务1.png 任务1%}
2. 每2秒每个sentinel通过master节点的channel交换信息（pub/sub）
    - 通过__sentinel__ : hello频道交互
    - 交互对节点的“看法”和自身信息
    {% asset_img 任务2.png 任务2%}
3. 每1秒每个sentinel对其他sentinel和redis执行ping
    - 心跳检测，失败判定依据
    {% asset_img 任务3.png 任务3%}

## 主观下线和客观下线

```sql
sentinel monitor<masterName> <ip> <port> <quorum>
sentinel monitor myMaster 127.0.0.1 6379 2
sentinel down-after-milliseconds <masterName> <timeout>
sentinel down-after-milliseconds mymaster 300000 
```

- 主观下线：每个sentinel节点对Redis节点失败的“偏见”
- 客观下线：所有sentinel节点对Redis节点失败“达成共识”（超过quorum个统一）

`sentinel is-master-down-by-addr`

## 领导者选举

- 原因：只有一个sentinel节点完成故障转移
- 选举：通过`sentinel is-master-down-by-addr`命令都希望成为领导者

1. 每个做主观下线的Sentinel节点向其他Sentinel节点发送命令，要求将它设置为领导者
2. 收到命令的Sentinel节点如果没有同意通过其他Sentinel节点发送命令，那么将同意该请求，否则拒绝
3. 如果该 Sentinel节点发现自己的票数已经超过Sentinel集合半数且超过quorum，那么它将成为领导者。
4. 如果此过程有多个Sentinel节点成为了 领导者，那么将等待一段时间重新选举
