---
title: python基础
p: 后端/python/python基础
date: 2020-05-19 21:17:10
tags: python
categories: python
---
## 变量

1. 在使用变量之前，需要对其赋值
2. 变量名可以报考字母、数字、下划线，但变量名不能以数字开头

### 字符串

#### 原始字符串

原始字符串的使用非常简单，只需要在字符串钱家一个字母r即可，**注意，原始字符串不能在结尾放\会报错**

```python
str1 = r'C:\now'
str2 = r'C:\now\fish\a\'
会报错
```

##### 解决办法

1. 在后面拼接`str = r'C:\new\aaa\bbb'+'\\'`
2. 在后面加上[:-1] `str = r'C:\new\aaa\bbb\\'[:-1]`

#### 长字符串

如果想得到一个跨越多行的字符串，我们就需要使用三重引号`"""dsdssdsds"""`

### 整型

### 浮点型

e记法：科学计数法

```python
15000 == 1.5e4
0.001 == 1e-3
```

### 布尔类型

true为1，false为0

## 类型转换

```python
int('222')
int(3.2) == 3

float(520) == 520.0
float('23.4') = 23.4

str(5e2) == '5e+2'
```

## 类型检测

```python
type(5e0) -> float
type('ewew') -> str

isinstance(333,int) -> true
isinstance(false,bool) -> true
```

## 条件分支

```python
if 条件 :
    条件为真执行的操作
else:
    条件为假执行的操作
```

## while循环

```python
while 条件 :
    条件为真执行的操作
```
