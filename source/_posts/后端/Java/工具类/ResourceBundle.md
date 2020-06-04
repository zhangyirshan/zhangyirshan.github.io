---
title: ResourceBundle
date: 2020-06-03 15:19:40
tags: Java
categories: [Java,Java工具类]
---

> 这个类主要用来解决国际化和本地化问题。国际化和本地化可不是两个概念，两者都是一起出现的。可以说，国际化的目的就是为了实现本地化。比如对于“取消”，中文中我们使用“取消”来表示，而英文中我们使用“cancel”。若我们的程序是面向国际的（这也是软件发展的一个趋势），那么使用的人群必然是多语言环境的，实现国际化就非常有必要。而ResourceBundle可以帮助我们轻松完成这个任务：当程序需要一个特定于语言环境的资源时（如 String），程序可以从适合当前用户语言环境的资源包（大多数情况下也就是.properties文件）中加载它。这样可以编写很大程度上独立于用户语言环境的程序代码，它将资源包中大部分（即便不是全部）特定于语言环境的信息隔离开来。

这使编写的程序可以：
    轻松地本地化或翻译成不同的语言
    一次处理多个语言环境
    以后可以轻松进行修改，以便支持更多的语言环境

说的简单点，这个类的作用就是读取资源属性文件（properties），然后根据.properties文件的名称信息（本地化信息），匹配当前系统的国别语言信息（也可以程序指定），然后获取相应的properties文件的内容。

使用这个类，properties需要遵循一定的命名规范，一般的命名规范是： 自定义名语言代码国别代码.properties，如果是默认的，直接写为：自定义名.properties。

比如：

myres_en_US.properties
myres_zh_CN.properties

myres.properties

当在中文操作系统下，如果myres_zh_CN.properties、myres.properties两个文件都存在，则优先会使用myres_zh_CN.properties，当myres_zh_CN.properties不存在时候，会使用默认的myres.properties。

没有提供语言和地区的资源文件是系统默认的资源文件。

资源文件都必须是ISO-8859-1编码，因此，对于所有非西方语系的处理，都必须先将之转换为Java Unicode Escape格式。转换方法是通过JDK自带的工具native2ascii.

狼人杀项目中的例子

{% asset_img 配置文件.png 配置文件 %}
{% asset_img 中文配置文件.png 中文 %}
{% asset_img 日文配置文件.png 日文 %}
