---
title: 使用jQuery实现Ajax
p: /后端/Ajax/使用jQuery实现Ajax
date: 2019-11-27 16:51:17
tags: Ajax
categories: Ajax
---
## jQuery中的Ajax

jQuery对Ajax操作进行了封装，在jQuery中最底层的方法时$.ajax(),第二层使load(),$.get()和$.post(),第三层使$.getScript()和$.getJSON()

1. 什么是Ajax？
不用刷新页面，但可以和服务器进行通信的方式，使用Ajax的主要方式时XMLHttpRequest对象

2. 使用XMLHttpRequest对象实现Ajax【了解】

3. Ajax传输数据的三种方式
    XML：笨重，解析困难，但XML是通用的数据交换格式。
    HTML：不需要解析可以直接放到文档中，若仅更新一部分区域，但传输的数据不是很方便，且HTML代码需要拼装完成。
    JSON：小巧，有面向对象的特征，且有很多第三方的jar包可以把Java对象或集合转为JSON字符串。

4. 使用jQuery完成Ajax操作
    1). load方法：可以用于HTML文档的元素更新，把结果直接加为对应节点的子元素，通常而言，load方法加载后的数据时一个HTML片段。

    var $obj = ...
    var url = ...
    var args = {key:value...};
    $obj.load(url,args);

    2). $.get,$.post, $getJSON:更加灵活，除去使用load方法的情况，大部分时候都使用这3个方法。

        1. 基本的使用
        url:Ajax请求的目标URL
        args：传递的参数：JSON类型
        data：Ajax响应成功后的数据，可能是XML，HTML，JSON
        $.get(url,args,function(data){})

        2. 请求JSON数据
        $.get(url,args,function(data){},"JSON")
        $.post(url,args,function(data){},"JSON")
        $.getJSON(url,args,function(data){})

## 例子

### html

    ```js
    <script src="../jquery-3.4.1.js" type="text/javascript"></script>
    $(function () {
                $("a").click(function () {
                    var url = this.href + " h2, a";
                    $("#details").load(url);
                    //任何一个html节点都可以使用load方法来加载Ajax，结果将直接插入到html节点中。
                    return false;
                })
            })
    ```

### XML

    ```js
    $(function () {
                $("a").click(function () {
                    var url = this.href;
                    var args = {"time": new Date()};

                    //url:
                    //args:JSON格式
                    //function：回调函数，当响应结束时，回调函数被触发，响应结果在data中。
                    $.get(url,args,function (data) {
                        var name = $(data).find("name").text();
                        var email = $(data).find("email").text();
                        var website = $(data).find("website").text();

                        $("#details").empty()
                            .append("<h2><a href='mailto:" + email + "'>" + name + "</a></h2>")
                            .append("<a href='" + website + "'>" + website + "</a>");
                    });
                    return false;
                })
            })
    ```

### JSON

    ```js
    $(function () {
                $("a").click(function () {
                    var url = this.href;
                    var args = {"time": new Date()};

                    //url:
                    //args:JSON格式
                    //function：回调函数，当响应结束时，回调函数被触发，响应结果在data中。
                    $.getJSON(url,args,function (data) {
                        var name = data.person.name;
                        var email = data.person.email;
                        var website = data.person.website;

                        $("#details").empty()
                            .append("<h2><a href='mailto:" + email + "'>" + name + "</a></h2>")
                            .append("<a href='" + website + "'>" + website + "</a>");
                    });
                    return false;
                })
            })
    ```

### load方法

    ```js
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
        <script src="../jquery-3.4.1.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(function () {
                $("a").click(function () {
                    //使用load方法处理Ajax
                    var url = this.href;
                    var args = {"time": new Date()};
                    $("#content").load(url, args);
                    return false;
                })
            });
        </script>
    </head>
    <body>
        <a href="helloAjax.txt">HelloAjax</a>
        <div id="content"></div>
    </body>
    </html>
    ```
