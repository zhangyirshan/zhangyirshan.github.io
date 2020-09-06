---
title: Docker搭建多服务平台
p: 后端/Docker/Docker搭建多服务平台
date: 2020-09-02 20:31:23
tags: Docker
categories: Docker
---
## 搭建GitLab平台

```yml
version: '3'
services:
  web:
    image: 'twang2218/gitlab-ce-zh'
    restart: always
    hostname: '192.168.92.135'
    environment:
      TZ: 'Asia/Shanghai'
      GITLAB_OMNIBUS_CONFIG: |
        external_url 'http://192.168.92.135:8080'
        gitlab_rails['gitlab_shell_ssh_port'] = 2222
        unicorn['port'] = 8888
        nginx['listen_port'] = 8080
    ports:
      - '8080:8080'
      - '8443:443'
      - '2222:22'
    volumes:
      - /usr/local/docker/gitlab/config:/etc/gitlab
      - /usr/local/docker/gitlab/data:/var/opt/gitlab
      - /usr/local/docker/gitlab/logs:/var/log/gitlab
```

## Nexus

```yml
version: '3.1'

services:
  nexus:
      restart: always
      image: sonatype/nexus3
      container_name: nexus
      ports:
        - "8081:8081"
      volumes:
        - /usr/local/docker/nexus/data:/nexus-data
```

```linux
chmod 777 data
```

### 更新本地maven的settings.xml

```xml
<server>
  <id>nexus-releases</id>
  <username>qwe</username>
  <password>qweqweqweqw</password>
</server>

<server>
  <id>nexus-snapshots</id>
  <username>qwe</username>
  <password>eqweqweqwe</password>
</server>
```

### 更新项目的pom.xml文件

```xml
<distributionManagement>
    <repository>
        <id>nexus-releases</id>
        <name>Nexus Release Repository</name>
        <url>http://192.168.92.136:8081/repository/maven-releases/</url>
    </repository>
    <snapshotRepository>
        <id>nexus-snapshots</id>
        <name>Nexus Snapshot Repository</name>
        <url>http://192.168.92.136:8081/repository/maven-snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```

#### 注意

- ID名称必须要与settings.xml中Servers配置的ID名称保持一致
- 项目版本号中有SNAPSHOT标识的，会发布到Nexus Snapshots Repository，否则发布到Nexus Release Repository，并根据ID去匹配授权账号。

### 部署到仓库

```mvn
mvn deploy
```

### 从私服中拉取依赖

修改pom文件

```xml
<repositories>
    <repository>
        <id>nexus</id>
        <name>Nexus Repository</name>
        <url>http://192.168.92.136:8081/repository/maven-public/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>
```

### 如果报错401

修改nexus的允许上传和自己本地的settings.xml是否是运行自己调整后的

## Registry（镜像管理平台）

### 服务端

```yml
version: '3.1'
services:
  registry:
    image: registry
    restart: always
    container_name: registry
    ports:
      - 5000:5000
    volumes:
      - /usr/local/docker/registry/data:/var/lib/registry
```

```url
http://192.168.92.137:5000/v2/
```

### 客户端

```json
"insecure-registries": [
  "ip:5000"
]
```
