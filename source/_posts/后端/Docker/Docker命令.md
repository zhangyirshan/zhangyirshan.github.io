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

### 交互的方式进入容器

`docker exec -it [容器ID] bash`

### 交互的方式启动容器

`docker run -it tomcat bash`

## 删除

删除容器是`docker rmi 容器id`
删除镜像是`docker rm 镜像id`
