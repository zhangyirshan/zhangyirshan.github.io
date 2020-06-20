---
title: Elasticsearch 新旧索引数据迁移(_reindex)
p: 后端/Elasticsearch/Elasticsearch 新旧索引数据迁移(_reindex)
date: 2020-06-12 15:41:12
tags: Elasticsearch
categories: Elasticsearch
---
Elasticsearch 新旧索引数据迁移(_reindex)

举个例子，在输入“1992-02-27”这样格式的数据，es会把他默认为是date数据类型，但是有时候我们希望它是text类型，在原索引中已经有数据的情况下该如何操作。

1. 创建原索引

```
PUT /aaron_index/aaron_type/1
{
  "name":"张辽","age":27,"content":"1992-02-27"
}
PUT /aaron_index/aaron_type/2
{
  "name":"曹阿瞒","age":28,"content":"1991-02-19"
}
```

2. 创建新索引

```
PUT /aaron_index_new
{
  "mappings": {
    "aaron_type":{
      "properties":{
        "content":{"type":"text"},
        "name":{"type":"text"}
      }
    }
  }
}
```

```
PUT /aaron_index_new/aaron_type/2
{
  "name":"曹操","content":"1991-02-19,28岁"
}
```

3.查看两个索引的mapping类型

```
GET /aaron_index/_mapping
GET /aaron_index_new/_mapping
```

4.将原索引全部放入新索引中，_id冲突的以原索引为准

虽然新索引中只有两个字段(原索引中有三个)，也会将原索引中的数据插入新索引中，并覆盖_id相同的数据。

```
POST _reindex
{
  "source": {"index": "aaron_index"},
  "dest": {"index": "aaron_index_new"}
}
```

5.将原索引放入新索引中，_id冲突的以新索引为准

虽然新索引中只有两个字段(原索引中有三个)，也会将原索引中的数据插入新索引中，但不覆盖_id相同的数据。

```
POST _reindex
{
  "conflicts": "proceed",
  "source": {"index": "aaron_index"},
  "dest": {"index": "aaron_index_new","op_type": "create"}
}
```

————————————————
版权声明：本文为CSDN博主「羲凡丞相」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_42003671/article/details/96485675
