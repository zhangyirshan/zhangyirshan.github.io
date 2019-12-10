---
title: JS命名规范
p: 前端/JS/JS命名规范
date: 2019-12-10 20:54:16
tags: [前端,JS]
categories: [前端,JS]
---

## JS中的命名规范

- 严格区分大小写

```js
let Test=100;
console.log(test);//=>无法输出，因为第一个字母小写了
```

- 使用数字、字母、下划线、$，数字不能作为开头

```js
let $box; //=>一般用JQ获取的以$开头
let _box; //=>一般公共变量都是_开头
let 1box; //=>不可以，但是可以写box1
```

- 使用驼峰命名法：首字母小写，其余每一个有意义单词的首字母都要大写（命名尽可能语义化明显，使用英文单词）

```js
let studentInformation;
let studentInfo;
//常用的缩写：add/insert/create/new（新增）、update（修改）、delete/del/remove/rm（删除）、sel/select/query/get（查询）、info（信息）...

//不正确的写法
let xueshengInfo;
let xueshengxinxi;
let xsxx;
```

- 不能使用关键字和保留字

```js
当下有特殊含义的是关键字，未来可能会成为关键字的叫做保留字(？)
var let const function ...

var var = 10; //=>肯定不行的
```

//=>代码强迫症（代码洁癖）：良好的编程习惯、极客精神

## JS中常用的数据类型

- 基本数据类型
  - 数字number
    常规数字和NaN
  - 字符串string
    所有用单引号、双引号、反引号（撇）包起来的都是字符串
  - 布尔boolean
    true/false
  - 空对象指针null
  - 未定义undefined
- 引用数据类型
  - 对象数据类型object
        - {} 普通对象
        - [] 数组对象
        - /^[+-]?(\d|([1-9]\d+))(\.\d+)?$/ 正则对象
        - Math数学函数对象
        - 日期对象
        - ...
  - 函数数据类型function
