---
title: CAS
p: 后端/Java/高并发与多线程/CAS
date: 2020-04-01 11:46:41
tags: [Java,多线程]
categories: [Java,多线程]
---
## 什么是CAS

- 并发
- 我认为V的值应该是A，如果是的话那我就把它改成B，如果不是A（说明被别人修改过了），那我就不修改了，避免多人同时修改导致出错
- CAS有三个操作数：内存值V、预期值A、要修改的值B，当且仅当预期值A和内存之V相同时，才将内存值修改为B，否则什么都不做。最后返回现在的V值
- CPU的特殊指令
- CAS的等价代码（语义）

```java
public class SimulatedCAS {
    private volatile int value;

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }
}
```

## 应用场景

- 乐观锁
- 并发容器
- 原子类

## 以AtomicInteger为例，分析在Java中是如何利用CAS实现原子操作的

- AtomicInteger加载Unsafe工具，用来直接操作内存数据
- 哟个Unsafe类实现底层操作
- 用volatile修饰value字段，保证可见性
- getAndAddInt方法分析

## Unsafe类

- Unsafe是CAS的核心类。Java无法直接访问底层操作系统，而是通过本地（native）方法来访问。不过尽管如此，JVM还是开了一个后门，JDK中有一个类Unsafe，它提供了**硬件级别的原子操作**
- valueOffset表示的是变量值在内存中的偏移地址，因为Unsafe就是根据内存偏移地址获取数据的原值的，这样我们救恩那个通过Unsafe来实现CAS了

### 分析在Java中是如何利用CAS实现原子操作的

- Unsafe类中的compareAndSwapInt方法
  - 方法中先想办法拿到变量value在内存中的地址。
  - 通过Atomic::cmpxchg实现原子性的比较和替换，其中参数x是即将更新的值，参数e是原内存的值。至此，最终完成了CAS的权过程。

## 缺点

- ABA问题
- 自旋时间过长
