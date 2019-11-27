---
layout: 后端/ajax
title: Ajax概述
date: 2019-11-27 14:08:04
tags: Ajax
categories: Ajax
---

## 什么是Ajax

1. Ajax的技术的产生
    - Ajax被认为是（Asynchronous【异步】 JavaScript and XML的缩写）。现在，允许浏览器与服务器通信无须刷新当前页面的技术都被叫做Ajax。
    - “Ajax”这个名字是在2005年2月，Adaptive Path的Jesse James Garrett再他的文章Ajax：A New Approach to Web Application中创造。
    - 而Ajax这项技术，是Google再Google Labs发布Google Maps和GoogleSuggest后真正为人所认识。
    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190701134153248.png)
    图1 Web的传统模型，客户端向服务器发送一个请求，服务器返回整个页面，如此反复。
    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190701134240626.png)
    图2 在Ajax模型中，数据在客户端与服务器之间独立传输。服务器不再返回整个页面。

2. 不用刷新整个页面便可与服务器通讯的方法：
    - Flash
    - Java applet
    - 框架:如果使用一组框架构造了一个网页，可以只更新其中一个框架，而不必惊动整个页面
    - 隐藏的iframe
    - XMLHttpRequest：该对象是对JavaScript的一个扩展，可使网页与服务器进行通信。是创建Ajax应用的最佳选择。**实际上通常把Ajax当成XMLHttpRequest对象的代名词**

## Ajax的工作原理图

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190701134724700.png)

## Ajax工具包

Ajax并不是一项新技术，它实际上是几种技术，每种技术各尽其职，以一种全新的方式聚合在一起

- <font color=red>服务器端语言</font>：服务器需要具备向浏览器发送特定信息的能力。<font color=blue>Ajax与服务器端语言无关。</font>
- <font color=red>XML</font>(eXtensible Markup Language,可扩展标记语言)是一种描述数据的格式，<font color=blue>Ajax程序需要某种格式化的格式来在服务器和客户端之间传递信息，XML是其中的一种选择</font>
- <font color=red>XHTML</font>（eXtended Hypertext Markup Language，使用扩展超媒体标记语言）和<font color=red>CSS</font>（Cascading Style Sheet，级联样式单）<font color=blue>标准化呈现；</font>
- <font color=red>DOM</font>（Document   Object Module，文档对象模型）实现动态显示和交互；
- 使用XMLHTTP组件<font color=red>XMLHttpRequest对象</font><font color=blue>进行异步数据读取</font>
- 使用<font color=red>JavaScript</font><font color=blue>绑定和处理所有数据</font>

## Ajax的缺陷

AJAX不是完美的技术。使用AJAX,它的一些缺陷不得不权衡一下：

- 由Javascript和AJAX引擎导致的浏览器的<font color=blue>兼容</font>
- 页面局部刷新，导致<font color=blue>后退等功能失效。</font>
- 对流媒体的支持没有FLASH、Java Applet好
- 一些手机设备（如手机、PDA等）支持性差。
