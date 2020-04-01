---
title: Lock接口
p: 后端/Java/高并发与多线程/Lock接口
date: 2020-03-20 17:41:42
tags: [Java,多线程]
categories: [Java,多线程]
---
## Lock简介、地位、作用

- 锁是一种工具，用于控制对共享资源的访问。
- Lock和synchronized，这两个是最常见的锁，它们都可以达到线程安全的目的，但是在使用上和功能上又有较大的不同。
- Lock并不是用来代替synchronized的，而是当使用synchronized不合适或不足以满足要求的时候，来提供高级功能的。
- Lock接口最常见的实现类是ReentrantLock
- 通常情况下，Lock只允许一个线程来访问这个共享资源。不过有的时候，一些特殊的实现也可允许并发访问，比如ReadWriteLock里面的ReadLock。

### 为什么需要Lock

- 为什么synchronized不够用

1. 效率低：锁的释放情况少、试图获得锁时不能设定超时、不饿能中断一个正在试图获得锁的线程。
2. 不够灵活（读写锁更灵活）：加锁和释放的实际单一，每个锁仅有单一的条件（某个对象），可能时不够的。
3. 无法知道是否成功获得到锁

## Lock主要方法介绍

- 在Lock中声明了四个方法来获取锁
- lock()、tryLock()、tryLock(long time,TimeUnit unit)和lockInterryptibly

### lock()

- lock()就是最普通的获取锁。如果锁已被其他线程获取，则进行等待。
- Lock不会像synchronized一样在异常时自动释放锁
- 因此最佳实践是，在finally中释放锁，以保证发生异常时锁一定被释放
- lock()方法不能被中断，这回带来很大的隐患：一旦陷入死锁，lock()就会陷入永久等待

### tryLock()

- tryLock()用来尝试获取锁，如果当前锁没有被其他线程占用，则获取成功，则返回true，否则返回false，代表获取锁失败
- 相比于lock，这样的方法显然功能更加强大了，我们可以根据是否能获取到锁来决定后续程序的行为。
- 该方法会立即返回，即便在拿不到锁时不会一直在那等

#### 主要方法介绍

- tryLock(long time,TimeUnit unit):超时就放弃
- lockInterruptibly():相当于tryLock(long time,TimeUnit unit)把超时时间设置为无线。在等待锁的过程中，线程可以被中断
- unlock():解锁

## 可见性保证

- 可见性
- happens-before
- Lock的加解锁和synchronized有同样的内存语义，也就是说，下一个线程加锁后可以看到所以前一个线程解锁前发生的所有操作。
