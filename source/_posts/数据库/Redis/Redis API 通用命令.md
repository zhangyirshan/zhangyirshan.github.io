---
title: Redis API 通用命令
date: 2020-10-16 13:59:42
tags: redis
categories: redis
---
## 通用命令

```sql
# 遍历所有key
keys
# 计算key的总数
dbsize
# 检查key是否存在
exists key
# 删除指定key-value
del key [key...]
# key在seconds秒后过期
expire key seconds
# 查看key剩余的过期时间
ttl key
# 去掉key的国企时间
persist key
# 返回key的类型
type key
```

### 例子

```redis
127.0.0.1:6379> keys *
1) "hello"
127.0.0.1:6379> dbsize
(integer) 1
127.0.0.1:6379> mset hel vo heh haha php gpo java his
OK
127.0.0.1:6379> keys h*
1) "heh"
2) "hel"
3) "hello"
127.0.0.1:6379> exists php
(integer) 1
127.0.0.1:6379> del php
(integer) 1
127.0.0.1:6379> exists php
(integer) 0
127.0.0.1:6379> EXPIRE heh 20
(integer) 1
127.0.0.1:6379> ttl heh
(integer) 16
127.0.0.1:6379> ttl heh
(integer) 10
127.0.0.1:6379> ttl heh
(integer) 4 还有4秒国企
127.0.0.1:6379> ttl heh
(integer) -2 已经过期删除
127.0.0.1:6379> EXISTS heh
(integer) 0
127.0.0.1:6379> EXPIRE hello 20
(integer) 1
127.0.0.1:6379> ttl hello
(integer) 15
127.0.0.1:6379> PERSIST hello
(integer) 1
127.0.0.1:6379> ttl hello
(integer) -1 代表key存在，并且没有过期时间
127.0.0.1:6379> get hello
"world"
```
