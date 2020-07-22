---
title: Linux软件的安装与卸载
p: 操作系统/Linux/Linux软件的安装与卸载
date: 2020-07-22 22:04:59
tags: Linux
categories: Linux
---
## 软件的安装与卸载

apt 软件包管理程序
ubuntu 下载软件包管理是通过apt
centos通过yum来进行软件包管理程序

安装文件
    安装版
    绿色版  压缩包

## 命令

安装：`sudo apt-get install nano`
删除：`sudo apt-get remove nano`
查看系统版本：`lsb_release -a`

```linux
Distributor ID: Deepin
Description:    Deepin 20 Beta
Release:        20 Beta
Codename:       n/a
```

编辑数据源：`vi /etc/apt/sources.list`
将数据源改为阿里的镜像数据源`deb [by-hash=force] https://mirrors.aliyun.com/deepin/ panda main contrib non-free`
更新数据源：`sudo apt-get update`
更新软件,就重新安装一遍
