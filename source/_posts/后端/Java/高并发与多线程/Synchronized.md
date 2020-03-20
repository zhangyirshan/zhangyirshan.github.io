---
title: Synchronized
p: 后端/Java/高并发与多线程/Synchronized
date: 2019-12-11 15:53:09
tags: [Java,多线程]
categories: [Java,多线程]
---
## synchronized的作用

> 同步方法支持一种简单的策略来防止线程干扰和内存一致性错误：如果一个对象对多个线程可见，则对该对象变量的所有读取或写入都是通过同步方法完成的。

能够保证在`同一时刻`最多只有`一个`线程执行该段代码，以达到保证并发安全的效果。

## Synchronized的地位

+ Synchronized是Java的`关键字`，被Java语言原生支持
+ 是`最基本`的额互斥同步手段
+ 是并发编程中的`元老级`角色，是并发编程的`必学`内容

## 不使用并发手段会有什么后果

代码实战：两个线程同时a++，最后结果会比预计的少

### 代码

```java
public class DisppearRequest1 implements Runnable{

    static DisppearRequest1 instance = new DisppearRequest1();

    static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            i++;
        }
    }
}
```

### 原因

count++，它看上去只是一个操作，实际上包含了三个动作：

1. 读取count
2. 将count加一
3. 讲count的值写入到内存中

## Synchronized的两个用法

**对象锁**
包括`方发锁`**（默认锁对象为this当前实例对象）**和`同步代码块锁`**（自己指定锁对象）**

**类锁**
指synchronized修饰`静态`的方法或指定锁为`Class对象`。

### 第一个用法：对象锁

代码块形式：手动指定锁对象
方发锁形式：synchronized修饰普通方法，锁对象默认为this

```java
// 代码块形式
public class SynchronizedObjectCodeBlock2 implements Runnable {

    static SynchronizedObjectCodeBlock2 instance = new SynchronizedObjectCodeBlock2();

    Object lock1 = new Object();
    Object lock2 = new Object();

    @Override
    public void run() {
        synchronized (lock1) {
            System.out.println("我叫lock1代码块形式。我叫" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "lock1运行结束。");
        }

        synchronized (lock2) {
            System.out.println("我收lock2代码块形式。我叫" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "lock2运行结束。");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println("finished");
    }
}
```

```java
//方发锁形式
public class SynchronizedObjectMethod3 implements Runnable{

    static SynchronizedObjectMethod3 instance = new SynchronizedObjectMethod3();

    Object lock1 = new Object();
    Object lock2 = new Object();

    @Override
    public void run() {
        method();
    }

    public synchronized void method() {
        System.out.println("我的对象锁 的方法修饰符形式 ，我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束。");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println("finished");
    }
}
```

### 第二个用法：类锁

**只有一个Class对象**：Java类可能会有很多个对象，但是只有一个Class对象。
**本质**：所以所谓的类锁，不过是Class对象的锁而已。
**用法和效果**：类锁只能在同一时刻被一个对象拥有。

> 两种形式
    1. 形式1：synchronized加static方法上
    2. 形式2：synchronized（*.class）代码块

```java
// 形式1：synchronized加static方法上
public class SynchronizedClassStatic4 implements Runnable{
    static SynchronizedClassStatic4 instance1 = new SynchronizedClassStatic4();
    static SynchronizedClassStatic4 instance2 = new SynchronizedClassStatic4();

    @Override
    public void run() {
        method();
    }

    public static synchronized void method(){
        System.out.println("我的类锁 的第一种形式 ，我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束。");
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println("finished");
    }
}

// 形式2：synchronized（*.class）代码块
public class SynchronizedClassClass5 implements Runnable {
    static SynchronizedClassClass5 instance1 = new SynchronizedClassClass5();
    static SynchronizedClassClass5 instance2 = new SynchronizedClassClass5();
    @Override
    public void run() {
        method();
    }
    public void method(){
        synchronized (SynchronizedClassClass5.class) {
            System.out.println("我是类锁的第二种形式：synchronized（*.class）。我叫" + Thread.currentThread());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "运行结束");
        }
    }
    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {

        }
        System.out.println("finished");
    }
}

```

## 多线程访问同步方法的7中情况（面试常考）

1. 两个线程同时访问一个对象的同步方法                    =>  串行
2. 两个线程访问的使两个对象的同步方法                    =>  并行
3. 两个线程访问的是synchronized的静态方法               =>  串行
4. 同时访问同步方法与非同步方法                         =>  并行
5. 访问同一个对象的不同的普通同步方法                   =>  串行
6. 同时访问静态synchronized和非静态synchronized方法     =>  并行
7. 方法抛异常后，会**释放锁**(lock不会释放)

### 核心思想

1. 一把锁只能同时被一个线程获取，没有拿到锁的线程必须等待（1，5）
2. 每个实例都对应有自己的一把锁，不同势力之间互不影响；例外：锁对象是*.class以及synchronized修饰的是static方法的时候，所有对象公用同一把类锁（2，3，4，6）
3. 无论是方法正常执行完毕或者方法抛出异常，都会释放锁（7）

## 性质

### 可重入

> 什么是可重入：指的是同一线程的外层函数获得锁之后，内层函数可以直接再次获取该锁

+ 好处：避免死锁、提升封装性
+ 粒度：线程而非调用（用3种情况来说明和pthread的区别）
  + 情况1：证明同一个方法是可重入的
  + 情况2：证明可重入不要求是同一个方法
  + 情况3：证明可重入不要求是同一个类种的

### 不可中断

一旦这个锁已经被别人获得了，如果我还想获得，我只能选择等待或者阻塞，知道别的线程**释放**这个锁。如果别人永远不释放锁，那么我只能永远地等下去。

相比之下，未来会介绍的Lock类，可以拥有中断的能力，第一点，如果我觉得我等的时间太长了，有权中断现在已经获取到锁的线程的执行；第二点，如果我觉得我等待的时间太长了不想再等了，也可以退出。

## 原理

1. 加锁和释放锁的原理：现象、实际、深入JVM看字节码
2. 可重入原理：加锁次数计数器
3. 保证可见性的原理：内存模型

加锁和释放锁的原理
现象
获取和释放锁的时机：内置锁

Java内置反编译指令  javap （-verbose） Decompilation14.class

### 可重入原理

> 加锁次数计数器

1. JVM负责跟踪对象被加锁的次数
2. 线程第一次给对象加锁的时候，技术变为1.每当这个相同的线程在此对象上再次获得锁时，计数会递增。
3. 每当任务离开时，计数递减，当计数为0的时候，锁被完全释放。

### 可见性原理：Java内存模型

{% asset_img 内存模型.png 内存模型%}

## 缺陷

1. 效率低：锁的释放情况少，试图获得锁时不能设定超时、不能中断一个正在试图获得锁的线程。
2. 不够灵活（读写锁更灵活）：加锁和释放的时机单一，每个锁仅有单一的条件（某个对象），可能时不够的。
3. 无法知道是否成功的获取到锁。

## 面试问题

1. 使用注意点：锁对象不能为空、作用域不宜过大、避免死锁
2. 如何选择Lock和synchronized关键字？
    1. 如果可以尽量用java.util.concurrent包中的类，不需要自己作同步工作。
    2. 如果synchronized关键字适用，那么就优先适用synchronized关键字，这样可以减少我们编写的代码。
    3. 如果需要使用Lock或condition特性的时候，那么才使用他们。
3. 多线程访问同步方法的各种具体情况。
