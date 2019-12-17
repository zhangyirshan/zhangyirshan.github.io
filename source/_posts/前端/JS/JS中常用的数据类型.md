---
title: JS中常用的数据类型
p: 前端/JS/JS中常用的数据类型
date: 2019-12-12 19:35:04
tags: [前端,JS]
categories: [前端,JS]
---
## JS中常用的数据类型

- 基本数据类型
  - 数字number：常规数字和NaN
  - 字符串string：所有用单引号、双引号、反引号（撇）包起来的都是字符串
  - 布尔boolean：true/false
  - 空对象指针：null
  - 未定义：undefined
- 引用数据类型
  - 对象数据类型object
        - {} 普通对象
        - [] 数组对象
        - /^[+-]?(\d|([1-9]\d+))(\.\d+)?$/ 正则对象
        - Math数学函数对象
        - 日期对象
        - ...
  - 函数数据类型function

## 基本数据类型

### number数字类型

> 包含：常规数字、NaN

NaN ：not a number
> 不是一个数，但它属于数字类型

NaN和任何值（包括自己）都不相等：NaN！=NaN，所以我们不能用相等的方式判断是否为有效数字

isNaN ：检测一个值是否为非有效数字，如果不是有效数字返回TRUE，反之返回false。

在使用isNaN进行检测的时候，受限会验证检测的值是否为数字类型，如果不是，先基于Number()这个方法，把值转换为数字类型，然后再检测

#### 把其他类型值转换为数字类型

- Number([val])
- parseInt/parseFloat([val],[进制])：也是转换为数字的方法，对于字符串来说，它是从左到右依次查找有效数字字符，直到遇到非有效数字字符，停止查找（不管后面是否还有数字，都不再找了），把找到的当作数字返回
- ==进行比较的时候,可能要出现把其他类型值转换为数字

## string字符串数据类型

> 所有用单引号、双引号、反引号（撇ES6模板字符串）抱起来的都是字符串

### 把其他类型值转换为字符串

- [val].toString
- 字符串拼接

```java
//===========字符串拼接
//四则运算法则中，除加法之外，其余都是数学计算，只有假发可能存在字符串拼接（一旦遇到字符串，则不是数学运算，而是字符串拼接）
console.log('10'+10)//=》'1010'
//用Number来转换（默认）
console.log('10'-10)
console.log('10px'-10)//=》NaN-10=NaN

let a = 10 + null + true + [] + true + undefined + '珠峰' + null + 32 + false + {name:"sss"}
/*
10 + null -> 10 + 0 = 10
10 + true -> 10 + 1 = 11
11 + [] -> 11 + '' = '11' 空数组变为数字，先要经历变为空字符串，遇到字符串直接变为字符串拼接
*/
console.log(a)// 11trueundefined珠峰null32false[object Object]
```

## boolean布尔数据类型

> 只有两个值true/false

### 把其他类型值转换为布尔类型

> 只有0、NaN、''、null、undifined五个值转换为FALSE，其余都转换为TRUE（而且没有任何的特殊情况）

- Boolean([val])
- !/!!
- 条件判断

## null/undefined

> null和undefined都代表的是没有

- null：意料之中（一般都是开始不知道值，我们手动先设置为null，后期再给予赋值操作）

```js
let num = null // let num = 0 一般最好用null作为初始的空值，因为零不是空值，他在栈内存中有自己的存储空间（占了位置）

num = 12
```