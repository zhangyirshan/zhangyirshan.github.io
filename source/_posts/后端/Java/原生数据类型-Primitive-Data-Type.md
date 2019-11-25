---
title: 原生数据类型(Primitive Data Type)
date: 2019-11-22 13:59:24
tags: Java
categories: [Java,Java SE]
---

## 轻量级编辑器

Windows：notepad、editplus、ultraedit、gvim

Linux：vi、vim、gedit

## 1、Java 中的数据类型分为两大类

1） 原生数据类型 （Primitive Data Type）

2） 引用类型（对象类型） （Reference Type）

## 2、变量与常量

所谓常量，就是值不会变化的量；所谓变量，就是值可以变化的量。

### （1）如何定义变量

变量类型 变量名;

```java
int a;
```

### （2）如何为变量赋值

变量名 = 变量值;

```java
a = 2;
```

= 表示赋值，将等号右边的值赋给了左边的变量。

Java 中使用==表示相等，等价于数学中的=。

### （3）综合变量定义与赋值

变量类型 变量名;

变量名 = 变量值;

```java
int a;
a = 1;
```

可以将上面两个步骤合二为一：
变量类型 变量名 = 变量值;

```java
int a = 1;
```

```java
package JavaBase.PrimitiveDataType;

public class Vairable {
    public static void main(String[] args) {
        int a = 1;
        System.out.println(a);
        System.out.println("a");
    }
}

```

`结果是：
1
a`

## 3、变量名

在 Java 中，变量名以下划线、字母、 $符号开头，并且后跟下划线、字母、 $符号以及数字。 总之，<font color=red>Java 中的变量名不能以数字开头。</font>

### （1）关于计算机系统中的数据表示位

bit（只有 0， 1 两种状态），是计算机系统中的最小数据表示单位。
字节： byte， 1 byte = 8 bit。
1 KB = 1024 Byte （1Kg = 1000g，与计算机系统不同）
1 MB = 1024 KB
1 GB = 1024 MB

## 4、原生数据类型

1. Java 中的原生数据类型共有 8 种

    1） 整型：使用 int 表示。（32 位）

    2） 字节型：使用 byte 表示。（表示-128～127 之间的 256 个整数）。

    3）短整型：使用 short 表示。（16 位）

    4） 长整型：使用 long 表示。（64 位）

    5）单精度浮点型：使用 float 表示。 所谓浮点型，指的就是小数，比如 1.2。

    6）双精度浮点型：使用 double 表示。 双精度浮点型表示的数据范围要比单精度浮点型大。

    7） 字符型：使用 char 表示（char 是 character 的缩写）。所谓字符， 就是单个的字符表示，比如字母 a，或者中文张，外面用单引号包围上。比如 char a = ‘B’; char b = ‘张’;

    8）布尔类型，使用 boolean 表示。 布尔类型只有两种可能值，分别是 true 与 false。

2. Java 中的所有浮点类型默认情况下都是 double。 不能将 double 类型的值赋给 float 类型的变量，即便该 double 类型的值处于 float 类型的范围内也是不可以的。总之，能否成功赋值取决于等号右边的值类型与等号左边的变量类型是否一致。

3. 如何将 double 类型的值赋给 float 类型的变量？ 答案就是(1)强制类型转换，将 double 类型的值强制转换为 float 类型。 (2)使用 java 预言的支持。强制转换的语法： 类型 变量名 = （类型） 变量值;

4. 变量在使用前必须要赋值；变量必须要声明其类型方可使用；变量在使用前必须要定义，并且只能定义一次。

5. 如下代码无法通过编译：

    ```java
    int a = 1;
    short b = a;
    ```

    a 是 int 类型， b 是 short 类型， int 类型表示的数据范围要比 short 类型大，不能将表示范围大的值赋给表示范围小的变量。

6. 如下代码可以通过编译：

    ```java
    short a = 1;
    int b = a;
    ```

    a 是 short 类型， b 是 int 类型， int 类型表示的数据范围要比 short 类型大，可以将表示范围小的值赋给表示范围大的变量。

7. 总结：可以将表示范围小的值赋给表示范围大的变量；但不能直接将表示范围大的值赋给表示范围小的变量，只能通过强制类型转换实现。
