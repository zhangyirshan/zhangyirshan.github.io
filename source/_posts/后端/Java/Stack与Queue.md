---
title: Stack与Queue
p: 后端/Java/Stack与Queue
date: 2019-11-25 13:51:14
tags: Java
categories: [Java,Java SE]
---
## Stack(栈)

栈(Stack)也是一种特殊的线性表，是一种后进先出(LIFO)的结构。

1. 栈是限定仅在表尾进行插入和删除运算的线性表，表尾称为栈顶(top)，表头称为栈底(bottom)。
2. 栈的物理存储可以用顺序存储结构，也可以用链式存储结构。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181227084350862.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)

## Queue(队列)

队列(Queue)是限定所有的插入只能在表的一端进行，而所有的删除都在表的另一端进行的线性表。

1. 表中允许插入的一端称为队尾(Rear)，允许删除的一端称为队头(Front)。
2. 队列的操作是按先进先出(FIFO)的原则进行的。
3. 队列的物理存储可以用顺序存储结构，也可以用链式存储结构。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181227084616495.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)

```java
package fourtyFourth;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue{
    private LinkedList list = new LinkedList();

    public void add(Object o){
        list.addLast(o);
    }

    public Object poll(){
        return list.removeFirst();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public static void main(String[] args){
        MyQueue myQueue = new MyQueue();

        myQueue.add("one");
        myQueue.add("two");
        myQueue.add("three");

        System.out.println(myQueue.poll());
        System.out.println(myQueue.poll());
        System.out.println(myQueue.poll());

        System.out.println(myQueue.isEmpty());

        Queue queue = new LinkedList();

        queue.add("one");
        queue.offer("two");
        queue.add("three");

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.isEmpty());
    }
}
```

结果是
one
two
three
true
one
two
three
false
