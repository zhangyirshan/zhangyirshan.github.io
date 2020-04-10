---
title: final
p: 后端/Java/高并发与多线程/final
date: 2020-04-02 17:56:55
tags: [Java,多线程]
categories: [Java,多线程]
---
## final关键字和不变性

1. 什么是不变性（Immutable）
2. final的作用
3. 3中用法：修饰变量、方法、类

## 什么是不变性（Immutable）

- 如果对象在被创建后，状态就不能被修改，那么它就是不可变的
- 具有不变性的对象一定是线程安全的，我们不需要对其采取任何额外的安全措施，也能保证线程安全。

## final的作用

- 早期
  - 锁定
  - 效率：早期的Java实现版本中，会将final方法转为内嵌调用
- 现在
  - 类防止被继承、方法防止被重写、变量防止被修改
  - 天生是线程安全的，而不需要额外的同步开销

## final的3种用法

### final修饰变量

- 含义：被final修饰的变量，意味着值不能被修改。如果变量是对象，那么对象的引用不能变，但是对象自身的内容依然可以变化

```java
final Person person = new Person();
person = new Person();// 会报错
person.bag = "book";
```

#### final修饰：3种变量

- final instance variable(类中的final属性)
- final static variable(类中的static final属性)
- final local variable（方法中的final变量）

#### final修改变量：赋值时机

- 属性被声明为final后，该变量则只能被赋值一次。且一旦被赋值，final的变量就不能再被改变，无论如何也不会变。

- final instance variable(类中的final属性)
  - 第一种是在声明变量的等号右边直接赋值
  - 第二种就是构造函数中赋值
  - 第三种就是在类的初始代码块中赋值（不常用）
  - 如果不采用第一种赋值方法，那么就必须再第2、3种挑一个来赋值，而不能不赋值，这是final语法所规定的

```java
public class FinalVariableDemo {
//    private final int a = 6;
    private final int a;

//    public FinalVariableDemo(int a) {
//        this.a = a;
//    }
    {
        a = 7;
    }
}
```

- final static variable(类中的static final属性)
  - 两个赋值时机：除了在声明变量的等号右边直接赋值外，static final变量还可以用static初始代码块赋值，但是不能用普通的初始代码块赋值

- final local variable（方法中的final变量）
  - 和前面两种不同，由于这里的变量是在方法里的，所有没有构造函数，也不存在初始代码块
  - final local variable不规定赋值时机，只要求在使用前必须赋值，这和方法种的非final变量的要求也是一样的

- 为什么要规定赋值时机？
  - 我们来思考一下为什么语法要这样？：如果初始化不赋值，后续赋值，就是从null变成你的赋值，这就违反final不变的原则了！

### final修饰方法

- 构造方法不允许final修饰
- 不可被重写，也就是不能被override
- 引申：static方法不能被重写

### final修饰类

- 不可被继承
- 例如典型的String类就是final的，我们从没见过那个类是继承String类的。

## 不变性和final的关系

- 不变性并不意味着，简单地用final修饰就是不可变
  - 对于基本数据类型，确实被final修饰后就具有不变性
  - 但是对于对象类型，需要该对象保证自身被创建后，状态永远不会变才可以

- 对象创建后，其状态就不能修改
- 所有属性都是final修饰的
- 对象创建过程种没有发送逸出

## 把变量写在线程内部——栈封闭技术

- 在方法里新建的局部变量，实际上是存储在每个线程私有的占空间，而每个栈的栈空间是不能被其他线程所访问到的，所以不会有线程安全问题。这就是著名的“栈封闭”技术，是“线程封闭”技术的一种情况

## 面试题 真假美猴王

```java
public class FinalStringDemo1 {
    public static void main(String[] args) {
        String a = "wukong2";
        final String b = "wukong";
        final String f = getDashixion();
        String d = "wukong";
        String c = b + 2;
        String e = d + 2;
        System.out.println(a == c);
        System.out.println(a == e);
        System.out.println(a == f);
    }

    private static String getDashixion() {
        return "wukong";
    }
}

true
false
false
```

final修饰的String编译器会将它作为常量使用，因此c的wukong2是常量池中的悟空2，也就是和a同一个地址的wukong2。
而d+2编译器会新生成一个对象，并将wukong2放入其中。
但是通过方法调用的wukong，无法将它当成常量，也就是说无法预测方法的返回值是什么，因此也是和e的效果一样会产生一个新的对象。
