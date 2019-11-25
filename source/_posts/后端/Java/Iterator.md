---
title: Iterator
p: 后端/Java/Iterator
date: 2019-11-25 13:26:48
tags: Java
categories: [Java,Java SE]
---

## iterator

通常希望循环通过类集中的元素。例如，可能会希望显示每一个元素。到目前为止，处理这个问题的最简单方法是使用iterator，iterator是一个或者实现Iterator或者实现ListIterator接口的对象。 Iterator可以完成循环通过类集，从而获得或删除元素。 **ListIterator扩展Iterator，允许双向遍历列表，并可以修改单元。**

对于执行List的类集，也可以通过调用ListIterator来获得迭代函数。正如上面解释的那样，列表迭代函数提供了前向或后向访问类集的能力，并可让你修改元素。否则， ListIterator如同Iterator功能一样

public Iterator\<E> iterator()
Returns an iterator over the elements in this set. The elements are returned in no particular order.
（返回一个迭代器针对于集合中所有的元素，元素不以特定的顺序返回）

## Interface Iterator\<E>

public interface Iterator\<E>

An iterator over a collection.（一个针对集合的迭代器） Iterator takes the place of Enumeration in the Java collections framework. Iterators differ from enumerations in two ways:

- Iterators allow the caller to remove elements from the underlying collection during the iteration with well-defined semantics.
- Method names have been improved.

This interface is a member of the Java Collections Framework.
|Method Summary||
|--|--|
| boolean |hashNext（） Returns true if the iteration has more elements.  |
|E|next()      Returns the next element in the iteration.|
|void|remove()    Removes from the underlying collection the last element returned by the iterator (optional operation).|

## hasNext

boolean hasNext()
Returns true if the iteration has more elements.
（如果迭代还有更多的元素那就返回真）
(In other words, returns true if next would return an element rather than throwing an exception.)

## next

E next()
Returns the next element in the iteration.
(返回迭代中的下一个元素）

## 迭代器的工作原理

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181229085859965.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)
在通过迭代函数访问类集之前，必须得到一个迭代函数。每一个Collection类都提供一个iterator( )函数，该函数返回一个对类集头的迭代函数。通过使用这个迭代函数对象，可以访问类集中的每一个元素，一次一个元素。通常，使用迭代函数循环通过类集的内容，步骤如下

1. 通过调用类集的iterator( )方法获得对类集头的迭代函数。
2. 建立一个调用hasNext( )方法的循环，只要hasNext( )返回true，就进行循环迭代。
3. 在循环内部，通过调用next( )方法来得到每一个元素。

```java
package fourtySixth;

import java.util.HashSet;
import java.util.Iterator;

public class InteratorTest {
    public static void main(String[] args) {
        HashSet set = new HashSet();

        set.add("a");
        set.add("b");
        set.add("c");
        set.add("d");
        set.add("e");

    //      Iterator iter = set.iterator();
    //
    //      while(iter.hasNext())
    //      {
    //          String value = (String)iter.next();
    //          System.out.println(value);
    //      }

        for (Iterator iter = set.iterator(); iter.hasNext();) {
            String value = (String) iter.next();

            System.out.println(value);
        }

    }
}

```

结果是：
a
b
c
d
e
