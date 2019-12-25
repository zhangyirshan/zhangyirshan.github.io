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
