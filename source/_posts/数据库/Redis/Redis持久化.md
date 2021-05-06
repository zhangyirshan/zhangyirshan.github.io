---
title: Redis持久化
date: 2020-11-18 16:21:49
tags: redis
categories: redis
---
## 持久化的作用

### 什么是持久化

> redis所有数据保存在内存中，对数据的更新将一部保存到磁盘上。

{% asset_img 持久化.png 持久化%}

### 持久化方式

{% asset_img 持久化方式.png 持久化方式%}

## RDB

### 什么是RDB

{% asset_img RDB.png RDB%}

### 触发机制-主要三种方式

1. save（同步）
2. bgsavve（异步）
3. 自动

#### save命令

{% asset_img save.png save%}

因为是同步命令，并且redis是单线程，所以会阻塞其他命令

### bgsave命令

{% asset_img bgsave.png bgsave%}
一般fork命令执行比较快，所以不会阻塞redis
文件策略和复杂度与save命令相同。

### 自动生成RDB

|配置| seconds| changes|
|--|--|--|
|save | 900 | 1     |
|save | 300 | 10    |
|save | 60  | 10000 |

#### 配置

```sql
save 900 1
save 300 10
save 60 10000                       # 可以做自己策略的修改
dbfilename dump.rdb                 # 文件名字
dir ./                              # 生成文件路径为当前路径
stop-writes-on-bgsave-error yes     # 遇到错误停止生成
rdbcompression yes                  # rdb是否采用压缩模式     
rdbchecksum yes                     # 是否对rdb文件进行校验和检验
```

#### 最佳配置

```sql
dbfilename dump-${port}.rdb         # 文件名字
dir /bigdiskpath                    # 生成文件路径为当前路径
stop-writes-on-bgsave-error yes     # 遇到错误停止生成
rdbcompression yes                  # rdb是否采用压缩模式     
rdbchecksum yes                     # 是否对rdb文件进行校验和检验
```

#### 触发机制-不容忽略方式

1. 全量复制，例如：主从复制
2. debug reload
3. shutdown

### RDB的问题

1. 耗时、耗性能
2. 不可控、丢失数据

- O(n)数据 : 耗时
- fork() : 消耗内存，copy-on-write策略
- DiskI/O : IO性能

## AOF

### AOF运行原理-创建

{% asset_img 创建.png 创建%}

### AOF运行原理-恢复

{% asset_img 恢复.png 恢复%}

### AOF的三种策略

- always
{% asset_img always.png always%}
- everysec
{% asset_img everysec.png everysec%}
- no
{% asset_img no.png no%}

|命令|always|everysec|no|
|--|--|--|--|
|优点|不丢失数据|每秒一次fsync丢1秒数据|不用管|
|缺点|IO开销较大，一般的sata盘只有几百TPS|丢1秒数据|不可控|

### AOF重新作用

- 减少硬盘占用量
- 加速恢复速度

### AOF重写实现两种方式

#### bgrewriteaof命令

{% asset_img bgrewriteaof.png bgrewriteaof%}

#### AOF重写配置

配置

- auto-aof-rewrite-min-size : AOF文件重写需要的尺寸
- auto-aof-rewrite-percentage : AOF文件增长率

统计

- aof_current_size : AOF当前尺寸（单位：字节）
- aof_base_size : AOF上次启动和重写的尺寸（单位：字节）

自动触发时机（同时满足）

- aof_current_size > auto-aof-rewrite-min-size
- aof_current_size - aof_base_size / aof_base_size > auto-aof-rewrite-percentage

{% asset_img AOF重写流程.png AOF重写流程%}

##### 配置

```properties
appendonly yes
appendfilename "appendonly-${port}.aof"
appendfsync  everysec
dir /bigdiskpath
no-appendfsync-on-rewrite yes
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
```

## RDB与AOF对比

|命令|RDB|AOF|
|--|--|--|
|启动优先级|低|高|
|体积|小|大|
|恢复速度|快|慢|
|数据安全性|丢数据|根据策略决定|
|轻重|重|轻|

### RDB最佳策略

- “关”
- 集中管理
- 主从，从开？

### AOF最佳策略

- “开”：缓存和存储
- AOF重写集中管理
- everysec

### 最佳策略

- 小分片
- 缓存或者存储
- 监控（硬盘、内存、负载、网络）
- 足够的内存
