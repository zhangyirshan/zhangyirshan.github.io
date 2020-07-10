---
title: SpringBoot的查询问题
date: 2020-07-10 22:16:23
tags: [错误,SpringBoot]
categories: [错误,SpringBoot]
---
## 问题状况

穿入一个参数
如果你在service方法中传入相同的参数，会得到同一个对象

## 解决办法

new ArrayList(creditDao.selectxxx());
