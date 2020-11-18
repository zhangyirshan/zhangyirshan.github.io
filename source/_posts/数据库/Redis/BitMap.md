---
title: Bitmap
date: 2020-11-18 10:01:07
tags: redis
categories: redis
---
## 位图

{% asset_img 位图.png 位图%}

## API

### setbit

```sql
setbit key offset value
# 给位图指定索引设置值
```

```sql
127.0.0.1:6379> setbit unique:users:2016-04-05 0 1
(integer) 0
127.0.0.1:6379> setbit unique:users:2016-04-05 5 1
(integer) 0
127.0.0.1:6379> setbit unique:users:2016-04-05 11 1
(integer) 0
127.0.0.1:6379> setbit unique:users:2016-04-05 15 1
(integer) 0
127.0.0.1:6379> setbit unique:users:2016-04-05 19 1
(integer) 0
```

{% asset_img setbit.png setbit%}

### getbit

```sql
getbit keyy offset
# 获取位图指定索引的值

127.0.0.1:6379> getbit unique:users:2016-04-05 8
(integer) 0
127.0.0.1:6379> getbit unique:users:2016-04-05 1
(integer) 0
127.0.0.1:6379> getbit unique:users:2016-04-05 19
(integer) 1
127.0.0.1:6379> getbit unique:users:2016-04-05 0
(integer) 1
```

### bitcount

```sql
bitcount key [start end]
获取位图指定范围(start到end，单位为字节，如果不指定就是获取全部)位值为1的个数

127.0.0.1:6379> bitcount unique:users:2016-04-05
(integer) 5
```

### bitop

```sql
bitop op destkey key [key ...]
做多个Bitmap的and（交集）、or（并集）、not（非）、xor（异或）
操作并将结果保存咋destkey中

# 求两个位图的并集
将unique:users:2016-04-05和unique:users:2016-04-04放在unique:users:and:2016_04_04-2016_04_05中
127.0.0.1:6379> bitop and unique:users:and:2016_04_04-2016_04_05 unique:users:2016-04-05 unique:users:2016-04-04
(integer) 3
```

### bitpos

```sql
bitpos key targetBit [start] [end]
计算位图指定范围（start到end，单位为字节，如果不指定就是获取全部）第一个偏移量对应的值等于targetBit的位置

127.0.0.1:6379> bitpos unique:users:2016-04-05 1 1
(integer) 11
127.0.0.1:6379> bitpos unique:users:2016-04-05 1 0
(integer) 0
127.0.0.1:6379> setbit unique:users:2016-04-05 8 1
(integer) 0
127.0.0.1:6379> bitpos unique:users:2016-04-05 1 1
(integer) 8
```

## 独立用户统计

1. 使用set和Bitmap
2. 1亿用户，5千万独立

{% asset_img 用户统计.png 用户统计%}

只有10w独立用户呢

{% asset_img 10w.png 10w%}

## 使用经验

1. type=string，最大512MB
2. 注意setbit时的偏移量，可能有较大耗时
3. 位图不是绝对好。