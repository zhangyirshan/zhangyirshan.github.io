---
title: Docker容器数据持久化
p: 后端/Docker/Docker容器数据持久化
date: 2020-08-05 22:25:25
tags: Docker
categories: Docker
---
## 数据卷

> 数据卷是一个可供一个或多个容器使用的特殊目录，它绕过UFS，可以提供很多有用的特性：

- 数据卷可以在容器之间共享和重用
- 对数据卷的修改会立马生效
- 对数据卷的更新，不会影响镜像
- 数据卷默认会一直存在，及时容器被删除

注意：**数据卷的使用，类似于Linux下对目录或文件进行mount，镜像中的被指定为挂载点的目录中的文件会隐藏掉，能显示看的是挂载的数据卷**

```docker
docker run -p 3306:3306 --name mysql \
-v /usr/local/docker/mysql/conf:/etc/mysql \
-v /usr/local/docker/mysql/logs:/var/log/mysql \
-v /usr/local/docker/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql
```

如果执行sql失败吧/etc/mysql.conf.d/mysqldump.cnf的`max_allowed_packet = 16M`追加到配置文件末尾
`echo "max_allow_packet = 128M" >> mysqld.cnf`

## 将容器里的文件复制到宿主机

```docker
docker cp mysql:/etc/mysql .
复制mysql容器的/etc/mysql文件夹到当前目录
mv *.* ..
移动所有文件到上级目录
rm -fr mysql
删除空文件夹
```
