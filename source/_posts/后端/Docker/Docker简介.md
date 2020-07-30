---
title: Docker简介
p: 后端/Docker/Docker简介
date: 2020-07-27 21:12:05
tags: Docker
categories: Docker
---
## 简介

1. 更高效的利用云资源
2. 更快速的启动时间
3. 一致的运行环境
4. 持续交付和部署
5. 更轻松的迁移
6. 更轻松的维护和扩展

> Docker使用客户端-服务器(C/S)架构模式，使用原厂API来管理和创建Docker容器。
  Docker容器通过Docker镜像来创建。
  容器与镜像的关系类似于面向对象编程中的对象与类

|标题|说明|
|--|--|
|镜像（Images）|Docker镜像是用于创建Docker容器的模板|
|容器（Container）|容器是独立运行的一个或一组应用|
|客户端（Client）|Docker客户端通过命令行或者其他工具使用Docker API与Docker的守护进程通信。|
|主机（Host）|一个物理或者虚拟的机器用于执行DOcker守护进程和容器。|
|仓库（Registry）|Docker仓库用来保存镜像，可以理解为代码控制中的代码仓库。Docker Hub提供了庞大的镜像集合供使用。|
|Docker Machine|Docker Machine是一个简化Docker安装的命令行工具，通过一个简单的命令行即可在相应的平台撒花姑娘安装Docker，比如VitrualBox、Digtal Ocean、Microsoft Azure|

Docker仓库 -> Docker镜像 -> Docker容器

## Docker仓库

分为公有仓库和私有仓库

Docker CE 社区版 免费版
Docker EE 企业版 收费版

## Dockers镜像

Docker镜像采用的是分层存储技术，下载也是一层一层的。

### 镜像体积

如果仔细观察，会注意到，这里的表示的所占用空间和在DockerHub上看到的镜像的大小不同。比如，ubuntu:16.04镜像大小，在这里是127MB，但是在DockerHub显示的确实50MB。这是因为DokrHub中显示的体积是压缩后的体积。在镜像下载和上传过程中镜像是保持着压缩状态的，因此Docker Hub所显示的大小是网络传输中更关心的流量大小，而`docker image ls`显示的是镜像下载到本地后，解压的大小，因为镜像到本地后，查看空间的时候，更关心的是本地磁盘空间占用的大小。

另外一个需要注意的问题是，`docker image ls`列表中的镜像体积综合并非是所有镜像实际硬盘消耗。由于Docker镜像是多层存储结构，并且可以继承、复用，因此不同镜像可能会因为使用相同的基础镜像，从而拥有共同的层。由于Docker使用Union FS，相同的层只需要保存一份即可，因此实际镜像硬盘占用空间可能要比这个列表镜像大小的总和要小的多。
你可以通过以下命令来查看镜像、容器、、数据卷所占用的空间。

```docker
$ docker system df
```

### 虚悬镜像

上面的镜像列表中，还可以看到一个特殊的镜像，这个镜像没有仓库名，也没有标签，均为`<none>`

```docker
<none>  <none>  00284dsa8df   5 days ago    324MB
```

这个镜像原本是有镜像名和标签的，原来为`mongo:3.2`,随着官方维护发布新版本，重新`docker pull mongo:3.2`时，`mongo:3.2`这个镜像名呗转移到了新下载的镜像身上，而旧的镜像上的这个名称则被取消，从而成为了`<none>`。除了`docker pull`可能导致这种情况，`docker build`也同样可以导致这种现象。由于新旧镜像同名，旧镜像名称被取消，从而出现仓库名、标签均为`<none>`的镜像。这类无标签镜像也被成为虚悬镜像，可以使用`docker images ls -f dangling=true`

一般来说，虚悬镜像已经失去了存在的价值，是可以随意删除的，可以用`dicjer image prune`命令删除
