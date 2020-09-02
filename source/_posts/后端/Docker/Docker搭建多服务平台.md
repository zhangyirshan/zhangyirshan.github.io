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
