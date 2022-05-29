---
title: CSS样例
p: 前端/CSS/CSS样例
date: 2020-01-06 17:02:31
tags: [前端,CSS,样例]
categories: [前端,CSS]
---
## 鼠标滑过显示详情

```js
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>鼠标滑过显示详情</title>
    <!-- import css -->
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        .box {
            /* CSS3新盒子模型属性：控制WIDTH/HEIGHT是盒子最终的宽高*/
            box-sizing: border-box;
            margin: 20px auto;
            width: 200px;
            height: 40px;
            line-height: 40px;
            text-align: center;
            border: 1px solid lightcoral;
            position: relative;
        }

        .box:hover {
            border-bottom-color: #ffffff;
        }

        .box .detail{
            display: none;
            width: 500px;
            height: 100px;
            line-height: 100px;
            text-align: center;
            border: 1px solid lightcoral;
            box-sizing: border-box;
            position: absolute;
            right: -1px;
            top: 38px;
            /* 父div的级别调高子的也会受影响，所以应该将子的div级别调低*/
            z-index: -1;
            cursor: pointer;
        }

        .box:hover .detail{
            display: block;
        }

        /* 如果是点击实现显示没不需要基于JS也可以，可以基于:target实现手风琴效果*/
    </style>
</head>
<body>
    <!-- 基于CSS实现，我们需要让详情区域是按钮的子元素 -->
    <div class="box">
        <span>购物车</span>
        <div class="detail">
            购物车相关信息
        </div>
    </div>
</body>
</html>
```