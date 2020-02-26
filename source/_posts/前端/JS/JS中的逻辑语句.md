---
title: JS中的逻辑语句
p: 前端/JS/JS中的逻辑语句
date: 2020-01-06 15:12:41
tags: [前端,JS]
categories: [前端,JS]
---
## JS中的操作语句：判断、循环

### 判断

> 条件成立做什么? 不成立做什么?

- if/else if/ else
- 三元运算符
- switch case

1. if/else

    ```js
    if(条件){
        条件成立执行
    }else if(条件2){
        条件2成立执行
    }
    ...
    else{
        以上条件都不成立
    }
    ```

2. == VS ===

    == ：相等（如果左右两百年数据值类型不同，是默认向转换为相同的类型，然后比较）
    `'5' == 5  =>TRUE`
    === : 绝对相等（如果类型不一样，肯定不相等）
    `'5' == 5  =>FALSE`
    项目中为了保证业务的严谨，推荐使用===

### 循环

> 重复做某些事情就是循环

- for循环
- for in循环
- for of循环（ES6新增）
- while循环
- do while循环

#### for in

for(var 变量(key) in 对象)
对象中有多少组键值对，循环就执行几次（除非break结束）

```js
var obj = {
    name: '春亮',
    age: 52,
    friends: '王鹏，志刚',
    1: 223
    2: 33
    4: 231
}
for ( var key in obj){
    // 每一次循环key变量存储的值: 当前对象的属性名
    // 获取属性值：obj[属性名] => obj[key] obj.key/obj['key']
    console.log('' + key + '' + obj[key])
}
```

for in 在遍历的时候，优先循环数字属性名（从小到大）
