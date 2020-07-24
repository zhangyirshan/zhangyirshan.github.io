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

### 重启

- reboot
- shutdown -r now

### 关机

- shutdown -h now

## Linux编辑器

### vim

- 编辑模式：等待编辑命令输入
- 插入模式：编辑模式下，输入i进入插入模式，插入文本信息
- 命令模式：在编辑模式下，输入:进行命令模式

### 命令

1. :q 直接退出vi
2. :wq 保存后退出vi，并可以新建文件
3. :q! 强制退出
4. :w file 将当前内容保存成某个文件
5. :set number 在编辑文件显示行号
6. :set nonumber 在编辑文件不显示行号

### nano

nano是一个字符中断的文本编辑器，有点像DOS下的editor程序。它比vi/vim要简单的多，比较审核Linux初学者使用。某些Linux发行版的默认编辑器就是nano。