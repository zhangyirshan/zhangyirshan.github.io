---
title: Java实体类中为何不用基本数据类型
date: 2020-07-22 13:10:24
tags: [性能分析,Java]
categories: [性能分析,Java]
---

阿里巴巴开发手册中写的很明确，基本类型接收NULL值有NPE风险（java.lang.NullPointerException  NPE 空值异常），而且默认值和NULL值不能传达同一种信息。

{% asset_img ali.jpg ali %}