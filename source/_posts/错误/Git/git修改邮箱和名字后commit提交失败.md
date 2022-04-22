---
title: git修改邮箱和名字后commit提交失败 
date: 2022-04-22 14:34:19
tags: [错误,Git]
categories: [错误,Git]
---

## 问题

git的user.email和user.name配置错误，修改后git提交依旧失败

## 原因

因为上次的commit的email已经是错误的了，修改之后并未修改commit提交的email

## 解决办法

```git
git commit --amend --reset-author --no-edit
```
