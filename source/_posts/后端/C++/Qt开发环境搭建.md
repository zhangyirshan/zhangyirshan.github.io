---
title: Qt开发环境搭建
date: 2024-10-27 15:19:40
tags: C++
categories: [C++]
---
## 安装

### 第一步：（安装MinGW版QT）

首先安装MinGW版的Qt5,下载地址:<http://download.qt.io/archive/qt/5.7/5.7.1/qt-opensource-windows-x86-mingw530-5.7.1.exe.mirrorlist>

如果链接不能用可以从网上找其他的下载地址下载，只要能下载到这个安装包就行：qt-opensource-windows-x86-mingw530-5.7.1.exe

接着进行安装，安装的时候需要注意一点，需要选择以下Tools里面的MinGW5.3.0（默认是不选择的），一直Next下去，直到安装完成。

注意一定要安装这个MinGW版的Qt，本人之前用的MSVC版的Qt ，尝试多次安装都是不成功，后来还是选择了这个MinGW版的QT，如果你安装了MSVC版的也不要紧，可以再安装一个MinGW版的，只要安装路径不要一样就可以（不要和原来自己安装的弄混了）。

### 第二步：（配置系统变量）

右键“我的电脑”-》“高级系统设置”-》“环境变量”-》“系统变量”-》Path，添加如下四个新的系统变量：

{% asset_img  环境变量.png 环境变量 %}

{% asset_img  qt配置.png qt配置 %}

注意：我是安装到了E盘。大家根据自己的安装路径配置如上四个系统变量。

### 第三步：（在Clion中设置使用MinGW的环境）

没安装Clion的话，自己去安装一下，下载地址：<http://www.jetbrains.com/clion/download/#section=windows,安装完成后，可以从网上找一下激活码进行激活一下，如果长期使用，建议手动破解一下，具体破解教程：https://blog.csdn.net/iamjingong/article/details/80876430>

启动Clion后，左上角是点击“File”-》“settings”-》“Build，Excution，Deployment”-》“Toolchains”,然后将Environment改为“MinGW”，然后需要选择一下mingw的路径，具体如下图：

{% asset_img  clion配置.png clion配置 %}

{% asset_img  QtDesigner.png QtDesigner %}

### 第四步：新建一个Clion工程(测试一下能不能用)

新建QT项目然后运行项目就行啦~

大功告成：

{% asset_img  运行效果.png 运行效果 %}
