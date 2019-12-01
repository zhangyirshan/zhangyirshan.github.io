---
layout: "后端\ajax\ajax_数据格式"
title: Ajax_数据格式
date: 2019-11-27 14:30:14
tags: Ajax
categories: Ajax
---

## 数据格式提要

1. 在服务器端AJAX是一门与语言无关的技术。再业务逻辑层使用何种服务器端语言都可以。
2. 从服务器端接受数据的时候，那些数据必须以浏览器能够理解的格式来发送。服务器端的编程语言只能以如下3中格式返回数据：
    - XML
    - JSON
    - HTML

## 解析HTML

1. HTML由一些普通文本组成。如果服务器通过XMLHttpRequest发送HTML，文本将存储在responseText属性中。
2. 不必从responseText属性中读取数据。它已经是希望的格式，可以直接将他插入到页面中。
3. 插入HTML代码最简单的方法是更新这个元素的innerHTML属性

```js
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript">

        window.onload = function () {
            var aNodes = document.getElementsByTagName("a");
            for (var i = 0; i < aNodes.length; i++) {
                aNodes[i].onclick = function () {

                    var  request = new XMLHttpRequest();
                    var method = "GET";
                    var url = this.href;

                    request.open(method, url);
                    request.send(null);

                    request.onreadystatechange = function () {
                        if (request.readyState == 4) {
                            if (request.status == 200 || request.status == 304) {
                                document.getElementById("details").innerHTML = request.responseText;
                            }
                        }
                    }

                    return false;
                }
            }
        }

    </script>
</head>
<body>
    <h1>people</h1>
    <ul>
        <li><a href="files/andy.html"> Andy</a></li>
        <li><a href="files/richard.html"> Richard</a></li>
        <li><a href="files/jeremy.html"> Jeremy</a></li>
    </ul>
    <div id="details"></div>
</body>
</html>
```

### 优点

- 从服务器端发送的HTML代码在浏览器端不需要用JavaScript进行解析。
- HTML的可读性好。
- HTML代码块与innerHTML属性搭配，效率高

### 缺点

- 若需要通过AJAX更新一篇文档的多个部分，HTML不合适
- innerHTML并非DOM标准

## 解析XML

```java
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript">

        window.onload = function () {
            var aNodes = document.getElementsByTagName("a");
            for (var i = 0; i < aNodes.length; i++) {
                aNodes[i].onclick = function () {

                    var  request = new XMLHttpRequest();
                    var method = "GET";
                    var url = this.href;

                    request.open(method, url);
                    request.send(null);

                    request.onreadystatechange = function () {
                        if (request.readyState === 4) {
                            if (request.status === 200 || request.status === 304) {
                                //1.结果为XML格式，所有需要使用responseXML来获取
                                var result = request.responseXML;
                                //2. 结果不能直接使用，必须先创建对应的节点，再把节点加入到#details中
                                //目标格式为：
                                /*
                                 *<h2>Andy</h2>
                                 *<a href="www.baidu.com">www.baidu.com</a>
                                 */
                                var name = result.getElementsByTagName("name")[0].firstChild.nodeValue;
                                var website = result.getElementsByTagName("website")[0].firstChild.nodeValue;
                                var email = result.getElementsByTagName("email")[0].firstChild.nodeValue;

                                // alert(name);
                                // alert(website);
                                // alert(email);

                                var aNode = document.createElement("a");
                                aNode.appendChild(document.createTextNode(name));
                                aNode.href = "mailto:" + email;

                                var h2Node = document.createElement("h2");
                                h2Node.appendChild(aNode);

                                var aNode1 = document.createElement("a");
                                aNode.appendChild(document.createTextNode(website));
                                aNode.href = website;

                                var detailsNode = document.getElementById("details");
                                detailsNode.innerHTML = "";
                                detailsNode.appendChild(aNode);
                                detailsNode.appendChild(aNode1);
                            }
                        }
                    }
                    return false;
                }
            }
        }

    </script>
    <title></title>
</head>
<body>
    <h1>people</h1>
    <ul>
        <li><a href="files/andy.xml"> Andy</a></li>
        <li><a href="files/richard.xml"> Richard</a></li>
        <li><a href="files/jeremy.xml"> Jeremy</a></li>
    </ul>
    <div id="details"></div>
</body>
</html>
```

### 优点

1. XML是一种通用的数据格式
2. 不必把数据强加到已定义好的格式中，而是要为数据自定义合适的标记。
3. 李彤DOM可以完全掌控文档。

### 缺点

1. 如果文档来自于服务器，就必须得包含文档含有正确的首部信息。若文档类型不正确，那么responseXML的值将是空的。
2. 当浏览器接收到长的XML文件后，DOM解析可能会很复杂

### JSON

1. JSON（javaScript Object Notation）一种简单的数据格式，比xml更轻巧。JSON是JavaScript原生格式，这意味着在JavaScript中处理JSON数据不需要任何特殊的API或工具包。
2. JSON的规则很简单：对象是一个无需的“‘名称/值’ 对”集合。一个对象以“{”（左括号）开始，“}”（右括号） 结束。每个名称后跟一个“：”冒号；“‘名称/值’ 对”照顾华北是固体不过“，”都好分隔。

#### JSON示例

```js
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript">

        var jsonObject = {
            "name": "matthew",
            "age": 12,
            "address":{"city":"上海", "school": "北京大学"},
            "teaching":function () {
                alert("Java EE,spring.....");
            }
        };
        alert(jsonObject.name);
        alert(jsonObject.address.city);

        jsonObject.teaching();
    </script>
</head>
<body>
</body>
</html>
```

JSON用冒号（而不是等号）来复制，每一条赋值语句用逗号隔开，整个对象用大括号封装起来，可用大括号分级潜逃数据。
对象描述中存储的数据可以是字符串，数字或者布尔值，对象描述也可存储函数，那就是对象的方法。

### 解析JSON

1. JSON只是一种文本字符串，他被存储在responseText属性中
2. 为了读取存储在responseText属性中的JSON数据，需要根据JavaScript的eval语句。<font color=blue>函数eval会把一个字符串当作它的参数，然后这个字符串会被当做JavaScript代码来执行。因为JSON的字符串就是由JavaScript代码构成的，所以它本身是可执行的</font>

#### 优点

1. 作为一种数据传输格式，JSON与XML很相似，但是它更加灵巧
2. JSON不需要从服务器端发送含有特定内容类型的首部信息

#### 缺点

1. 语法过于严谨
2. 代码不易读
3. eval函数存在风险
