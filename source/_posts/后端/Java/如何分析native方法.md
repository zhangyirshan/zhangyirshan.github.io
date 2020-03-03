---
title: 如何分析native方法
p: 后端/Java/如何分析native方法
date: 2020-03-03 09:51:36
tags:
categories:
---

## 分析navative方法

1. 进入github（也可以进openJDK网站）
2. 点“搜索文件”，搜索对应的c代码类Thread.c
3. 找到native方法对应的方法名
4. 去src/htspot/share/prims/jvm.cpp里看cpp代码

{% asset_img native源码.png native源码%}
