---
title: Linux操作命令
p: 操作系统/Linux/Linux操作命令
date: 2020-07-21 22:24:55
tags: Linux
categories: Linux
---

## 系统管理命令

[参考手册](https://www.runoob.com/linux/linux-command-manual.html)

### 交换空间

内存 1GB 超过   内存溢出 会造成阻塞崩溃宕机
磁盘 1TB swap 交换空间，虽然会防止内存溢出，但是会减慢速度拖垮系统
云服务器    一台超级计算机 1TB 硬盘 128G 内存，云服务器是没有交换空间的
                开辟一个空间    20GB 1G

### Linuxw文件操作命令

```shell
如果无法找到ls或其他命令
export PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin
压缩文件
# tar -xzvf test.tar.gz
```

tail 命令可用于查看文件的内容，有一个常用的参数 -f 常用于查阅正在改变的日志文件。
tail -f filename 会把 filename 文件里的最尾部的内容显示在屏幕上，并且不断刷新，只要 filename 更新就可以看到最新的文件内容。
显示文件 notes.log 的内容，从第 20 行至文件末尾:`tail +20 notes.log`
显示文件 notes.log 的最后 10 个字符: `tail -c 10 notes.log`

### 重启

- reboot
- shutdown -r now

### 关机

- shutdown -h now

### 查询正在运行的进程

使用ps -ef查看所有进程
使用ps axu，查看所有进程
如果想查询特定进程，可以使用

（1）ps -ef|grep 名称

（2）ps aux|grep 名称

### 停止进程

通过kill 进程id的方式可以实现,
首先需要知道进程id, 例如,想要杀死firefox的进程,通过 ps -ef|grep firefox,可以查到firefox的进程id:
然后通过 kill 3781 就可以关闭进程了.
补充: 1. kill -9 来强制终止退出, 例如: kill -9 3781

### 查询程序内存或cpu的使用情况

- htop常用功能键

    F1 : 查看htop使用说明
    F2 : 设置
    F3 : 搜索进程
    F4 : 过滤器，按关键字搜索
    F5 : 显示树形结构
    F6 : 选择排序方式
    F7 : 减少nice值，这样就可以提高对应进程的优先级
    F8 : 增加nice值，这样可以降低对应进程的优先级
    F9 : 杀掉选中的进程
    F10 : 退出htop

    / : 搜索字符
    h : 显示帮助
    l ：显示进程打开的文件: 如果安装了lsof，按此键可以显示进程所打开的文件
    u ：显示所有用户，并可以选择某一特定用户的进程
    s : 将调用strace追踪进程的系统调用
    t : 显示树形结构

    H ：显示/隐藏用户线程
    I ：倒转排序顺序
    K ：显示/隐藏内核线程
    M ：按内存占用排序
    P ：按CPU排序
    T ：按运行时间排序

    上下键或PgUP, PgDn : 移动选中进程
    左右键或Home, End : 移动列表
    Space(空格) : 标记/取消标记一个进程。命令可以作用于多个进程，
例如 "kill"，将应用于所有已标记的进程

## Linux编辑器

### vim

- 编辑模式：等待编辑命令输入
- 插入模式：编辑模式下，输入i进入插入模式，插入文本信息
- 命令模式：在编辑模式下，输入:进行命令模式

```vim
sheft + g 直达文件底部
```

### 命令

1. :q 直接退出vi
2. :wq 保存后退出vi，并可以新建文件
3. :q! 强制退出
4. :w file 将当前内容保存成某个文件
5. :set number 在编辑文件显示行号
6. :set nonumber 在编辑文件不显示行号

### nano

nano是一个字符中断的文本编辑器，有点像DOS下的editor程序。它比vi/vim要简单的多，比较审核Linux初学者使用。某些Linux发行版的默认编辑器就是nano。

## 查看Linux服务器的外网访问地址

```linux
1、curl ifconfig.me
2、curl cip.cc
3、curl icanhazip.com
4、curl ident.me
5、curl ipecho.net/plain
6、curl whatismyip.akamai.com
7、curl tnx.nl/ip
8、curl myip.dnsomatic.com
9、curl ip.appspot.com
10、curl -s checkip.dyndns.org | sed 's/.*IP Address: \([0-9\.]*\).*/\1/g'
```

## 解压压缩包

```
gzip -d  压缩文件    // 不保留原来的压缩文件
```