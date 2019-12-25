---
title: NIO与IO区别
p: 后端/Java/Java8新特性/NIO/NIO与IO区别
date: 2019-12-20 10:37:37
tags: Java
categories: [Java,Java8新特性,NIO]
---
## NIO主要内容

1. Java NIO简介
2. Java NIO 与 IO 的主要区别
3. 缓冲区(Buffer)和通道(Channel)
4. 文件通道(FileChannel)
5. NIO 的非阻塞式网络通信
    - 选择器(Selector)
    - SocketChannel、ServerSocketChannel、DatagramChannel
6. 管道(Pipe)
7. Java NIO2 (Path、Paths 与 Files

## Java NIO简介

Java NIO（New IO）是从Java 1.4版本开始引入的一个新的IO API，可以替代标准的Java IO API。NIO与原来的IO有同样的作用和目的，但是使用的方式完全不同，NIO支持面向缓冲区的、基于通道的IO操作。NIO将以更加高效的方式进行文件的读写操作。

## Java NIO 与 IO 的主要区别

|IO|NIO|
|--|--|
|面向流(Stream Oriented) |面向缓冲区(Buffer Oriented)|
|阻塞IO(Blocking IO) |非阻塞IO(Non Blocking IO)|
|(无) |选择器(Selectors)|

## 通道（Channel）与缓冲区（Buffer）

Java NIO系统的核心在于：通道(Channel)和缓冲区(Buffer)。通道表示打开到 IO 设备(例如：文件、套接字)的连接。若需要使用 NIO 系统，需要获取用于连接 IO 设备的通道以及用于容纳数据的缓冲区。然后操作缓冲区，对数据进行处理。

`简而言之，Channel 负责传输， Buffer 负责存储`
