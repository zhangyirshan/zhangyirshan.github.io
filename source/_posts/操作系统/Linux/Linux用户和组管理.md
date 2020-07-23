---
title: Linux用户和组管理
p: 操作系统/Linux/Linux用户和组管理
date: 2020-07-23 21:01:59
tags: Linux
categories: Linux
---
Linux操作系统个还是一个多用户系统，他允许多用户同时登录到系统上并使用资源。系统会根据账户来区分每个用户的文件，进程，任务和工作环境，是的每个用户工作都不受干扰。

## 使用Root用户

给root账户设置密码`sudo passwd root`
切换到root账户`su`
允许root账户远程连接 `vi /etc/ssh/sshd_config`

```shell
# Authentication:

LoginGraceTime 2m
# PermiRootLogin prohibit-password  注释此行
PermitRootLogin yes            #  加入此行
StrictModes yes

更改完文件后保存
重启服务
service ssh restart
```

## 组账户说明

### 私有组

当创建一个用户时没有指定属于哪个组，Linux救护建立一个与用户同名的私有组，次私有组只含有该用户

### 标准组

当创建一个用户时可以选定一个标准组，如果一个用户同时属于多个组时，登录后所属的组为主组，其他的位附加组。

## 账户文件说明

### /etc/passwd

每一行代表一个账户，众多账户是系统正常运行所必须的，例如bin，nobody每行定义一个用户账户，此文件对所有用户刻度。每行账户包含如下信息：
`root:x0:0:root:/root:/bin/bash`