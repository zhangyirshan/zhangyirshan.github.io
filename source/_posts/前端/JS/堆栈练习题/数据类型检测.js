/*
基于typeof检测出来的结果
1. 首先是一个字符串
2. 字符串中包含对应的类型
局限性
1. typeof null => "object" 但是null并不是对象
2. 基于typeof无法细分出当前值是普通对象还是数组对象等，应为只要是对象数据类型，返回的结果都是"object"
*/
console.log(typeof 1)
let a = NaN
console.log(typeof a)
console.log(1)

console.log(typeof typeof typeof [])