---
title: Mina简介
p: 后端/Mina/Mina简介
date: 2020-08-17 21:52:11
tags: Mina
categories: Mina
---
## Mina服务端

> Mina apache 开发的一个开源的网络通信框架，基于我们java NIO来实现

1. NIOSocket
2. 设置编码解码过滤器
3. 设置一些session属性
4. 绑定一个端口

```shell
cmd访问本地服务器
telnet 127.0.0.1 7080
```

## Mina客户端

替代cmd的telnet

## 结构分析

1. mina在应用程序中处于什么地位
    主要屏蔽了网络通信的一些细节，对socket进行封装，并且是NIO的一个实现架构，可以帮助我们快速的开发网络通信。常常用于游戏的开发，中间件等服务端程序。
2. IOService接口
    用于描述我们的客户端和服务端接口，其子类是connector和Acceptor，分别用于描述我们的客户端和服务端。IOproceser多线程环境来处理我们的连接请求流程。IOFilter提供数据的过滤工作：包括编解码，日志等信息的过滤，Hanlder就是我们的业务对象，自定义的handler需要实现IOHandlerAcceptor
3. 大致看看我的类图结构

                        IOService
    IOconnector                         IOAcceptor
    NIOSocketConnector                  NIOSocketAcceptor

IOsession：描述的是客户端和服务端连接的描述。常常用于接受和发送数据。

总结：
       IOconnector -> IOProcessor -> IOFilter -> Handler
       IOAcceptor -> IOProcessor -> IOFilter -> Handler

## 长短连接

1. 长连接：通信双方长期的保持一个连接状态不断开，比如qq，当我们登录qq的时候，我们就去连接腾讯服务器，一旦建立连接，就不断开，除非发生异常，这样的范式就是长连接，对于长连接比较耗费IO资源。
2. 短连接：通信双方不是保持一个长期的连接状态，比如http协议，当客户端发起http请求，服务器处理http请求，当服务器处理完成之后，返回客户端数据后就断开连接，对于下次的连接请求需要重新发起。

## IOService

1. 它实现了对网络通信的客户端和服务端之间的抽象，用于描述客户端的子接口IOConnector，用于描述服务端的子接口IOAcceptor。
2. 它的作用是可以管理我们网络通信的客户端和服务端，并且可以管理连接双方的会话session，同样可以添加过滤器。
3. IOService类结构
                                    IOService
                        IOAcceptor                  IOConnector
                                    abstractIoService
                        abstractIOAcceptor          abstractIOConnector
                        NioSocketAcceptor           NioSocketConnector

### 常用API

### IOService常用API

1. getFilterChain() 获得过滤器链
2. setHandler(IoHandler handelr) 设置我们真正业务handler
3. getSessionConfig() 得到我们会话的配置信息
4. dispose() 在我们完成关闭连接的时候所调用的方法

### IOConnector常用API

1. connect(SocketAddress remoteAddress) 主要用户发起一个连接请求
2. setConnectTimeout(int connectTimeout) 连接超时的设置

### IoAcceptor常用API

1. bind(SocketAddress localAddress) 绑定端口
2. getLocalAddress() 获得本地ip地址

### NioSocketAcceptor常用API

1. accept（IoProcessor\<NioSession> processor, ServerSocketChannel handle) 接受一个连接
2. open(SocketAddress localAddress) 打开一个socketchannel
3. select() 获得我们的选择器

### NIOSocketConnector常用API

1. connect(SocketChannel handle,SocketAddress remoteAddress)用于描述连接请求
2. register(SocketChannel handle,AbstractPollingIoConnector.ConnectionRequest request) 注册我们的IO事件
3. select(int timeout) 返回选择器

## IOFilter

> IOFilte:对应用程序和网络传输，就是二进制数据和对象之间的相互转化，有相应的解码和编码器。这也是我们过滤器一种，我们对过了长期还可以做日志、消息确认等功能。它是在应用层和我们的业务层之间的过滤层

Client：业务handler之前会调用我们的过滤器
Server：同样在我们接收到数据的时候和发送数据的时候也调用了过滤器，然后交给我嗯的handler

## IOSession

1. 主要描述我们网络通信双方所建立的连接之间描述。
2. 可以完成对于连接的一些管理，可以发送或者读取数据，并且可以设置我们会话的上下文信息。

### IOSession常用API

1. getAttribute(Object key) 根据key获获得设置的上下文属性。
2. setAttribute(Object key,Object value)设置上下文属性
3. removeAttribute(Object key) 删除上下文属性
4. write(Object message)发送数据
5. read() 读取数据

## IOSessionConfig

1. 提供我们对连接的配置信息的描述，比如缓冲区的设置等等。
2. 设置读写缓冲区的信息，读和写的空闲时间以及设置读写超时信息。

### IOSessionConfig常用API

1. getBothIdleTime() 获得读写通用的空闲时间
2. setIdleTime(IdleStatus status,int idleTime)设置我们的读或者写的空闲时间
3. setReadBufferSize(int readBufferSize) 设置读缓冲区大小
4. setWriteTimeout(int writeTimeout) 设置写超时时间

## IOProcessor

1. Processor：是以NIO为基础实现的以多线程的方式来完成我们读写工作。
2. Processor的作用：是为我们的filter读写原始数据的多线程环境，如果mina不去实现的话，我们来实现NIO的话需要自己写一个非阻塞读写的多线程的环境。
3. 配置Processor的多线程环境
    - 通过NioSocketAcceptor(int processorCount)构造函数可以指定多线程的个数
    - 通过NioSocketConnector(int processorCount)构造函数也可以指定多线程的个数

## IOBuffer

基于java NIO 中的ByteBuffer做了封装，用户操作缓冲区中的数据，包括基本数据类型以及字节数组和一些对象，其本质就是可扩展的byte数组

IoBuffer的索引属性

{% asset_img iobuffer.png iobuffer%}

1. Capacity：代表当前缓冲区的大小
2. Position：理解成当前读写位置，也可以理解成下一个刻度数据单元的位置，Position<=Capicity的时候可以完成数据的读写操作
3. Limit：就是下一个不可以被读写缓冲区的单元的位置。Limit<=Capacity

### IOBuffer常用的API

1. static allocate(int capacity) 开辟已制定的大小缓冲区的空间
2. setAutoExpand(boolean autoExpand) 可以设置是否支持动态的扩展
3. putString(CharSequence val,CharsetEncoder encoder),putInt(int calue)等等方法实现让缓冲区中法如数据Putxxx()
4. flip() 就是把缓冲区的数据放入到流中，往往在发送数据之前调用
5. hasRemaining() 缓冲区中是否有数据: boolean是关于position<=limit=true,否则返回false
6. remaining() 返回的是缓冲区中可读数据的大小，limit-position的值
7. Rest和Clear
    reset() 实现情况数据
    Clear() 实现数据的覆盖，position=0，重新开始都我们缓冲区的数据

## 自定义协议

1. 自定义的编解码工厂：要实现编解码工厂就要实现ProtocolCodecFactory接口。
2. 实现自定义编解码器：
    1. 实现自定义解码器：实现ProtocolDecoder接口
    2. 实现自定义编码器：实现ProtocolEncoder接口
3. 根据我们自定义的编解码工厂来获得编解码对象

### 为什么要使用自定义的编码器

因为现实中往往不是通过一个字符串就可以传输所有的信息。我们传输的是自定义的协议包。并且能在应用程序和网络通信中存在对象和二进制流之前转化关系。所以我们需要结合业务编写自定义的编解码器

### 常用的自定义协议的方法

1. 定长的方式：Aa,bb,cc,ok,po等这样的通信方式
2. 定界符：helloworld|watchmend|...|...|通过特殊的符号来区别消息，这样方式会出现粘包，半包等现象。
3. 自定义协议包：包头+包体，包头：数据包的版本号，以及整个数据包长度，包体：实际数据。
