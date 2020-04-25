---
title: AQS
p: 后端/Java/高并发与多线程/AQS
date: 2020-04-10 12:43:12
tags: [Java,多线程]
categories: [Java,多线程]
---
## 学习AQS的思路

- 学习AQS的目的主要是想理解原理、提高技术、以及应对面试
- 先从应用层面理解为什么需要他如何使用它，然后再看一看我们Java代码的设计者是如何使用它的了解它的应用场景
- 这样之后我们再去分析它的结构，这样的话我们就学习得更加轻松了

## 为什么需要AQS

- 锁和协作类有共同点：闸门
  - 我们已经学过了ReentrantLock和Semaphore，有没有发现他们有共同点？很相似?
  - 事实上，不仅是ReentrantLock和Semaphore，包括CountDownLatch、ReentrantReadWriteLock都有这样类似得协作（或叫同步）功能，起始他们底层都用了一个共同得基类，这就是AQS
- 因为上面得那些协作类，它们有很多工作都是类似得，所以如果能提取出一个工具类，那么就可以直接用，对于ReentrantLock和Semaphore而言就可以屏蔽很多细节，只关注它们自己得“业务逻辑”就可以了

## Semaphore和AQS得关系

- Semaphore内部有一个Sync类，Sync类继承了AQS
- CountDownLatch也是一样的

## 如果没有AQS

- 就需要每个协作工具自己实现：
  - 同步状态的原子性管理
  - 线程的阻塞与解除阻塞
  - 队列的管理

- 在并发场景下，自己正确且高效实现这些内容，是相当有难度的，所以我们用AQS来帮我们把这些脏活累活都搞定，我们只关注业务逻辑就够了

## AQS的作用

- AQS是一个用于构建锁、同步器、协作工具类的工具类（框架）。有了AQS以后，更多的协作工具类都可以很方便被写出阿里
- 一句话总结：有了AQS，构建线程协作类就容易多了

## AQS的重要性、地位

- AbstractQueuedSynchronizer是Doug Lea写的，从JDK1.5加入的一个基于FIFO等待队列实现的一个用于实现同步器的基础框架。

## AQS内部原理解析

- AQS最核心的就是三大部分:
  - state
  - 控制线程抢锁和配合的FIFO队列
  - 期望协作公爵类去实现的获取/释放等重要方法

### state状态

- 这里的state的具体含义，会根据具体是实现类的不同而不同，比如在Semaphore里，它表示“剩余的许可证数量”，而在CountDownLatch里，它表示“还需要倒数的数量”
- stae是volatile修饰的，会被并发地修改，所以所有修改state的方法都需要保证线程安全，比如getState、setState以及compareAndSetState操作来读取和更新这个状态。这些方法都依赖于j.u.catomic包的支持
- 在ReentrantLock中
- state用来表示“锁”的占有情况，包括可重入计数
- 当state的值为0的时候，标识改Lock不被任何线程所占有

### 控制线程抢锁和配合的FIFO队列

- 这个队列用来存放“等待的线程”，AQS就是排队管理器，当多个线程正用同意把锁时，必须有排队机制将那些没能拿到锁的线程串在一起。当锁释放时，锁管理器就会挑选一个合适的线程来占有这个刚刚释放的锁
- AQS会维护一个等待的线程队列，把线程都放到这个队列里
- 这是一个双向形式的队列
{% asset_img 队列.png 队列%}

### 期望协作工具类去实现的获取/释放等重要方法

- 这里的获取和释放方法，时利用AQS的协作工具类里最重要的方法，时由协作类自己去实现的，并且含义各不相同

#### 获取方法

- 获取操作会依赖state变量，经常会阻塞（比如获取不到锁的时候）
- 在Semaphore中，获取就是acquire方法，作用时获取一个许可证
- 而在CountDownLatch里面，获取就是await方法，作用时“等待，直到倒数结束”

#### 释放方法

- 释放操作不会阻塞
- 在Semaphore中，释放就是release方法，作用时释放一个许可证
- CountDownLatch里面，获取就是countDown方法，作用式“倒数1个数”

## AQS用法

1. 写一个类，想好协作的逻辑，实现获取/释放方法。
2. 内部写一个Sync类继承AbstractQueuedSynchronizer
3. 根据是否独占来重写tryAcquire/tryReleasehuo tryAcquireShared(int acquires)和tryReleaseShared（int releases）等方法，在之前写的获取/释放方法中调用AQS的acquire/release或者Shared方法

### CountDownLatch源码分析

1. 内部类Sync继承AQS
2. 调用CountDownLatch的await方法时，便会尝试获取共享锁，不过一开始时获取不到该锁的，于是线程被阻塞
3. 而共享锁可获取到的条件，就是锁计数器的值为0
4. 而锁计数器的初始值为count，每当一个线程调用该CountDownLatch对象的countDown()方法时，才将锁计数器-1
5. count个线程调用countDown()之后，锁计数器才为0，而前面提到的等待获取共享锁的线程才能继续运行。

### AQS在Semaphore的应用

- 在Semaphore中，state表示许可证的剩余数量
- 看tryAcquire方法，判断nonfairTryAcquireShared大于等于0的话，代表成功
- 这里会先检查剩余许可证数量够不够这次需要的，用减法来计算，如果直接不够，那就返回负数，表示失败，如果够了，就用自旋加compareAndSetState来改变state状态，直到改变成功就返回正数；或者时期间如果被其他人修改了导致剩余数量不够了，那也返回负数代表获取失败
- 分析释放锁的方法tryRelease
  - 由于时可重入的，所以state代表重入的次数，每次释放锁，先判断是不是当前持有锁的线程释放的，如果不是就抛异常如果是的话，重入次数就减一，如果减到了0，就说明完全释放了，于是free就是true，并且把state设置0

