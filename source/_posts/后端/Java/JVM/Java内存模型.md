---
title: Java内存模型
p: 后端/Java/JVM/Java内存模型
date: 2020-03-07 19:24:54
tags: [Java,JVM]
categories: [Java,JVM]
---
## 底层原理

- 重要性：Java面试的必考知识点，只有学会了这个，才能说呢真正懂了并发。

从Java代码到CPU指令的变化过程
{% asset_img java编译.png java编译 %}
我们在Java代码中，使用的控制并发的手段例如synchronized关键字，最终也是要转化为CPU指令来生效的，我们来回顾一下从Java代码到最终执行的CPU指令的流程：

1. 最开始，我们编写的Java代码，是*.java文件
2. 在编译（javac命令）后，从刚才的*.java文件会变出一个新的Java字节码文件（*.class）
3. JVM会执行刚才生成的字节码文件（*.class），并把字节码文件转化为机器指令
4. 机器指令可以直接在CPU上运行，也就是最终的程序执行

而不同的JVM实现会带来不同的“翻译”，不同的CPU平台的机器指令又千差万别；所以我们在java代码层写的各种Lock，其实最后依赖的是JVM的具体实现（不同版本会有不同实现）和CPU的指令，才能帮我们达到线程安全的效果。
由于最终效果依赖处理器，不同处理器结果不一样，这样无法保证并发安全，所以需要一个标准，让多线程运行的结果可预期，这个标准就是JMM。

## 三兄弟：JVM内存结构 VS Java内存模型 VS Java对象模型

### 整体方向

- JVM内存结构,和Java虚拟机的**运行时区域**有关。
- Java内存模型，和Java的而**并发**编程有关。
- Java对象模型，和Java对象在**虚拟机中的表现形式**有关。

### JVM内存结构

{% asset_img JVM内存结构.png JVM内存结构 %}

- 堆（heap）
- 虚拟机栈（VM stack）
- 方法区（method）
- 本地方法栈
- 程序计数器

### Java对象模型

{% asset_img Java对象模型.png Java对象模型 %}

- Java对象自身的存储模型
- JVM会给这个类创建一个instanceKlass，保存在方法区，用来在JVM层表示该Java类。
- 当我们在Java代码中，使用new创建一个对象的时候，JVM会创建一个instanceOopDesc对象，这个对象中包含了**对象头**以及**实例数据**。

## Java内存模型（JMM）

### 什么是JMM

JMM（Java Memory Model）是规范

为什么需要JMM？

- **C**语言不存在内存模型的概念
- 依赖处理器，不同处理器结果不一样
- **无法保证**并发安全
- 需要一个标准，让多线程运行的结果可预期

- JMM是一组规范，需要各个JVM的实现来遵循JMM规范，以便于开发者可以利用这些规范，更方便地开发多线程程序。
- 如果没有这样的一个JMM内u才能模型来规范，那么很可能经过了不同JVM的不同规准的重排序之后，呆滞不同的虚拟机上运行的结果不一样，那是很大的问题。

### JMM是工具类和关键字的原理

- volatile、synchronized、Lock等的原理都是JMM
- 如果没有JMM，那就需要我们自己指定什么时候用内存栅栏等，那是相仿麻烦的，幸好有了JMM，让我们只需要用同步工具和关键字就可以开发并发程序。

## 重排序

- 什么是重排序
    在线程1内部的两行代码的实际执行顺序和代码在Java文件种的顺序不一致，代码指令并不是严格按照代码语句顺序执行的，它们的顺序被改变了，这就是重排序，这里被颠倒的是y=a和b=1这两行语句。
- 重排序的好处：提高处理速度
    {% asset_img 重排序指令.png 重排序指令 %}
    编译器优化：包括JVM，JIT编译器等
    CPU指令重排：就算编译器不发生重排，CPU也可能堆指令进行重排
    内存的“重排序”：线程A的修改线程B却看不到，引出可见性问题
- 重排序的3种情况：编译器优化、CPU指令重排、内存的“重排序”
  - 编译器优化
    编译器（包括JVM，JIT编译器等）出于优化的目的（例如当前有了数据a，那么如果把对a的操作放到一起效率会更高，避免了读取b后又返回来重新读取a的时间开销），在编译的过程中会进行一定程度的重排，导致生成的机器指令和之前的字节码的顺序不一致。
    在刚才的例子中，编译器将y=a和b=1这两行语句换了顺序（也可能是线程2的两行换了顺序，同理），因为它们之间没有数据依赖关系，那就不难得到 x =0，y = 0 这种结果了。
  - 指令重排序
    CPU 的优化行为，和编译器优化很类似，是通过乱序执行的技术，来提高执行效率。所以就算编译器不发生重排，CPU 也可能对指令进行重排，所以我们开发中，一定要考虑到重排序带来的后果。
  - 内存的“重排序”
    在刚才的例子中，假设没编译器重排和指令重排，但是如果发生了内存缓存不一致，也可能导致同样的情况：线程1 修改了 a 的值，但是修改后并没有写回主存，所以线程2是看不到刚才线程1对a的修改的，所以线程2看到a还是等于0。同理，线程2对b的赋值操作也可能由于没及时写回主存，导致线程1看不到刚才线程2的修改。

## 可见性

### 为什么会有可见性问题

{% asset_img 可见性问题.png 可见性问题 %}

- CPU有**多级缓存**，导致读的数据过期
  - 高速缓存的**容量**比主内存小，但是**速度**劲卒於寄存器，所以在CPU和主内存之间就多了Cache层
  - 线程间的对于共享变量的可见性问题不是直接由多核引起的，而是由多缓存引起的。
  - 如果所有个核心都只用一个缓存，那么也就不存在内存可见性问题了。
  - 每个核心都会将自己需要的数据读到独占缓存中，数据修改后也是写入到缓存中，然后等待刷入到主存中。所以会导致有些核心读取的值是一个过期的值。

### JMM的抽象：主内存和本地内存

- Java作为高级语言，屏蔽了这些底层细节，用JMM定义了一套读写内存数据的规范，虽然我们不再需要关心一级缓存和二级缓存的问题，但是，JMM抽象了主内存和本地内存的概念。
- 这里说的本地内存并不是真的是一块给每个线程分配的内存，而是JMM的一个抽象，是对于寄存器、一级缓存、二级缓存等的抽象。

#### 什么是主内存和本地内存

- Java作为高级语言，屏蔽了这些底层细节，用JMM定义了一套读写内存数据的规范，虽然我们不再需要关心一级缓存和二级缓存的问题，但是，JMM抽象了主内存和本地内存的概念。
- 这里说的本地内存并不是真的是一块给每个线程分配的内存，而是JMM的一个抽象，是对于寄存器、一级缓存、二级缓存等的抽象。

{% asset_img 主内存和本地内存.png 主内存和本地内存 %}
{% asset_img 主内存.png 主内存和本地内存 %}

- JMM有以下规定：
  1. 所有的变量都存储在主内存中，同时每个线程也有自己独立的工作内存，工作内存中的变量内容是主内存中的拷贝。
  2. 线程布恩那直接读写主内存中的变量，而是只能操作自己工作内存中的变量，然后再同步到主内存中。
  3. 主内存是多个线程共享的，但线程间不共享工作内存，如果线程间需要通信，必须借助主内存中转来完成。
所有的共享变量存在于主内存中，每个线程有自己的本地内存，而且线程读写共享数据也是通过本地内存交换的，所以才导致了可见性问题。

### Happens-Before原则

- happens-before规则是用来解决可见性问题的：在时间上，动作A发生在动作B之前，B保证能看见A，这就是happens-before。
- 两个操作可以用happens-before来确定它们的执行顺序：如果一个操作happens-before于另一个操作，那么我们说第一个操作对于第二个操作时可见的。

#### 什么不是happens-begore

- 两个线程没有相互配合的机制，所以代码X和Y的执行结果并不能保证总被对方看到的，这就不具备happens-before。
- 两个操作可以用happens-before来确定它们的执行顺序：如果一个操作happens-before于另一个操作，那么我们说第一个操作对于第二个操作时可见的。

#### Happens-Before规则有哪些

1. 单线程规则
  {% asset_img 单线程规则.png 单线程规则 %}
2. 锁操作（synchronized和Lock）
  {% asset_img 锁操作.png 锁操作 %}
  {% asset_img 锁操作2.png 锁操作 %}
3. volatile变量
  {% asset_img volatile变量.png volatile变量 %}
4. 线程启动
  {% asset_img 线程启动.png 线程启动 %}
5. 线程join
  {% asset_img 线程join.png 线程join %}
6. 传递性：如果hb(A,B)而且hb(B,C),那么可以推出hb(A,C)
7. 中断：一个线程被其他线程interrupt时，那么检查中断（isInterrupted）或者抛出InterruptedException一定能看到。
8. 构造方法：对象构造方法的最后一行指令happens-before于finalize()方法的第一行指令
9. 工具类的Happens-Before原则
    1. 线程安全的容器get一定能看到在此之前的put等存入操作
    2. CountDownLatch
    3. Senaohore
    4. Future
    5. 线程池
    6. CyclicBarrier

#### happens-before演示

```java
public class FieldVisibility {
    int a = 1;
    volatile int b = 2;

    private void change() {
        a = 3;
        b = a;
    }

    private void print() {
        System.out.println("b = " + b + "; a = " + a);
    }

    public static void main(String[] args) {
        while (true) {
            FieldVisibility test = new FieldVisibility();

            new Thread(()->{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.change();
            }).start();

            new Thread(()->{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.print();
            }).start();
        }
    }
}
```

- happens-before有一个原则是：如果A是对volatile变量的写操作，B是对同一个百年来的操作，那么hb(A,B)
- 分析这四种情况：
  a=3,b=2
  a=1,b=2
  a=3,b=3
- 第四种情况（概览低）：没给b加volatile，那么有可能出现a=1，b=3.因为a虽然被修改了，但是其他线程不可见，而b恰好其他线程可见，这就造成了b=3，a=1.
- 近朱者赤：给x加了volatile，不仅x被影响，也可以实现轻量级同步
- b之前的写入（对应代码b = a）对读取b后的代码（print b）都可见，所以子啊writerThread里对a的赋值，一定会对readerThread里的读取可见，所以这里的a即使不加volatile，只要b读到是3，就可以由happens-before原则保证了读取到的都是3而不可能读取到1.

### volatile关键字

#### volatile是什么

- volatile是一种同步机制，比synchronized或者Lock相关类更轻量，因为适用volatile并不会发生上下文切换等开销很大的行为。
- 如果一个变量被修饰成volatile，那么JVM就知道了这个变量可能会被并发修改。
- 但是开销小，相应的能力也小，虽然说volatile是用来同步的保证线程安全的，但是volatile做不到synchronized那样的原子保护，volatile尽在很有限的场景下才能发挥作用。

#### volatile的适用场合

- 不适用：a++
- 适用场合1：boolean flag，如果一个共享变量自始至终只被各个线程赋值，而没有其他的操作，那么就可以用volatile来 代替synchronized或者代替原子变量，因为赋值自身是有原子性的，而volatile又保证了可见性，所以就足以保证线程安全。
- 适用场合2：作为刷新之前变量的触发器。

```java
public class FieldVisibility {
    int abc = 1;
    int abcd = 1;
    int a = 1;
    volatile int b = 2;

    private void change() {
        abc = 30;
        abcd = 42;
        a = 3;
        b = a;
    }

    private void print() {
        System.out.println("b = " + b + "; a = " + a + "; abc = " + abc + "; abcd = " + abcd);
    }

    public static void main(String[] args) {
        while (true) {
            FieldVisibility test = new FieldVisibility();

            new Thread(()->{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.change();
            }).start();

            new Thread(()->{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                test.print();
            }).start();
        }
    }
}
```

#### volatile的作用：可见性、禁止重排序

1. 可见性：读一个volatile变量之前，需要先使相应的本地缓存失效，这样就必须到主内存读取最新值，写一个volatile属性会立即刷入到主内存。
2. 禁止指令重排序优化：解决单例双重锁乱序问题。

#### volatile和synchronized的关系

- volatile在这方面可以看做是轻量版的synchronized：如果一个共享变量自始至终制备各个线程赋值，而没有其他的操作，那么就可以用volatile来代替synchronized或者代替原子变量，因为赋值自身是由原子性的，而volatile由保证了可见性，所以就足以保证线程安全。

#### 学以致用用volatile修正重排序问题

- OutOfOrderExecution类加了volatile后，用于不会出现（0，0）的情况了

```java
package MultithReading.threadcoreknowledge.jmm;

import java.util.concurrent.CountDownLatch;

/**
 * @Description TODO
 * 演示重排序的现象
 * “直到某个条件才停止”，测试小概率事件
 * 这4行代码的执行顺序决定了最终x和y的结果，一共有3种情况：
 * 1. a=1;x=b(0);b=1;y=a(1),最终的结果是x=0，y=1
 * 2. b=1;y=a(0);a=1;x=b(1),最终的结果是x=1,y=0
 * 3. b=1;a=1;x=b(1);y=a(1),最终的及格过是x=1，y=1
 * 会出现x=0，y=0？那是因为重排序发生了，4行代码的执行顺序的其中一种可能：
 * y=a；
 * a=1；
 * x=b；
 * b=1；
 * 第701446次（0,0)
 * @Author Matthew
 * @Date 2020/3/8 10:41
 * @Version 1.0
 */

public class OutOfOrderExecution {

    private volatile static int x = 0, y = 0;
    private volatile static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;

            CountDownLatch latch = new CountDownLatch(1);

            Thread one = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1;
                x = b;
            });

            Thread two = new Thread(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b = 1;
                y = a;
            });
            one.start();
            two.start();
            latch.countDown();
            one.join();
            two.join();

            String result = "第" + i + "次（" + x + "," + y + ")";
            System.out.println(result);
            if (x == 0 && y == 0) {
                break;
            }
        }
    }
}
```

#### volatile小结

1. voaltile修饰符适用于以下场景：某个属性被多个线程共享，其中有一个线程修改了此属性，其他线程可以立即得到修改后的值，比如boolean flag；或者作为触发器，实现轻量级同步。

2. volatile属性的读写操作都是无锁的，它不饿能替代synchronized，因为他没有提供原子性和互斥性。因为无锁不需要花费时间在获取锁和释放锁上，所以说它是低成本的。

3. volatile只能作用于属性，我们用volatile修饰属性，这样compilers就不会对这个属性做指令重排序。

4. volatile提供了可见性，任何一个线程对其的修改将立马对其他线程可见。volatile属性不会被线程缓存，始终从主存中读取。

5. volatile提供了happends-before保证，对volatile变量的v的写入happends-before所有其他线程后续对v的读操作。

6. volatile可以使得long和double的赋值时原子的。

### 能保证可见性的措施

- 除了volatile可以让变量保证可见性外，synchronized、Lock、并发集合、Thread.join()和Thread.start()等都可以保证的的可见性

### 升华：对synchronized可见性的正确理解

- synchronized不仅保证了原子性，还保证了可见性
- synchronized不仅让保护的代码安全，还近朱者赤（保证synchronized之前的代码都被看到）

## 原子性

### 什么是原子性

- 一系列的操作，要么全部执行成功，要么全部不执行，不会出现执行一半的情况，是不可分割的。
- ATM里取钱
- i++不是原子性的

### Java中的原子操作有哪些

- 除了long和double之外的基本类型（int,byte,boolean,shot,char,float)的赋值操作
- 所有引用reference的赋值操作，不管是32位的机器还是64位的机器
- java.concurrent.Atomic.*包中所有类的原子操作

### long和double的原子性

- 问题描述：官方文档、对于64位的值的写入，可以分为两个32位的操作进行写入、读取错误、适用volatile解决
- 结论:在32位上的JVM上，long和double的操作不是原子的，但是在64位的JVM上是原子的。
- 实际开发中：商用Java虚拟机中不会出现

### 原子操作 + 原子操作 ！= 原子操作

- 简单地把原子操作组合在一起，并不能保证整体依旧具有原子性
- 比如我去ATM机两次取钱时两次独立的原子操作，但是期间有可能银行卡被借给吗v朋友，也就是被其他线程打断并被修改。
- 全同步的HashMap也不完全安全

## 常见面试问题

### JMM应用实例：单例模式8中写法、单例和并发的关系（真实面试超高频考点）

- 单例模式的作用
  1. 为什么需要单例？
    节省内存和计算
    保证结果正确
  
- 单例模式适用场景
  1. 无状态的工具类：比如日志工具类，不管是在哪里适用，我们需要的知识它帮我们记录日志信息，除此之外，并不需要在它的实例对象上存储任何状态，这时候我们就只需要一个实例对象即可。
  2. 全局信息类：比如沃恩在一个类上记录网站的访问次数，我们不希望有的访问被记录在对象A上，有的却记录在对象B上，这时候我们就让这个类成为单例。