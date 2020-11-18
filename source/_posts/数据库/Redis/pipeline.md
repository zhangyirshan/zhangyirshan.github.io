---
title: pipeline
date: 2020-11-17 14:12:31
tags: redis
categories: redis
---
## 什么是流水线

{% asset_img pipeline.png pipeline %}

## 流水线的作用

|命令|N个命令操作|1次 pipeline（n个命令）|
|--|--|--|
|时间|n次网络 + n次命令|1次网络 + n次命令|
|数据量|1条命令|n条命令|

两点注意

1. Redis的命令时间是微妙级别。
2. pipeline每次条数要控制（网络）。

{% asset_img 流水线.png 流水线 %}

## 与原生M操作的区别

{% asset_img 原子.png 原子 %}
{% asset_img 非原子.png 非原子 %}

## 使用建议

1. 注意每次pipeline携带数据量
2. pipeline每次只能作用在一个Redis节点上
3. M操作与pipeline区别