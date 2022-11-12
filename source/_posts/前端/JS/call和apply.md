---
title: call和apply
p: 前端/JS/call和apply
date: 2022-11-06 12:20:26
tags: [前端,JS]
categories: [前端,JS]
---

## call

> call() 方法是预定义的 JavaScript 方法。
它可以用来调用所有者对象作为参数的方法。
通过 call()，您能够使用属于另一个对象的方法。

```js
var person = {
  fullName: function(city, country) {
    return this.firstName + " " + this.lastName + "," + city + "," + country;
  }
}
var person1 = {
  firstName:"Bill",
  lastName: "Gates"
}
console.log(person.fullName.call(person1, "Seattle", "USA"));
// 输出结果 Bill Gates,Seattle,USA
```

## call和apply的区别

1. call的语法：函数名.call(obj,参数1,参数2,参数3……);
2. apply的语法：函数名.apply(obj,[参数1,参数2,参数3……]);

这两个东西功能相同，就是把一个函数里面的this设置为某个对象，区别就是后面的参数的语法。call需要使用逗号分隔列出所有参数，但是apply是把所有参数写在数组里面。需要注意的是即使只有一个参数，也必须写在数组里面。

## 箭头函数

> A. 箭头函数内部的this是词法作用域，有上下文确定。<br/>
B. 箭头函数this是在‘定义函数’的时候绑定，而不是在‘执行函数’的时候绑定。<br/>
*词法作用域就是，你在写代码的时候就已经决定了变量的作用域，因此当词法分析器处理代码时会保持作用域不变。 <br/>
Javascript使用的是词法作用域。<br/>
它最重要的特征是，它的定义过程发生在代码的书写阶段。

1. 箭头函数没有自己的this对象。
2. 箭头函数的this永远指向其父作用域。
3. 任何方法都改变不了this，包括call，apply，bind。

## 面试考题

### 第一题

```js
console.log.call.call.call.apply((a) => a, [1, 2])

// 无输出结果
// 推到过程
// xxxx.apply(a,b) => a.xxx(b)
((a) => a).call(1,2)
// this指向的还是空对象，因为箭头函数的this不会改变所以a=2
console.log(console.log.call.call.call.apply((a) => a, [1, 2]))
// 输出结果为2
```

### 第二题

```js
console.log.call.apply.apply.call.apply((a) => console.log(this, this.a, a), [{ a: 1 }, 2])

//同理，此题的主要是考察箭头函数的this指向
// 输出结果，{} undefine 2
```