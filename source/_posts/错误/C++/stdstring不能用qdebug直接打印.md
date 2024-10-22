---
title: stdstring不能用qdebug直接打印
date: 2024-10-09 14:34:19
tags: [错误,C++]
categories: [错误,C++]
---

## 问题

std::string不能直接通过qDebug()使用

## 解决办法

转换成QString

```c++
const std::string& a2("aaaa");
qDebug() << QString::fromStdString(a2);

```

## 原因

因为std的string是c++的qdebug是qt的需要使用同样的qstring类。
