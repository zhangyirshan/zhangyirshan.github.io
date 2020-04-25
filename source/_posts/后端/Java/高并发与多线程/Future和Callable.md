---
title: Future和Callable
p: 后端/Java/高并发与多线程/Future和Callable
date: 2020-04-15 12:15:09
tags: [Java,多线程]
categories: [Java,多线程]
---
## Runnable的缺陷

- 不能返回一个返回值
- 不能抛出checked Exception

## Callable接口

类似于Runnable，被其他线程执行的任务

- 实现call方法
- 有返回值

## Future

### 主要方法：一共5个

#### get()方法：获取结果

get方法的行为取决于Callable任务的状态，只有以下这5种情况：

1. 任务正常完成：get方法回立刻返回结果
2. 任务尚未完成（任务还没开始或进行中）：get将阻塞并直到任务完成
3. 任务执行过程中抛出Exception：get方法回抛出ExecutionException：这里的抛出异常，是call()执行时产生的那个异常，看到这个异常类型时java.util.concurrent.ExecutionException。不论call()执行时抛出的异常类型时什么，最后get方法抛出的异常类型都是ExecutionException。
4. 任务呗取消：get方法会抛出CancellationException
5. 任务超时：get方法有一个重载方法，时传入一共延迟时间的，如果时间到了还没有获得结果，get方法就会抛出TimeoutException。

##### get(long timeout,TimeUnit unit):有超时的获取

- 超时的需求很常见
- 用get(long timeout,TimeUnit unit)方法时，如果call()在规定时间内完成了任务，那么就会正常获取到返回值；而如果再指定时间内没有计算除结果，那么就会抛出TimeoutException
- 超时不获取，任务需取消

#### cancel方法

- 取消任务的执行

1. 如果这个任务开没有开始执行，那么这种情况最简单，任务会被正常的取消，未来也不会被执行，方法返回true
2. 如果任务已完成，或者已取消，那么cancel()方法会执行失败，返回false。
3. 如果这个任务已经开始执行了，那么这个取消方法将不会执行取消该任务，而是会根据我们填的参数mayInterruptIfRunning做判断

Future.cancel(true)适用于：
1.任务能够处理interrupt

Future.cancel(false)仅用于避免启动尚未启动的任务，适用于:

1.未能处理interrupt的任务
2.不清楚任务是否支持取消
3.需要等待已经开始的任务执行完成

#### isDone方法

- isDone()方法：判断线程是否执行完毕

#### isCancelled()方法

- isCancelled方法：判断是否被取消

### 用法2：用FutureTask来创建Future

- 用FutureTask来获取Future和任务的结果
- FutureTask是一种包装器，可以把Callable转化成Future和Runnable，它同时实现二者的接口
- 所以它既可以作为Runnable被线程执行，而可以作为Future得到Callable的返回值
- 把Callable实例当作参数，生成FutureTask的对象，然后把这个对象当作一共Runnable对象，用线程池或另起线程去执行这个Runnable对象，最后通过FututreTask获取刚才执行的结果

### Future的注意点

- 当for循环批量获取future的结果时，容易发生一部分线程很慢的情况，get方法调用时应使用timeout限制
- Future的生命周期不能后退
  - 声明周期只能前进，不能后退。就和线程池的生命周期一样，一旦完全完成了任务，他就永久停在了已完成的状态，不能重头再来。

## Callable和Future的关系

- 我们可以用Future.get来获取Callable接口返回的执行结果，还可以通过Future.isDone()来判断任务是否已经执行完了，以及取消这个任务，显示获取任务的结果等
- 在call()未执行完毕之前，调用get()的线程（假定此时是主线程)会被阻塞，直到call()方法返回了结果后，此时future.get()才会得到该结果，然后主线程才会切换到runnable状态
- 所以Future是一个存储器，它存储了call()这个任务的结果，而这个任务的执行时间是无法提前确定的，因为这完全取决与call()方法执行的情况
