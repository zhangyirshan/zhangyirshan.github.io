# Java简介

## 1、Java的版本

Java SE：Java Standard Edition         基础版本

Java ME：Java Micro Edition           手机开发

Java EE:：Java Enterprise Edition       企业开发

Java是由Sun公司推出的（后被Oracle公司收购）。
收购价格：74亿美金
J2SE、J2ME、J2EE

## 2、[下载JDK](https://www.java.com/zh_CN/download/windows-64bit.jsp)

JDK：Java Development Kit（Java开发包）(Java开发必备）

JRE：Java Runtime Environment（java执行环境）

JDK包含了JRE

jdk 1.4 ，jdk1.5（5.0），jdk1.6（6.0）

jdk1.5（5.0）：Tiger，老虎

jdk1.6（6.0）：Mustang，野马

最新免费版是1.8

## 3、配置环境变量

设定环境变量（可以是用户变量，也可以是系统变量)，指向JDK安装目录中的bin目录

CLASSPATH：D:\Java\jdk1.8.0_191\lib

Path：D:\Java\jdk1.8.0_191\bin

通过运行，输入cmd或powershell打开窗口，输入java -version，显示出Java版本信息

## 4、Java程序的执行过程

可以直接使用windows记事本来编写Java程序，也可以使用Editplus，UltraEdit等高级文本编辑工具编写Java程序，还可以使用专业的IDE（Integrated Devellopment Environment）编写。

第一次使用，我们使用记事本实现一个Hello World的Java程序。所有的Java代码，其后缀都是以java结尾。

Java程序的执行过程分为两步：

1. 编译  javac 文件名.java
2. 执行  java 文件名

Class文件是字节码文件，程序最终执行的就是这个字节（bytecode）文件。

**Java是跨平台的语言，真正执行的不是二进制，而是字节码。
JVM（Java Virtual Machine，Java虚拟机）
Java是跨平台的，而JVM不是跨平台的（JVM是由C语言编写的）。
Java之所以能做到跨平台，本质原因在于JVM不是跨平台的。**

```java
public class Test{
    public static void main(String[] args){
        System.out.println("Hello Word");
    }
}

```

![HelloWorld](https://img-blog.csdnimg.cn/20190227113749540.png)
