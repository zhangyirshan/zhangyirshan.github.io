---
title: volatile
p: 后端/Java/Java8新特性/JUC/volatile
date: 2019-12-17 09:30:31
tags: Java
categories: [Java,JUC线程]
---
## Java JUC 简介

在 Java 5.0 提供了 java.util.concurrent （简称JUC ）包，在此包中增加了在并发编程中很常用的实用工具类，用于定义类似于线程的自定义子系统，包括线程池、异步 IO 和轻量级任务框架。提供可调的、灵活的线程池。还提供了设计用于多线程上下文中的 Collection 实现等。

## 内存可见性

1. 内存可见性（Memory Visibility）是指当某个线程正在使用对象状态而另一个线程在同时修改该状态，需要确保当一个线程修改了对象状态后，其他线程能够看到发生的状态变化。
2. 可见性错误是指当读操作与写操作在不同的线程中执行时，我们无法确保执行读操作的线程能适时地看到其他线程写入的值，有时甚至是根本不可能的事情。
3. 我们可以通过同步来保证对象被安全地发布。除此之外我们也可以使用一种更加轻量级的 volatile 变量。

## volatile 关键字

1. Java 提供了一种稍弱的同步机制，即 volatile 变量，用来确保将变量的更新操作通知到其他线程。可以将 volatile 看做一个轻量级的锁，但是又与锁有些不同：

- 对于多线程，不是一种互斥关系
- 不能保证变量状态的“原子性操作”

```java
public class TestVolatile {
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();
        while (true) {
            if (threadDemo.isFlag()) {
                System.out.println("------------");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable{

    private boolean flag = false;


    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag(){
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
结果是
flag=true

并且循环不结束
```

> 原因：初始化时flag=false存在主存中，线程1，main线程从主存中分别拷贝后存在自己的线程的私有部分，当线程一将flag的值改变为true并协会主存后，并没有改写main线程中的值，因为main线程的while循环执行太快没有时间再次从主存中取值，所以main线程的值一直是false并且死循环。这个是内存可见性问题，当多个线程操作共享数据时，彼此不可见。

如果加个对象锁，可以解决这个问题,但是加上对象锁后，多个线程会使程序运行效率变低。

```java
public static void main(String[] args) {
    ThreadDemo threadDemo = new ThreadDemo();
    new Thread(threadDemo).start();
    while (true) {
        synchronized (threadDemo) {
            if (threadDemo.isFlag()) {
                System.out.println("------------");
                break;
            }
        }
    }
}

结果是
------------
flag=true

```

volatile关键字也可以解决这个问题，他是通过直接操作主存中的数据来实现实时刷新共享数据的
`private volatile boolean flag = false;`
当多个线程进行操作共享数据时，可以保证内存中的数据可见。相较于synchronized是一种较为轻量级的同步策略。

### 注意

1. colatile不具备“互斥性”
2. volatile不能保证变量的“原子性”

## 原子性

### i++的原子性问题

实际上分为三个步骤“读-改-写”

```java
int i = 10;
i = i++;//10

//在底层
int temp = i;
i = i+1;
i = temp;
```

```java
package Java_JUC;

public class TestVolatile {
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        new Thread(threadDemo).start();
        while (true) {
//            synchronized (threadDemo) {
            if (threadDemo.isFlag()) {
                System.out.println("------------");
                break;
            }
//            }
        }
    }
}

class ThreadDemo implements Runnable{

    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag(){
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
```

### 原子变量

- 类的小工具包，支持在单个变量上解除锁的线程安全编程。事实上，此包中的类可将 volatile 值、字段和数组元素的概念扩展到那些也提供原子条件更新操作的类。
- 类 AtomicBoolean、AtomicInteger、AtomicLong 和 AtomicReference 的实例各自提供对相应类型单个变量的访问和更新。每个类也为该类型提供适当的实用工具方法。
- AtomicIntegerArray、AtomicLongArray 和 AtomicReferenceArray 类进一步扩展了原子操作，对这些类型的数组提供了支持。这些类在为其数组元素提供 volatile 访问语义方面也引人注目，这对于普通数组来说是不受支持的。
- 核心方法：boolean compareAndSet(expectedValue, updateValue)
- java.util.concurrent.atomic 包下提供了一些原子操作的常用类:
  - AtomicBoolean 、AtomicInteger 、AtomicLong 、 AtomicReference
  - AtomicIntegerArray 、AtomicLongArray
  - AtomicMarkableReference
  - AtomicReferenceArray
  - AtomicStampedReferen

jdk1.5后java.util.concurrent.atomic包下提供了常用的原子变量：
    1. volatile保存内存可见性
    2. CAS（compare-And-Swap）算法保证数据的原子性
        CAS算法是硬件对于并发操作共享数据的支持
        CAS包含了三个操作数：
        内存值V
        预估值A
        更新值B
        当且仅当V == A时，V = B，否则，将不做任何操作

```java
package Java_JUC;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicDemo {

    public static void main(String[] args) {
        AtomicDemo atomicDemo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(atomicDemo).start();
        }
    }
}

class AtomicDemo implements Runnable {

//    private volatile int serialNumber = 0;

    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getSerialNumber());
    }

    public int getSerialNumber(){
//        return serialNumber++;
        return serialNumber.getAndIncrement();
    }
}
```

## CAS算法

CAS (Compare-And-Swap) 是一种硬件对并发的支持，针对多处理器操作而设计的处理器中的一种特殊指令，用于管理对共享数据的并发访问。

- CAS 是一种无锁的非阻塞算法的实现。
- CAS 包含了 3 个操作数：
- 需要读写的内存值 V
- 进行比较的值 A
- 拟写入的新值 B
- 当且仅当 V 的值等于 A 时，CAS 通过原子方式用新值 B 来更新 V 的值，否则不会执行任何操作。

## ConcurrentHashMap 锁分段机制

- Java 5.0 在 java.util.concurrent 包中提供了多种并发容器类来改进同步容器的性能。
- ConcurrentHashMap 同步容器类是Java 5 增加的一个线程安全的哈希表。对与多线程的操作，介于 HashMap 与 Hashtable 之间。内部采用“锁分段”机制替代 Hashtable 的独占锁。进而提高性能。
- 此包还提供了设计用于多线程上下文中的 Collection 实现：ConcurrentHashMap、ConcurrentSkipListMap、ConcurrentSkipListSet、CopyOnWriteArrayList 和 CopyOnWriteArraySet。当期望许多线程访问一个给定 collection 时，ConcurrentHashMap 通常优于同步的 HashMap，ConcurrentSkipListMap 通常优于同步的 TreeMap。当期望的读数和遍历远远大于列表的更新数时，CopyOnWriteArrayList 优于同步的 ArrayList。
