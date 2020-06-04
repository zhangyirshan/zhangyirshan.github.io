---
title: ResultSet
date: 2020-06-03 15:36:40
tags: Java
categories: [Java,Java工具类]
---
继承Wrapper接口和AutoCloseable接口

Wrapper是包装类，对原生的int、float。。。进行封装
AutoCloseable是自动关闭资源

ResultSet虽然继承了AutoCloseable可以自动关闭连接释放资源，但是还是要手动关闭，因为官方文档中说：
通常，立即释放此Statement对象的数据库和JDBC资源，而不是等待它自动关闭时发生。 最好在资源使用完毕后立即释放资源，以避免占用数据库资源。