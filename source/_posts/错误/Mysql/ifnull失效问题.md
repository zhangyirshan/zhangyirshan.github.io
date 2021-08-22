---
title: ifnull失效问题
date: 2021-05-31 14:34:19
tags: [错误,Mysql]
categories: [错误,Mysql]
---
工作中遇到的一个例子，第一次书写如下所示:

```sql
SELECT IF(tgc.num = tugrm.match_num, true,false)
FROM tgamerecord tgr
            INNER JOIN tgameconfig tgc ON tgr.game_type = tgc.id AND tgc.num >= 9 AND tgc.num <= 12
            LEFT JOIN tusergamerecordmatch tugrm ON tugrm.game_no = #{gameId} AND tugrm.user_no = #{userId}
WHERE tgr.no = #{gameId}
```

返回的结果有null的情况，我以为是if不能判空，所以在外层加了IFNULL

```sql
SELECT IFNULL(IF(tgc.num = tugrm.match_num, true,false),false)
```

结果发现返回结果还是null，我就非常的不理解，于是上网查询了一下关于IFNULL失效的问题，结果发现失效的真正原因是查询的结果集为null所以SELECT根本没有执行，所以直接返回的就是null。
因此这个问题的解决方法就有了：

1. 通过后端代码判null解决
2. 通过sql语句让查询的结果肯定会执行

```sql
SELECT IF(count(*),IF(tgc.num = tugrm.match_num, true,false),false)
```

通过肯定会有查询结果的count(*)来作为判断条件，来确保会执行SELECT
