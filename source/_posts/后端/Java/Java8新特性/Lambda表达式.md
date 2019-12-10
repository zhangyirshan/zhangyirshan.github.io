---
title: Lambda表达式
p: 后端/Java/Java8新特性
date: 2019-12-02 09:34:53
tags: Java
categories: [Java,Java8新特性]
---
## 主要内容

1. Lambda 表达式
2. 函数式接口
3. 方法引用与构造器引用
4. Stream API
5. 接口中的默认方法与静态方法
6. 新时间日期 API
7. 其他新特性

## Java 8新特性简介

1. 速度更快
2. 代码更少（增加了新的语法 Lambda 表达式）
3. 强大的 Stream API
4. 便于并行
5. 最大化减少空指针异常 Optional
6. 其中最为核心的为 Lambda 表达式与Stream API

## Lambda表达式

1. Lambda表达式的基础语法：Java8中引入了一个新的操作符“->”该操作符成为箭头操作符或Lambda操作符
    箭头操作符将Lambda表达式拆分成两部分：
    左侧：Lambda表达式的参数列表
    右侧：Lambda表达式中所需执行的功能，即Lambda体
    - 语法格式一：无参数，无返回值
        `()->System.out.println("hello Lambda!");`
    - 语法格式二：有一个参数，并且无返回值
    - 语法格式三：若只有一个参数，小括号可以省略不写
    - 语法格式四：又两个以上的参数，并且有多条语句
    - 语法格式五：若Lambda体中只有一条语句，return和大括号都可以省略不写
    - 语法格式六：Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”

    ```java
    @Test
    public void test1(){
        int num = 0;//jdk1.7前，必须是final,现在默认加上了，可以省略
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world" + num);
            }
        };

        runnable.run();
        System.out.println("-------------");
        Runnable runnable1 = () -> System.out.println("Hello World!" + num);
        runnable1.run();
    }
    ```

2. Lambda表达式需要“函数式接口”的支持
    函数式接口:接口中只有一个抽象方法的接口，称为函数式接口。可以使用朱姐@FunctionalInterface修饰
    可以检查是否是函数式接口

## 函数式接口

什么是函数式接口？

1. 只包含一个抽象方法的接口，称为函数式接口。
2. 你可以通过 Lambda 表达式来创建该接口的对象。（若Lambda表达式抛出一个受检异常，那么该异常需要在目标接口的抽象方法上进行声明）。
3. 我们可以在任意函数式接口上使用 @FunctionalInterface 注解，这样做可以检查它是否是一个函数式接口，同时 javadoc 也会包含一条声明，说明这个接口是一个函数式接口。

作为参数传递 Lambda 表达式：为了将 Lambda 表达式作为参数传递，接收Lambda 表达式的参数类型必须是与该 Lambda 表达式兼容的函数式接口的类型。

## Java内置四大核心函数式接口

|函数式接口|参数类型|返回类型|用途|
|--|--|--|--|
|Consumer\<T>消费型接口|T|void|对类型为T的对象应用操作，包含方法：void accept(T t)|
|Supplier\<T>供给型接口|无|T|返回类型为T的对象，包含方法：T get();|
|Function\<T, R>函数型接口|T|R|对类型为T的对象应用操作，并返回结果。结果是R类型的对象。包含方法：R apply(T t);|
|Predicate\<T>断定型接口|T|boolean|确定类型为T的对象是否满足某约束，并返回boolean 值。包含方法boolean test(T t);|

## 方法引用与构造器引用

方法应用：若Lambda体重的内容有方法已经实现了，我们可以使用“方法引用”
可以理解为方法引用时Lambda表达式的另外一种表现形式

主要有三种语法格式：

- 对象::实例方法名

    ```java
    //对象::实例方法名
    @Test
    public void test1(){
        Consumer<String> consumer = (x) -> System.out.println(x);

        PrintStream ps1 = System.out;
        Consumer<String> consumer1 = (x) -> ps1.println(x);

        PrintStream ps = System.out;
        Consumer<String> consumer2 = ps::println;

        Consumer<String> consumer3 = System.out::println;
        consumer3.accept("aaaasdc");
    }
    ```

- 类::静态方法名

    ```java
    @Test
    public void test3(){
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

        Comparator<Integer> com1 = Integer::compare;
    }
    ```

- 类::实例方法名

### 注意

1. Lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致!

