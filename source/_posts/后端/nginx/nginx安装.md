---
title: nginx安装
p: 后端/nginx/nginx安装
date: 2020-04-04 11:15:57
tags: nginx
categories: nginx
---
## 准备工作

1. 打开虚拟机，使用远程连接工具连接 linux 操作系统
2. 到 nginx 官网下载软件 `http://nginx.org`
{% asset_img nginx官网.png nginx官网%}

## 开始进行 nginx 安装

1. 安装 pcre 依赖
第一步 联网下载 pcre 压缩文件依赖
`wget http://downloads.sourceforge.net/project/pcre/pcre/8.37/pcre-8.37.tar.gz`
{% asset_img pcre.png pcre%}
