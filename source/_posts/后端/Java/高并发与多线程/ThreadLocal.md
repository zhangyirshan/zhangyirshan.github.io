---
title: ThreadLocal
p: 后端/Java/高并发与多线程/ThreadLocal
date: 2020-03-16 18:00:47
tags: [Java,多线程]
categories: [Java,多线程]
---
## 两大使用场景————ThreadLocal的用途

- 典型场景1：每个线程需要一个独享的对象（通常时工具类，典型需要使用的类又SimpleDateFormat和Random）
- 典型场景2：每个线程内需要保存全局变量（例如再拦截器种获取用户信息），可以让不同方法直接使用，避免参数传递的麻烦

### 典型场景1——SimpleDateFormat的进化之路

1. 2个线程分别用自己的SimpleDateFormat，这没问题。
2. 后来延伸出10个，那就又10个线程和10个SimpleDateFormat，这随人写法不优雅（应该复用对象），但勉强可以接受。
3. 但是当需求变成了1000个，那么必然要用线程池（否则消耗内存太多）。
4. 所有的线程都公用同一个simpleDateFormat对象。
5. 这是线程不安全的，出现了并发安全问题。
6. 我们可以选择加锁，加锁后结果正常，但是效率低。
7. 在这里更好的解决方案是使用ThreadLocal，每个线程有独享的对象，在这里有10个对象对应10个线程。

## ThreadLocal的两个作用

1. 让某个需要用到的对象在线程间隔离（每个线程都有自己的独立的对象）
2. 在任何方法种都可以轻松获取到对象

根据共享对象的生成时机不同，选择initialValue或set来保存对象

### 场景一：initialValue

- 在ThreadLocal第一次get的时候把对象给初始化出来，对象的初始化时机可以由我们控制

### 场景二：set

- 如果需要保存到ThreadLocal里的对象的生产时机不由我们随意控制，例如拦截器生成的用户信息，用ThreadLocal.set直接放到我们的ThreadLocal中去，以便后续使用。

## 使用ThreadLocal带来的好处

1. 达到线程安全
2. 不需要加锁，提高执行效率
3. 更高效地利用内存、节省开销：相比于每个任务都新建一个SimpleDateFormat，显然用ThreadLocal可以节省内存和开销
4. 免去传参的繁琐：无论是场景一的工具类，还是场景二的用户名，都可以在任何地方直接通过ThreadLocal拿到，再也不需要每次都传同样的参数。ThreadLocal使得代码耦合度更低，更优雅。

## ThreadLocal原理

{% asset_img ThreadLocal.png ThreadLocal%}

## 主要方法解析

### T initialValue():初始化

1. 该方法会返回当前线程对应的初始值，这是一个延迟加载的方法，只有在调用get的时候，才会触发
2. 当线程第一次使用get方法访问变量时，将调用此方法，除非线程先前调用了set方法，在这种情况下，不会为线程调用本initialValue方法
3. 通常，每个线程最多调用一次此方法，但如果已经调用了remove()后，再调用get(),则可以在此调用此方法
4. 如果不重新本方法，这个方法会返回null。一半使用匿名内部类的方法来重写initialValue()方法，以便在后续使用中可以初始化副本对象

- 这正对应了ThreadLocal的两种典型用法
- initicalValue方法：是没有默认实现的，如果我们要用initialValue方法，需要自己实现，通常是匿名内部类的方式

### void set(T t) :为这个线程设置一个新值

### T get():得到这个线程对应的value。如果是首次调用get(),则会调用initialize来得到这个值

- get方法是先取出当前线程的ThreadLocalMap，然后调用map.getEntry方法，把本ThreadLocal的引用作为参数传入，取出map中属于本ThreadLocal的value
- 注意，这个map以及map中的key和value都是保存在线程中的，而不是保存在ThreadLocal中

### void remove():删除对应这个线程的值

## ThreadLocalMap类

- ThreadLocalMap类，也就是Thread.thrreadLocals
- ThreadLocalMap类是每个线程Thread类里面的变量，里面最重要的是一个键值对数组Entry[] table，可以认为是一个map，键值对：
    键：这个THreadLocal
    值：实际需要的成员变量，比如user或者simpleDateFormat对象

## ThreadLocal注意点

### 内存泄漏

- 什么是内存泄漏：某个对象不再有用，但是占用的内存却不能被回收。
- 弱引用的特点是，如果这个对象制备弱引用关联（没有任何强引用关联），那么这个对象就可以被回收。
- 所以弱引用不会阻止GC，因此这个弱引用的机制

### Value的泄漏

- ThreadLocalMap的每个Entry都是一个对key的弱引用，同时，每个Entry都包含了一个对value的强引用
- 正常情况下，当线程终止，保存在ThreadLocal里的value会被垃圾回收，因为没有任何强引用了。
- 但是，如果线程不重质（比如线程需要保持很久），那么key对应的value就不能被回收，因为有以下的调用链
    `Thread————>ThreadLocalMap————>Entry(key为null)————>Value`
- 因为value和Thread之间还存在这个强引用链路，所以导致value无法回收，就可能会出现OOM
- JDK已经考虑到了这个问题，所以在set，remove，rehash方法中会扫描key为null的Entry，并把对应的Value设置为null，这样value对象就可以被回收。
- 但是如果一个ThreadLocal不被使用，那么实际上set，remove，rehash方法也不会被调用，如果同时线程又不停止，那么调用链就一直存在，那么就导致了value的内存泄漏

### 如何避免内存泄漏（阿里规约）

- 调用remove方法，就会删除对应的Entry对象，可以避免内存泄漏，所以使用完ThreadLocal之后，应该调用remove方法

### THreadLocal注意点

- 空指针异常
  - 在进行get之前，必须先set，否则可能会报空指针异常？
  报错的原因是装箱拆箱导致的，不是set、get的问题
- 共享对象
  - 如果在每个线程中ThreadLocal.set()进去的东西本来就是多线程共享的同一个对象，比如static对象，那么多个线程ThreadLocal.get()取得的还是这个共享对象本身，还是有并发访问问题。
- 如果可以不适用ThreadLocal就解决问题，那么不要强行使用
  - 例如在任务数很少的时候，在局部变量中额可以新建对象就可以解决问题，那么就不需要使用到ThreadLcoal
- 优先使用框架的支持，而不是自己创造
  - 例如在Spring中，如果可以使用RequestContextHolder，那么就不需要自己维护ThreadLocal，因为自己可能会忘记戴奥用remove()方法等，造成内存泄露
