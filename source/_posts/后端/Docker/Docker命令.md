---
title: Docker命令
p: 后端/Docker/Docker命令
date: 2020-07-28 22:03:30
tags: Docker
categories: Docker
---
## 使用脚本自动安装

```shell
$ curl -fsSL get.docker.com -o get-docker.sh
$ sh get-docker.sh --mirror Aliyun           # 从阿里云国内资源上下载
```

## Ubuntu 16.04+、Debian8+、CentOS7

对于使用systemd的系统，请在`/etc/docker/daemon.json`中写入如下内容（如果文件不存在请新建该文件）

```json
{
    "registry-mirrors": [
        "https://registry.docker-cn.com"
    ]
}
```

**注意，一定要保证该文件符合json规范，否则Docker将不能启动**
之后重启服务

```shell
systemctl daemon-reload
systemctl restart docker
```

## 获取镜像

```docker
docker pull [选项] [Docker Registry 地址[:端口号]/]仓库名[:标签]
docker images 展示所有镜像
```

## 运行

\相当于window里的回车

```docker
$ docker run -it --rm \
    ubuntu:16.04 \
    bash
$ cat /etc/os-relaesase
$ exit
```

- -it: 以交互的形式运行容器。这是两个参数，一个是-i ：交互式操作，一个是-t ：终端。
- -rm: 这个参数是说容器退出后自动删除容器，默认情况下，为了排障需求，退出的容器并不会立即删除，除非手动`docker rm`。
- bash: 放在镜像名后的是命令，这里我们希望有个交互式Shell，因此用的是bash。
- exit: 退出系统

### 守护态运行

在run命令后加上-d参数

```docker
docker run -d tomcat
```

### 交互的方式进入容器

`docker exec -it [容器ID] bash`

### 交互的方式启动容器

`docker run -it tomcat bash`

## 删除

删除容器是`docker rmi 容器id`
删除镜像是`docker rm 镜像id`

## 定制镜像

```shell
cd /usr/local/
mkdir docker
cd docker
mkdir tomcat
cd tomcat
vi Dockerfile

FROM tomcat:8.5.32
RUN echo "Hello Docker" > /usr/local/tomcat/webapps/ROOT/index.html


FROM tomcat:8.5.32

WORKDIR /usr/local/tomcat/webapps/ROOT/
RUN rm -fr *
RUN echo "Hello Docker" > /usr/local/tomcat/webapps/ROOT/index.html

// 构建镜像
docker build -t mytomcat .
```

## 镜像构建上下文（Context）

如果注意，会看到`docker build`命令最后有一个`.`。表示当前目录，而`Dockerfile`就在当前目录，因此不少初学者以为这个路径是指`Dockerfile`所在路径，这么理解其实是不准确的。如果对应上面的命令格式，你可能会发现，这是在指定**上下文路径**，那么什么是上下文呢？

首先我们要理解`docker build`的工作原理。Docker在运行时分为Docker引擎（也就是服务端守护进程）和客户端工具。Docker的引擎提供了一组REST API，被称为Docker Remote API，而如`docker`命令这样的客户端工具，则是通过这组API与Docker引擎交互，从而完成各种功能。因此，虽然标明上我们像是在本机执行各种`docker`功能，但实际上，一切都是使用的远程调用形式在服务端（Docker引擎）完成。也因为这种C/S设计，让我们操作远程服务器的Docker引擎变得轻而易举。

当我们进行镜像构建的时候，并非所有与定制都会通过`RUN`指令完成，经常会需要将一些本地文件复制进镜像，比如通过`COPY`指令、`ADD`指令等。而`docker build`命令构建镜像，其实并非在本地构建，而是在服务端，也就是Docker引擎中构建的。那么在这种客户端/服务端的架构中，如何才能让服务端获得本地文件呢？

这就引入了上下文的概念。当构建的时候，用户会指定构建镜像上下文的路径，`docker build`命令得知这个路径后，会将路径下的所有内容打包，然后上传给Docker引擎。这样Docker引擎收到这个上下文包后，展开就会获得构建镜像所需要的的一起文件。

```shell
COPY ./package.json/app/
```

这并不是要赋值执行`docker build`命令所在的目录下的`package.json`,也不是复制`Dockerfile`所在目录下的`package.json`，而是复制**上下文（context）**目录下的`package.json`。

因此，`COPY`这类指令中的源文件的路径都是相对路径。这也是初学者经常会问的为什么`COPY ../package.json/app`或者`COPY /opt/xxxx/app`无法工作的原因，因为这些路径已经超出了上下文的范围，DOcker引擎无法获得这些位置的文件。如果真的需要那些文件，应该讲他们复制到上下文目录中去。

现在就可以理解刚才的，命令`docker build -t nginx:v3 .`中的这个`.`，实际上是在指定上下文的目录，`docker build`命令会将该目录下的内容打包交给DOcker引擎一帮助构建镜像。

{% asset_img 上下文.png 上下文 %}

## Dockerfile

```docker
FROM [镜像名：版本号]选取一个镜像
WORKDIR [工作目录]切换工作目录
RUN 运行一个容器，后面可以接shell命令
ADD [文件名] [粘贴的目标路径]复制文件，如果是压缩包并且格式为tar、gzip、bzip2、xz就会复制后解压缩
EXPOSE <端口1> [<端口2> ...]暴露端口
```