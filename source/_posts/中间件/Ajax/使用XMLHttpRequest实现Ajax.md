---
layout: 后端/ajax/使用xmlhttprequest实现ajax
title: 使用XMLHttpRequest实现Ajax
date: 2019-11-27 14:29:08
tags: Ajax
categories: Ajax
---
## XMLHttpRequest的概述

1. XMLHttpRequest最早是在IE5中以ActiveX组件的形式实现的。非W3C标准。
2. 创建XMLHttpRequest对象<font color=blue>（由于非标准所有实现方法不统一）</font>
    - Internet Explor把XMLHttpRequest实现为一个ActiveX对象
    - 其他浏览器（Firefox、Safari、Opera。。。）把它实现为一个本地的JavaScript
    - <font color=blue>XMLHttpRequest在不同浏览器上的实现是兼容的。</font>所有可以用同样的方式访问XMLHttpRequest实例的属性和方法，而不论这个实例创建的方法是什么。

## 创建XMLHttpRequest对象

为了每次下Ajax的时候都节省一点时间，可以把对象检查的内容打包成一个可服用的函数；

```js
function getHTTPObject(){
    var xhr = false;
    if(window.XMLHttpRequest){
        xhr = new XMLHttpRequest();
    } else if(window.ActiveXObject){
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xhr;
}
```

说明：对weindow.XMLHttpRequest的调用会返回一个对象或null，if语句会把调用返回的结果看作是true或false<font color=blue>（如果返回对象则为true，返回null则为false）。如果XMLHttpRequest对象存在，则把xhr的值设为对象的新实例。如果不存在，就去驾车ActiveObject的实例是否存在，如果答案是肯定的，则把微软XMLHTTP的新实例付给xhr

## XMLHttpRequest的方法

|方法|描述  |
|--|--|
| abort() | 停止当前请求 |
|  getAllResponseHeaders()  |  把HTTP请求的所有响应首部作为键/值对返回   |
|  getResponseHeader("header")  |   返回指定首部的串值  |
|  open("method","url")  |   建立对服务器的调用，Method参数可以是GET、POST或PUT、url参数额可以是相对URL或绝对URL  |
|  send(content)  |  向服务器发送请求   |
|  setRequestHeader("header","value")  |  把指定首部设置为所提供的值。在设置任何首部之前必须先调用open（）   |

## XMLHttpRequest的属性

|属性|描述  |
|--|--|
| onreadystatechange | 每个状态改变是都会触发这个事件处理器，通常会调用一个javaScript函数 |
| readyState | 请求的状态，由5个可取值，0：未初始化、1：正在加载、2：已经加载、3：交互中、4：完成 |
| responseText | 服务器的响应，表示为一个串 |
| responseXML | 服务器的响应，表示为XML，这个对象可以解析为DOM对象 |
| status | 服务器的HTTP状态码（200对应OK，404对应NotFound、等） |
| statusText |  HTTP状态码的相应文本（OK或NotFound等）|

## 发送请求

1. 利用XMLHttpRequest实例与服务器进行通信包含以下3个关键部分：
    - onreadystatechange事件处理函数
    - open方法
    - send方法
2. oreadystatechange：
    - 该事件处理函数由服务器触发，而不是用户
    - 在Ajax执行过程中，服务器会通知客户端当前的同学的状态，这依靠跟新XMLHttpRequest对象的readyState来实现。改变readyState属性是服务器对客户端连接操作的一种方式。每次readyState属性的改变都会触发readystatechange事件
3. open（method，url，asynch）
    - XMLHttpRequest对象的open方法<font color=blue>允许程序员用一个Ajax调用向服务器发送请求。</font>
    - method：请求类型，类似“GET”或“POST”的字符串。若只想从服务器检索一个文件，而不需要发送任何数据，使用GET（可以在GET请求里通过附加在URL上的擦汗寻字符串来发送数据，不过数据大小限制为2000个字符）。若需要向服务器发送数据，用POST。
    - 在某些情况下，有些浏览器会把多个XMLHttpRequest请求的结果缓存在同一个URL。如果对每个请求的响应不同，就会带来不好的结果。<font color=blue>在此将时间戳追加到URL的最后，就能确保URL的唯一性，从而避免浏览器缓存结果。</font>
    - url：路径字符串，指向你所请求的服务器上的那个文件，可以是绝对路径或相对路径。
    - asynch：表示请求是否要异步传输，默认值为true，指定true，在读取后面的脚本之前，不需要等服务器的的响应。指定false当脚本处理过程经过这点时，会停下来，一直等到Ajax请求执行完毕后再继续执行。
    ![</font>](https://img-blog.csdnimg.cn/20190701155453329.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)
4. send（data）
    - open方法定义了Ajax请求的一些细节，<font color=blue>send方法可为已经待命的请求发送指令</font>
    - data：将要传送给服务器的字符串；
    - <font color=blue>若选用的时GET请求，则不会发送任何数据，给send方法传递努力了即可：request.send(null);</font>
    - 当向send（）方法提供参数时，要确保open（）中指定的方法时POST，如果没有数据作为请求体的一部分发送，则使用null
    - 完整的Ajax的GET请求示例：

    ```js
    var request = getHttpObject();
    if(request){
        request.onreadystatechange = doSomeThing;
        request.open("GET","file.text",true);
        request.send(null);
    }
    ```

5. setRequestHeader(header,value)
    - 当浏览器向服务器请求页面时，它会伴随这个请求发送一组首部信息。这些首部信息是一系列描述请求的元数据（metadata）。首部信息用来声明一个请求时GET还是POST。
    - Ajax请求中，发送首部信息的工作可以由setRequestHeader该完成
    - 参数header：首部的名字；参数value；首部的值
    - 如果用POST请求向服务器发送数据，需要将“Content-type”的首部设置为“application/x-www-form-urlencoded".他会告知服务器正在发送数据，并且数据已经符合URL编码了。
    - 该方法必须再open（）之后才能调用
    - 完整的Ajax的POST请求示例：

    ```js
    var url = "../jsp/forumServlet";
    var nameValue = trim(document.forumAddForm.name.value);
    xhr.open("POST",url);
    xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
    xhr.send("method-name_isExist"+"&name"+nameValue);
    ```

## 接受响应

1. 用XMLHttpRequest的方法可向服务器发送请求。再Ajax处理过程中，XMLHttpRequest的如下属性可被服务器更改：
    - readState
    - status
    - responseText
    - responseXML
2. readyState
    - readyState属性表示Ajax请求的当前状。它的值用数字代表。
        - 0代表未初始化，还没有调用open方法
        - 1代表正在加载。open方法已被调用，但send方法还没有被调用
        - 2代表已加载完毕。send已被调用。请求已经开始
        - 3代表交互中。服务器正在发送响应
        - 4代表完成。响应发送完毕
    - 每次readyState值的改变，都会触发readystatechange事件。如果把onreadysataechange事件处理函数赋给一个函数，那么每次readyState值的改变都会引发该函数的执行。
    - readyState值的变化会因浏览器的不同而有所差异。但是当请求结束的时候，每个浏览器都会把readyState的值统一设为4.
3. status
    - 服务器发送给的每一个响应也都带有首部信息。三位数的状态码是服务器发送的响应中最重要的首部信息，并且属于超文本传输协议中的一部分。
    - 常用状态码及其含义：
        - 404没找到页面（not found）
        - 403禁止访问（forbidden）
        - 500内部服务器出错（internal service error）
        - 200一切正常（ok）
        - 304没有被修改（not modifiled）
    - 再XMLHttpRequest对象中，服务器发送的状态码都保存再status属性里。通过把这个值和200或304比较，可以确保服务器是否已发送一个成功的响应
4. responseText
    - XMLHttpRequest的responseText属性包含了从服务器发送的数据。它是一个HTML，XML或普通文本，这取决于服务器发送的内容。
    - 当readyState属性值变成4时，responseText属性才可用，表名Ajax请求已经结束

    ```js
    function doSomeThing(){
        if(request.readyState == 4){
            if(request.status == 200 || request.status == 304){
                alert(request.responseText);
            }
        }
    }
    ```

5. responseXML
    - 如果服务器返回的是XML，那么数据将储存再responseXML属性中。
    - 只用服务器发送了带有正确首部信息数据时，responseXML属性才是可用的。MIME类型必须为text/xml

## 例子

### GET请求

```js
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
    window.onload = function () {
        //1.获取a节点，并为onclick响应函数
        document.getElementsByTagName("a")[0].onclick = function() {
            //3. 创建一个XMLHttpRequest对象
            var request = new XMLHttpRequest();
            //4.准备发送请求的数据：url
            var url = this.href + "?time= " + new Date();
            var method = "GET";
            //5.调用XMLHttoRequest对象的open方法
            request.open(method,url);
            //6.调用XMLHttpRequest对象的send方法
            request.send(null);
            //7.为XMLHttpRequest对象添加onreadstatechange响应函数
            request.onreadystatechange = function() {
                //8.判断响应式否完成：XMLHttpRequest对象的readyState属性值为4的时候
                if(request.readyState == 4){
                    //9.判断响应是否可用：XMLHttpRequest对象status属性值为200
                    if(request.status == 200 || request.status == 304){
                        //10.打印响应结果：responseText
                        alert(request.responseText);
                    }
                }
            }

            //2. 取消a节点的默认行为
            return false;
        }
    }
</script>
</head>
<body>

<a href="helloAjax.txt">HelloAjax</a>

</body>
</html>
```

### POST请求

```js
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
    window.onload = function () {
        //1.获取a节点，并为onclick响应函数
        document.getElementsByTagName("a")[0].onclick = function() {
            //3. 创建一个XMLHttpRequest对象
            var request = new XMLHttpRequest();
            //4.准备发送请求的数据：url
            var url = this.href + "?time= " + new Date();
            var method = "POST";
            //5.调用XMLHttoRequest对象的open方法
            request.open(method,url);
            request.setRequestHeader("ContentType","application/x-www-form-urlencoded");
            //6.调用XMLHttpRequest对象的send方法
            request.send("name = 'matthew' ");
            //7.为XMLHttpRequest对象添加onreadstatechange响应函数
            request.onreadystatechange = function() {
                //8.判断响应式否完成：XMLHttpRequest对象的readyState属性值为4的时候
                if(request.readyState == 4){
                    //9.判断响应是否可用：XMLHttpRequest对象status属性值为200
                    if(request.status == 200 || request.status == 304){
                        //10.打印响应结果：responseText
                        alert(request.responseText);
                    }
                }
            }

            //2. 取消a节点的默认行为
            return false;
        }
    }
</script>
</head>
<body>

<a href="helloAjax.txt">HelloAjax</a>

</body>
</html>
```

```shell
Hello Ajax(*^_^*)
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190701162821788.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)
