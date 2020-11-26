---
title: GEO
date: 2020-11-18 15:44:26
tags: redis
categories: redis
---
## GEO

> GEO(地理信息定位)：存经纬度，计算两地距离，范围计算等

例：

北京：116.28，39.55
天津：117.12，39.08

## 应用场景

{% asset_img GEO.png GEO%}

## API

### geoadd

```sql
geoadd key longitude latitude memeber
[longitude latitude memeber]
# 增加地理位置信息

127.0.0.1:6379> geoadd city:location 120.17759030553346 30.250102922876387 保佑桥西弄5号
(integer) 1
127.0.0.1:6379> geoadd city:location 120.17095719788504 30.262918390984563 延安路306号天阳明珠商业中心A座701
(integer) 1
``` 

### geopos

```sql
geopos key  memeber [memeber]
# 获取地理位置信息

127.0.0.1:6379> geopos city:location 保佑桥西弄5号
1) 1) "120.17758995294570923"
   2) "30.2501037214901487"
``` 

### geodist

```sql
geodist key  memeber1 memeber2 [unit]
# 获取两个地理位置的距离
# unit：m（米）、km（千米）、mi（英里）、ft（尺）

127.0.0.1:6379> geopos city:location 保佑桥西弄5号
1) 1) "120.17758995294570923"
   2) "30.2501037214901487"
``` 

### georadius

```sql
geodist key  longitude latitude radiusm|km|ft|mi [withcoord] [withdist] [withhash] [COUNT count] [asc|desc] [store key] [storedist key]
# 获取指定位置范围内的地理位置信息集合
withcoord ：返回结果中包含经纬度。
withdist ：返回结果中包含距离中心节点位置。
withhash ：返回结果追踪包含geohash。
COUNT count ：指定返回结果的数量。
asc|desc ：返回结果按照距离中心节点的距离做升序或降序。
store key ： 将返回结果的地理位置信息保存到指定键
storedist key ：将返回结果距离中心节点的距离保存到指定键

127.0.0.1:6379> geopos city:location 保佑桥西弄5号
1) 1) "120.17758995294570923"
   2) "30.2501037214901487"
``` 

## 相关说明

1. since 3.2+
2. type geoKey = zset
3. 没有删除API：zrem key member