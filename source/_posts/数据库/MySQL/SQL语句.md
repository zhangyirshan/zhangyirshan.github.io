---
title: SQL语句
p: 数据库/MySQL/SQL语句
date: 2020-05-06 09:48:50
tags: MySQL
categories: MySQL
---
## SQL JOIN

### 语法

```sql
SELECT … FROM
a,b
WHERE a.x = b.x

SELECT … FROM a
INNER JOIN b
on a.x = b.x

SELECT … FROM a
JOIN b
on a.x = b.x
```

Q：上述这些语法是否有区别？
A：没有任何区别
Q：哪个性能更好？
A：没有任何区别
A：好吧，如果要认真算的话，那么3最好，因为字节数最少
Q：那为什么需要不同的语法？
A：ANSI SQL 89、ANSI SQL 92语法标准
A：ANSI 92标准开始支持OUTER JOIN
A：INNER JOIN可以省略INNER关键字

### 算法

• nested_loop join
    • simple nested-loop join
    • index nested-loop join
    • block nested-loop join
• classic hash join
    • Only support in MariaDB
• bached key access join
    • from MySQL 5.6

#### simple nested_loop join

扫描成本： O（Rn×Sn）
{% asset_img simple.png simple%}

#### index nested_loop join

使用前提是每张表都要有索引
添加索引减少扫描成本，因为添加索引后扫描的时间是固定的
扫描成本： O（Rn）
优化器倾向于使用小表做驱动表
理由是2^100 > 100^2

#### block nested-loop join

• 优化simple nested-loop join
• 减少内部表的扫描次数
{% asset_img block.png block%}
系统变量join_buffer_size决定了Join Buffer的大小
Join Buffer可被用于联接是ALL，index，range的类型
Join Buffer只存储需要进行查询操作的相关列数据，而不是整行的记录
扫描成本呢？

### classic hash join

基于block join增加了hash表，减少了比较次数，一般用于数据量大时
{% asset_img hash.png hash%}

## sql问题

### 给查出的表格加行号

注意 变量a前面要加@并且后面用:=这样才不会报错，并且一定要给表加逗号取别名

```sql
SELECT @a:=@a+1 as row ,id,username
FROM `user`,(select @a:=0) u
```

### 排名问题

按照规则并列的排相同的名次，10分第一名，20分第二名，30分并列第三，40分第四

当prev_value和rank_column相等就返回rank_count，如果不相等,就将rank_column赋值给prev_value，赋值语句一定返回true，然后将rank_count+1
{% asset_img sql问题.png sql问题%}

### 查询中时间类型的比较

用data_format来格式化时间，然后使用字符串进行比较，确保比较双方的格式一致

```sql
select * from test where date_format(create_time,'%Y-%m-%d') between '2019-03-05' and '2019-03-08';
```
