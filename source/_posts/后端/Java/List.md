---
title: List
date: 2019-11-25 13:31:17
tags: Java
categories: [Java,Java SE]
---

## 链表List

### ArrayList（数组列表）

1. java.util
Class ArrayList\<E>
java.lang.Object
  extended by java.util.AbstractCollection\<E>
      extended by java.util.AbstractList\<E>
          extended by java.util.ArrayList\<E>

| 构造方法（Constructor Summary） |  具体含义|
|--|--|
|  ArrayList()  | Constructs an empty list with an initial capacity of ten. 他是一个集合，通过add（E e） 添加 |

|Method Summary |具体含义|
|--|--|
|public boolean add(E e) | Appends the specified element to the end of this list.|
|public int size()| Returns the number of elements in this list. |
|public void clear() |Removes all of the elements from this list. The list will be empty after this call returns. |
|public boolean isEmpty()|Returns true if this list contains no elements. |
|public E remove(int index)|Removes the element at the specified position in this list. Shifts any subsequent elements to the left (subtracts one from their indices). |
|public boolean remove(Object o)|Removes the first occurrence of the specified element from this list, if it is present. If the list does not contain the element, it is unchanged. More formally, removes the element with the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))) (if such an element exists). Returns true if this list contained the specified element (or equivalently, if this list changed as a result of the call). |
|public int indexOf(Object o)|Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element. More formally, returns the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.|
|Object[] toArray()|Returns an array containing all of the elements（元素） in this list in proper sequence (from first to last element). |

#### add方法，get方法，size方法，remove方法，clear方法

```java
import java.util.ArrayList;

public class ArrayListTest1 {

    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();

        arrayList.add("hello");
        arrayList.add("world");
        arrayList.add("world");
        arrayList.add("welcome");

        String s1 = (String) arrayList.get(0);
        String s2 = (String) arrayList.get(1);
        String s3 = (String) arrayList.get(2);
        String s4 = (String) arrayList.get(3);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println("-----------------");

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }

        arrayList.remove(0);
        arrayList.remove("welcome");

        System.out.println("-----------------");

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }
        arrayList.add("aaa");
        arrayList.add("bbb");

        System.out.println(arrayList.indexOf("world"));
        System.out.println(arrayList.indexOf("aaa"));

        arrayList.clear();

        System.out.println(arrayList.size());
        System.out.println(arrayList.isEmpty());

    }

}
```

结果是
hello
world
world
welcome
------------ -----
hello
world
world
welcome
------------ -----
world
world
0
2
0
true

说明集合元素可重复。

```java
import java.util.ArrayList;

public class ArrayListTest2 {

    public static void main(String[] args) {
        ArrayList list = new ArrayList();

        list.add("hello");
        list.add(new Integer(2));

        String str = (String)list.get(0);
        Integer in = (Integer)list.get(1);

        System.out.println(str);
        System.out.println(in.intValue());

    }

}
```

结果是
hello
2

输入什么类型输出什么类型，如果将in的类型改为String编译时不会出错，但运行时会出错，出错原因： ***java.lang.Integer cannot be cast to java.lang.String***。

```java
ArrayList list = new ArrayList();
list.add(3);
int i = (int)list.get(0);
System.out.println(i);
```

这样是错误的因为add中的参数是对象，而8种原生数据类型不是对象，所以会出错。
但是实际上你编译运行时是不会报错的是因为编译器自动将3装箱放入ArrayList中，get方法又将object强转为integer对象

```java
public class Main {
    public static void main(String[] args) {
    //自动装箱
    Integer total = 99;

    //自定拆箱
    int totalprim = total;
    }
}
```

简单一点说，装箱就是自动将基本数据类型转换为包装器类型；拆箱就是自动将包装器类型转换为基本数据类型。

我们用arrayList实现个例子：将3，4，5，6存到集合中然后取出求和输出

```java
import java.util.ArrayList;

public class ArrayListTest3 {

    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(5));
        list.add(new Integer(6));

        int sum = 0;
        for(int i=0;i<list.size();i++) {
            int value = ((Integer)list.get(i)).intValue();
            sum+=value;
        }
        System.out.println(sum);
    }
}
```

结果是
18

```java
import java.util.ArrayList;

public class ArrayListTest4 {

    public static void main(String[] args) {
        ArrayList list = new ArrayList();

        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(5));
        list.add(new Integer(6));

        Integer[] in = (Integer[])list.toArray();
        for(int i= 0;i<in.length;i++) {
            System.out.println(in[i].intValue());
        }
    }

}
```

结果出错
***[Ljava.lang.Object; cannot be cast to [Ljava.lang.Integer;***
虽然Object可以转换为Integer，但Object[]不能转换为Integer[]，因此我们只能用遍历。

```java
public class ArrayListTest4 {

    public static void main(String[] args) {
        ArrayList list = new ArrayList();

        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(3));
        list.add(new Integer(4));
        list.add(new Integer(5));
        list.add(new Integer(6));

        /*
        * 不能将Object[]转换为Integer[]
        * 因为Integer[]继承的是Object而不是Object[]
        * 或者我们可以这么想，假设Object[]中有字符串那么我们转换时就会出错。
        */

        Object[] in = list.toArray();
        for(int i= 0;i<in.length;i++) {
            System.out.println(((Integer)in[i]).intValue());
        }
    }

}
```

**集合中存放的依然是对象的引用而不是对象本身**。

```java
package Fortieth;

import java.util.ArrayList;

public class ArrayListTest5 {

    public static void main(String[] args) {
        ArrayList list = new ArrayList();

        list.add(new Point(2, 3));
        list.add(new Point(2, 2));
        list.add(new Point(4, 4));

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));// 虽然返回的是Object但是实际上是Point，系统会调用Point的toString方法，因为我们没有重写所以会继承Object中的toString方法
        }
        System.out.println(list);// 调用list的toString方法
    }
}

```

```java
package Fortieth;

public class Point {
    int x;
    int y;

    /**
    * @param x coordinate of x
    * @param y coordinate of y
    */

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
```

结果是
Fortieth.Point@75b84c92
Fortieth.Point@6bc7c054
Fortieth.Point@232204a1
[Fortieth.Point@75b84c92, Fortieth.Point@6bc7c054, Fortieth.Point@232204a1]
很显然直接输出get返回的参数是Point调用自己的toString方法，因为没有重写，所以使用的是继承Object方法中的toString。
而直接输出list先调用list的toString方法输出[]然后再调用每一个元素的toString方法。

```java
import java.util.ArrayList;

public class ArrayListTest5 {

    public static void main(String[] args) {
        ArrayList list = new ArrayList();

        list.add(new Point(2, 3));
        list.add(new Point(2, 2));
        list.add(new Point(4, 4));

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));// 虽然返回的是Object但是实际上是Point，系统会调用Point的toString方法，因为我们没有重写所以会继承Object中的toString方法
        }
        System.out.println(list);// 调用list的toString方法
    }
}

```

```java
public class Point {
    int x;
    int y;

    /**
    * @param x coordinate of x
    * @param y coordinate of y
    */

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
    * @return return coordinate of axis of x and y
    */

    public String toString() {
        return "x=" + this.x + ",y=" + this.y;
    }
}
```

结果是
x=2,y=3
x=2,y=2
x=4,y=4
[x=2,y=3, x=2,y=2, x=4,y=4]

1. ArrayList 底层采用数组实现，当使用不带参数的构造方法生成ArrayList对象时，实际上会在底层生成一个长度为 10 的 Object 类型数组
2. 如果增加的元素个数超过了 10 个，那么 ArrayList 底层会新生成一个数组，长度为原数组的 1.5 倍+1， 然后将原数组的内容复制到新数组当中，并且后续增加的内容都会放到新数组当中。当新数组无法容纳增加的元素时，重复该过程。
3. 对于 ArrayList 元素的删除操作，需要将被删除元素的后续元素向前移动，代价比较高，对于 ArrayList 元素的插入操作，同样如此，如果已经有3个元素，再第二个元素位插入一个新的元素，后续元素将向后移动。
4. 集合当中只能放置对象的引用，无法放置原生数据类型，我们需要使用原生数据类型的包装类才能加入到集合当中。
5. 集合当中放置的都是 Object 类型，因此取出来的也是 Object 类型，那么必须要使用强制类型转换将其转换为真正的类型（放置进去的类型）。

### ArrayList

无参的构造方法我们已经了解了，接下来我们了解下有参的构造方法

1. public ArrayList(int initialCapacity)
Constructs an empty list with the specified initial capacity. （构造一个空的列表使用初始的容量）
initialCapacity - the initial capacity of the list （列表最开始的容量）
建立一个数组列表，该数组有指定的初始容量（capacity）。容量是用于存储元素的基本数组的大小。当元素被追加到数组列表上时，容量会自动增加。
2. public ArrayList(Collection<? extends E> c)
Constructs a list containing the elements of the specified collection, in the order they are returned by the collection's iterator. （构造一个列表包含指定集合的元素，顺序是在集合迭代的顺序返回）
c - the collection whose elements are to be placed into this list
建立一个数组列表，该数组列表由类集c中的元素初始化
**但是LinkedList只有两个没有第三个，因为没必要**

### LinkedList（链表）（链接的列表）

java.util
Class LinkedList\<E>
java.lang.Object
java.util.AbstractCollection\<E>
java.util.AbstractList\<E>
java.util.AbstractSequentialList\<E>
java.util.LinkedList\<E>

LinkedList和ArrayList都是实现的list接口但是LinkedList有一些特有的方法

### LinkedList

```java
LinkedList list = new LinktedLIst();
list.add("aaa");
```

1. 当向 ArrayList 添加一个对象时，实际上就是将该对象放置到了 ArrayList 底层所维护的数组当中；当向 LinkedList 中添加一个对象时，实际上 LinkedList 内部会生成一个Entry 对象，该 Entry 对象的结构为：

```java
Entry{
    Entry previous;
    Object element;
    Entry next;
}
Entry entry = new Entry();
entry.element = "aaa";
entry
lis.add(;
```)

其中的 Object 类型的元素 element 就是我们向 LinkedList 中所添加的元素，然后 Entry又构造好了向前与向后的引用 previous、 next，最后将生成的这个 Entry 对象加入到了表当中。 换句话说， **LinkedList 中所维护的是一个个的 Entry 对象**。


#### addLast

public void addLast(E e)
Appends the specified element to the end of this list.

#### addFirst

public void addFirst(E e)
Inserts the specified element at the beginning of this list.

```java
package fourtyFirst;
import java.io.ObjectStreamException;
import java.util.LinkedList;

public class LinkedListTest1 {

    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        list.add("F");
        list.add("B");
        list.add("D");
        list.add("E");
        list.add("C");

        list.addLast("Z");
        list.addFirst("A");

        list.add(1, "A2");

        System.out.println("最初的集合" + list);

        list.remove("F");
        list.remove(2);

        System.out.println("变化之后的集合"+list);

        Object value = list.get(2);
        list.set(2, (String)value + "changed");

        System.out.println("最后的集合"+ list);
    }
}
```

结果是
最初的集合[A, A2, F, B, D, E, C, Z]
变化之后的集合[A, A2, D, E, C, Z]
最后的集合[A, A2, Dchanged, E, C, Z]

## 链表的数据结构

1. 一般将数据结构分为两大类：**线型数据结构和非线性数据结构**。线型数据结构有线性表、栈、队列、串、数组和文件非线性数据结构有树和图（继承）
2. 线性表的逻辑结构是n个数据元素的有限序列:(a1, a2 ,a3,…an)n为线性表的长度(n≥0)， n=0的表称为空表。2 数据元素呈线性关系。必存在唯一的称为“第一个” 的数据元素；必存在唯一的称为“最后一个”的数据元素；除第一个元素外，每个元素都有且只有一个前驱元素； 除最后一个素外，每个元素都有且只有一个后继元素。
3. 所有数据元素在同一个线性表中必须是相同的数据类型。
4. 线性表按其存储结构可分为顺序表和链表。用顺序存储结构存储的线性表称为顺序表；
用链式存储结构存储的线性表称为链表。
5. 将线性表中的数据元素依次存放在某个存储区域中,所形成的表称为顺序表。 **一维数组就是用顺序方式存储的线性表**。

### 单向链表

#### 创建

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181222211910174.png)

```java
package fourtyFirst;

public class NodeTest {

    public static void main(String[] args) {
        Node fNode = new Node("node1");
        Node sNode = new Node("node2");
        Node tNode = new Node("node3");

        fNode.next = sNode;
        sNode.next = tNode;
        tNode.next = null;

        System.out.println(fNode.next.next.data);
    }
}
```

```java
package fourtyFirst;

public class Node {
    String data;//存放节点数据本身
    Node next; //存放指向下一个节点的引用

    public Node(String str) {
        data = str;
    }
}
```

结果是
node3

#### 插入

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181222212240254.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)

在上述main方法中加入下列代码

```java
System.out.println("---------------");

Node node4 = new Node("node4");

fNode.next = node4;
node4.next = sNode;

System.out.println(fNode.next.next.next.data);

```

结果是
node3
\---------------
node3

#### 删除

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181222212729606.png)

```java
System.out.println("---------------");

fNode.next = sNode;
node4.next = null;

System.out.println(fNode.next.next.data);
```

结果是
node3
\---------------
node3
\---------------
node3

### 循环链表

![在这里插入图片描述](https://img-blog.csdnimg.cn/2018122221303666.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)

```java
tNode.next = fNode;
```

### 双向循环链表

![在这里插入图片描述](https://img-blog.csdnimg.cn/20181222213105107.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)

```java
package fourtyFirst;

public class Node2 {
    Node2 previous;

    String data;

    Node2 next;

    public Node2(String data) {
        this.data = data;
    }
}
```

```java
package fourtyFirst;

public class Node2Test {
    public static void main(String[] args) {
        Node2 node1 = new Node2("node1");
        Node2 node2 = new Node2("node2");
        Node2 node3 = new Node2("node3");

        node1.previous = node3;
        node2.previous = node1;
        node3.previous = node2;

        node1.next = node2;
        node2.next = node3;
        node3.next = node1;

        Node2 node4 = new Node2("node4");
        //插入到1和2之间
        node1.next = node4;
        node4.next = node2;

        node2.previous = node4;
        node4.previous = node1;
        //删除4
        node1.next = node2;
        node4.next = null;

        node2.previous = node1;
        node4.previous = null;
    }
}

```

`LinkedList实际上就是双向链表`

## 关于 ArrayList 与 LinkedList 的比较分析

1. ArrayList 底层采用数组实现， LinkedList 底层采用双向链表实现。
2. 当执行插入或者删除操作时， 采用 LinkedList 比较好。
3. 当执行搜索操作时， 采用 ArrayList 比较好。
