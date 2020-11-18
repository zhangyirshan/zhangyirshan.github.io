---
title: Jedis配置优化
date: 2020-11-13 17:39:35
tags: redis
categories: redis
---
## Jedis配置优化

初始化Jedis连接池，通常来讲JedisPool是单例的。

```java
GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
JedisPool jedisPool = new JedisPool(poolConfig,"127.0.0.1",6379);
```

{% asset_img jedis配置优化.png Jedis配置优化%}
{% asset_img jedis配置优化2.png Jedis配置优化%}

### 适合的maxTotal

比较难确定的，举个例子：

1. 命令平均执行时间0.1ms=0.001s
2. 业务需要5wQPS
3. maxTotal理论值 = 0.001 * 50000 = 50个。实际值要偏大一些
4. 业务希望Redis并发量
5. 客户端执行命令时间
6. Redis资源：例如nodes（例如应用个数） * maxTotal是不能超过redis的最大连接数。（config get maxclients）
7. 资源开销: 例如虽然希望控制空闲连接，但是不希望因为连接池的频繁释放创建连接造成不必要开销。

###  适合的maxIdle和minIdle

建议maxIdle = maxTotal

- 减少新连接的开销。
    建议预热minIdle
- 减少第一次启动后的新连接开销。
