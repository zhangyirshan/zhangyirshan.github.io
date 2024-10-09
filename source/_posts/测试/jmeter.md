---
title: jmeter
date: 2024-03-27 22:16:23
tags: [测试,jmeter]
categories: [测试,jmeter]
---
## 基本配置

{% asset_img http.png http%}

线程组：用于发送http请求的父容器
查看结果树：查看请求结果
HTTP信息头管理器：修改请求头信息
HTTP请求默认值: 将所有http请求共用的内容封装到一起
setUp线程组：最优先执行的线程组
tearDown线程组：最后执行的线程组

### 断言

响应断言：判断相应内容，如果不匹配报错
大小断言：判断返回信息大小，如果超出报错
断言持续时间：判断请求时间，如果超出报错

### 逻辑控制器

if逻辑控制器：需要和http请求是父子级关系才生效，需要取消Interpret Condition as Variable Expression才能生效


### 跨线程组通信

