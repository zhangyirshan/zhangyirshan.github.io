---
title: 集合
p: 后端/Java/集合
date: 2019-11-25 10:40:17
tags: Java
categories: [Java,Java SE]
---
## 集合Collection概念

1. 对于 Java 中的常量的命名规则：所有单词的字母都是大写，如果有多个单词，
    那么使用下划线连接即可。 比如说：
    public static final int AGE_0F_PERSON = 20;
2. **在 Java 中声明 final 常量时通常都会加上 static 关键字**，这样对象的每个实例
    都会访问唯一一份常量值。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181220214709819.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)

### Interface Collection\<E>

public interface Collection\<E>extends Iterable\<E>
**E**可以先当作Object类型具体后面学。

#### Collection的Java API中的定义

The root interface in the collection hierarchy.
继承层次的根接口
 A collection represents a group of objects,known as its elements.
一个集合代表一组对象 ，这些对象我们称为元素
Some collections allow duplicate elements and others do not.
某些集合允许重复的元素而其他的是不可以的。
Some are ordered and others unordered.
有些是排序的有些不是排序的
The JDK does not provide any *direct implementations* of this interface:
JDK不提供任何对这个接口的直接实现
 it provides implementations of more specific subinterfaces like Set and List.
他提供了更加具体的子接口像Set和List来实现Collection这个父接口
This interface is typically used to pass collections around and manipulate them where maximum generality is desired.
这个接口通常被用作传递集合并且操纵集合。

### Interface Set\<E>

public interface Set\<E>extends Collection\<E>

A collection that contains no duplicate elements. More formally, sets contain no pair of elements e1 and e2 such that e1.equals(e2), and at most one null element. As implied by its name, this interface models the mathematical set abstraction.

### Interface List\<E>

public interface List\<E>extends Collection\<E>

An ordered collection (also known as a sequence).
一个**有序**的集合（也称之为***序列***）
 The user of this interface has precise control over where in the list each element is inserted. The user can access elements by their integer index (position in the list), and search for elements in the list.

## Collections

### reverseOrder()

reverseOrder() 方法用于获取一个比较用来实现Comparable接口的对象的集合的自然顺序相反。
以下是java.util.Collections.reverseOrder()方法的声明。
public static \<T> Comparator\<T> reverseOrder()
在方法调用返回一个比较器，它强行上实现Comparable接口的对象的集合的自然顺序相反。

### shuffle(List<?>)

shuffle(List<?>) 方法用于随机排列随机使用一个默认的源指定的列表。
以下是java.util.Collections.shuffle()方法的声明。
public static void shuffle(List<?> list)
list-- 将要改组的列表。

```java
package fourtyThird;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class CollectionsTest {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.add(new Integer(-8));
        list.add(new Integer(20));
        list.add(new Integer(-20));
        list.add(new Integer(8));

        Comparator r = Collections.reverseOrder();

        Collections.sort(list, r);

        for (Iterator iter = list.iterator(); iter.hasNext();) {
            System.out.println(iter.next() + " ");
        }

        System.out.println("-----------------");

        Collections.shuffle(list);

        for (Iterator iter = list.iterator(); iter.hasNext();) {
            System.out.println(iter.next() + " ");
        }

        System.out.println("minimum value: " + Collections.min(list));
        System.out.println("maximum value: " + Collections.max(list));
    }
}

```

结果是：
20
8
-8
-20
\-----------------
-20
20
-8
8
minimum value: -20
maximum value: 20
