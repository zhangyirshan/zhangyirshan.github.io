---
title: git checkout 时出现 error invalid path 
date: 2022-01-07 14:34:19
tags: [错误,Git]
categories: [错误,Git]
---

## 问题

在Windows本地使用git checkout指定的remote branch时，出现了这种错误：

`error: invalid path 'src/utils/Aux.ts'`

## 解决办法

1. 关闭git对于NTFS文件系统的保护，随后再次git checkout即可。分析是因为原代码是Linux环境下编写的，导致文件系统出的锅。

`git config core.protectNTFS false`

2. 修改出问题的文件名

## 原因

以aux命名的文件在Windows系统上都不能被创建，aux是Windows的预留文件名。

其实除aux之外，Windows还有许多预留文件名不能用，无论后缀名是什么

```
CON, PRN, AUX, NUL, COM1, COM2, COM3, COM4, COM5, COM6, COM7, COM8, COM9, LPT1, LPT2, LPT3, LPT4, LPT5, LPT6, LPT7, LPT8, and LPT9
```
