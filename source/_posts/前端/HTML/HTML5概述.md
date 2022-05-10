---
title: HTML5概述
p: 前端/HTML/HTML概述
date: 2019-11-30 18:59:05
tags: [前端,HTML]
categories: [前端,HTML]
---
## HTML5新特性

1. 用于绘画的canvas标签
2. 用于媒介回访放的video和audio元素
3. 对本地离线存储的更好支持
4. 新的特殊内容元素
    如：article、footer、header、nav、section
5. 新的表单控件
    如：calendar、date、time、email、url、search
6. 浏览器的支持
    Safai、Chrome、Firefox以及Opera包括IE9基本支持了HTML5

## HTML基础讲解

1. 声明<!DOCTYPE>
    HTML也有多个不同的版本，只有完全明白页面中只用的确切HTML版本，浏览器才能完全正确地显示出HTML页面。这就是<!DOCTYPE>的用处

## WebStorage

1. 存储内容大小一般支持5MB左右（不同浏览器可能还不一样）
2. 浏览器端通过Window.sessionStorage和Window.localStorage属性来实现本地存储机制。
3. 相关API：
    1. xxxxStorage.setItem('key','value')
        该方法接受哦一个键和值作为参数，会把键值对添加到存储中，如果键名存在，则更新其对应的值。
    2. xxxxStorage.getItem('key')
        该方法接受哦一个键名作为参数，返回键名对应的值。
    3. xxxxStorage.removeItem('key')
        该方法接受一个键名作为参数，并把该键名从存储中删除。
    4. xxxxStorage.clear()
        该方法会清空存储中的所有数据。
4. 备注：
    1. SessionStorage存储的内容会随着浏览器窗口关闭而消失。
    2. LocalStorage存储的内容，需要手动清除才会消失
    3. xxxxxStorage.getItem(xxx)如果xxx对应的calue获取不到，那么getItem的返回值是null。
    4. Json.parse(null)的结果依然是null
