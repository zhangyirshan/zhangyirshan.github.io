---
title: Set
p: 后端/Java/Set
date: 2019-11-25 11:20:33
tags: Java
categories: [Java,Java SE]
---
## Set

public interface Set\<E>extends Collection\<E>（继承collection接口）
A collection that contains no duplicate elements.
（一个集合不能包含重复的元素）

### add

public boolean add(E e)
Adds the specified element to this set if it is not already present.
（将指定的元素e加入如果集合中没有）
More formally, adds the specified element e to this set if this set contains no element e2 such that (e==null ? e2==null : e.equals(e2)).
（更重要的是，如果它不包含满足这个(e==null ? e2==null : e.equals(e2))条件的时候，它会将这个e加入这个集合中）
 If this set already contains the element, the call leaves the set unchanged and returns false.
 （如果这个集合中包含这个元素，这个集合就不改变，并且返回false）

```java
package fourtyfifth;

import java.util.HashSet;

public class SetTest1 {

    public static void main(String[] args) {
        HashSet set = new HashSet();

        set.add("c");
        System.out.println(set.add("a"));
        set.add("b");
        set.add("d");
        System.out.println(set.add("a"));

        System.out.println(set);

    }

}

```

结果是：
true
false
[a, b, c, d]
说明set是无序的，并且元素是不能重复的。

### equals

Indicates whether some other object is "equal to" this one.
（用来指示是否另外一个对象“equal to”调用equals方法的对象）
The equals method implements an equivalence relation on non-null object references:

It is reflexive（自反的）: for any non-null reference（引用） value x, x.equals(x) should return true.
It is symmetric（对称的）: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
It is transitive（传递性）: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
It is consistent（一致性）: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
For any non-null reference value x, x.equals(null) should return false.

 关于 Object 类的 equals 方法的特点
a) 自反性： x.equals(x)应该返回 true，前提是不为空。
b) 对称性： x.equals(y)为 true，那么 y.equals(x)也为 true。
c) 传递性： x.equals(y)为 true 并且 y.equals(z)为 true，那么 x.equals(z)也应该为 true。
d) 一致性： x.equals(y)的第一次调用为 true，那么 x.equals(y)的第二次、第三次、第 n次调用也应该为 true，前提条件是在比较之间没有修改 x 也没有修改y。
e) 对于非空引用 x， x.equals(null)返回 false。
The equals method for class Object implements the most discriminating possible equivalence relation on objects;（对于Object类的equals方法来说，它实现了最有差别的可能等价关系在对象上）
 that is, for any non-null reference values x and y, this method returns true if and only if x and y refer to the same object (x == y has the value true).
 （对于任何的非空引用x，y来说，这个方法返回真并且只返回真，前提是x，y指向同一个对象）

Note that it is generally necessary to override the hashCode method whenever this method is overridden,
（注意通常我们有必要去重写hashCode方法当这个方法被重写的时候）
 so as to maintain the general contract for the hashCode method, which states that equal objects must have equal hash codes.
（以便维护hashCode方法的一般契约,表明相等的对象必须有相等的hashCode)

### hashCode

public int hashCode()
（返回一个整数）
Returns a hash code value for the object.
（返回这个对象的hash code 值
This method is supported for the benefit of hashtables such as those provided by java.util.Hashtable.
（这个方法被支持为了hashtables这个方法）
The general contract of hashCode is:
（hashCode的一般性契约）
Whenever it is invoked on the same object more than once during an execution of a Java application,
（无论何时当我调用一个对象一次以上的hashCode在java应用的一次执行过程当中）
the hashCode method must consistently return the same integer,
（那么这个hashCode必须返回相同的整数值）
provided no information used in equals comparisons on the object is modified.
（假设这个对象的信息没有被修改）
This integer need not remain consistent from one execution of an application to another execution of the same application.
（这个整数不需要保持一致，在第一次执行过程和另一次执行过程）
If two objects are equal according to the equals(Object) method, then calling the hashCode method on each of the two objects must produce the same integer result.
（如果两个对象通过equals方法相等，那么这两个对象调用hashCode方法一定返回相同的整型值）
It is not required that if two objects are unequal according to the equals (java.lang.Object) method, then calling the hashCode method on each of the two objects must produce distinct integer results. However, the programmer should be aware that producing distinct integer results for unequal objects may improve the performance of hashtables.
（并不需要满足两个对象不相等，hashCode一定不一样，然而，如果不同则可以提高应用的性能）
As much as is reasonably practical, the hashCode method defined by class Object does return distinct integers for distinct objects.
（根据实际的情况来说，有类Object定义的hashCode方法会针对不同的对象返回不同的整数值。）
 (This is typically implemented by converting the internal address of the object into an integer, but this implementation technique is not required by the JavaTM programming language.)
 （通常将内部的地址转换为十六进制的数，但是这种机制并不是强制的）

关于 Object 类的 hashCode()方法的特点：
a) 在 Java 应用的一次执行过程当中，对于同一个对象的 hashCode 方法的多次调用，他们应该返回同样的值（前提是该对象的信息没有发生变化）。
b) 对于两个对象来说，如果使用 equals 方法比较返回 true，那么这两个对象的 hashCode
值一定是相同的。
c) 对于两个对象来说，如果使用 equals方法比较返回 false，那么这两个对象的 hashCode
值不要求一定不同（可以相同，可以不同）， 但是如果不同则可以提高应用的性能。
d) 对于 Object类来说，不同的 Object对象的 hashCode值是不同的（Object类的 hashCode值表示的是对象的地址）。

 当使用 HashSet 时， hashCode()方法就会得到调用，判断已经存储在集合中的对象的hash code 值是否与增加的对象的 hash code 值一致；如果不一致，直接加进去；如果一致，再进行 equals 方法的比较， equals 方法如果返回 true，表示对象已经加进去了，就不会再增加新的对象，否则加进去。

```java
package fourtyfifth;

import java.util.HashSet;

public class SetTest2{
    public static void main(String[] args){
        HashSet set = new HashSet();

        set.add(new People("zhangsan"));
        set.add(new People("lisi"));
        set.add(new People("zhangsan"));//两个zhangsan是不同的对象地址不同一定能放进去

        System.out.println(set);
        System.out.println("---------------");

        People p1 = new People("zhangsan");

        set.add(p1);
        set.add(p1);//p1的地址是一样的，然后用equals方法，发现一致所以只能放一个

        System.out.println(p1);
        System.out.println("---------------");

        String s1 = new String("a");
        String s2 = new String("a");

        System.out.println("hash code: " + (s1.hashCode() == s2.hashCode()));

        set.add(s1);
        set.add(s2);

        System.out.println(set);
    }
}

class People{
    String name;

    public People(String name){
        this.name = name;
    }
}
```

结果是：
[fourtyfifth.People@6bc7c054,fourtyfifth.People@232204a1,fourtyfifth.People@75b84c92]
\---------------
fourtyfifth.People@4aa298b7
\---------------
hash code: true
[a,fourtyfifth.People@6bc7c054,fourtyfifth.People@232204a1,fourtyfifth.People@4aa298b7, fourtyfifth.People@75b84c92]

 ***如果我们重写 equals 方法，那么也要重写 hashCode 方法，反之亦然。***

```java
package fourtyfifth;

import java.util.HashSet;

public class SetTest3{
    public static void main(String[] args){
        HashSet set = new HashSet();

        Student s1 = new Student("zhangsan");
        Student s2 = new Student("zhangsan");

        System.out.println(s1.hashCode()==s2.hashCode());
        set.add(s1);
        set.add(s2);

        System.out.println(set.hashCode());
        System.out.println(set);
    }
}

class Student{
    String name;

    public Student(String name){
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /* public int hashCode()
        {
        return this.name.hashCode();
        }

        public boolean equals(Object obj)
        {
        if(this == obj)
        {
        return true;
        }

        if(null != obj && obj instanceof Student)
        {
        Student s = (Student)obj;

        if(name.equals(s.name))
        {
        return true;
        }
        }

        return false;
        }
    */
}
```

结果是：
true
-1432604525
[fourtyfifth.Student@aa9c3093]
注释中是我们自己编写的hashCode和equals方法实现的功能和eclipse生成的一样。

## SortedSet

public interface SortedSet\<E>extends Set\<E>

A Set that further provides a total ordering on its elements. The elements are ordered using their natural ordering（自然排序）, or by a *Comparator* typically provided at sorted set creation time.

## TreeSet

java.util
Class TreeSet\<E>
java.lang.Object
  extended by java.util.AbstractCollection\<E>
      extended by java.util.AbstractSet\<E>
          extended by java.util.TreeSet\<E>
All Implemented Interfaces:
Serializable, Cloneable, Iterable\<E>, Collection\<E>, NavigableSet\<E>,***Set\<E>, SortedSet\<E>***
TreeSet为使用树来进行存储的Set接口提供了一个工具， 对象按升序存储。访问和检索是很快的。在存储了大量的需要进行快速检索的排序信息的情况下， TreeSet是一个很好的选择。

```java
package fourtyThird;

import java.util.TreeSet;

public class TreeSetTest{
    public static void main(String[] args){
        TreeSet set = new TreeSet();
        set.add("C");
        set.add("A");
        set.add("B");
        set.add("E");
        set.add("F");
        set.add("D");

        System.out.println(set);
    }
}
```

结果是：
[A, B, C, D, E, F]
正如上面解释的那样，因为TreeSet按树存储其元素，它们被按照排序次序自动安排，如程序输出所示
| 构造函数 | 解释 |
|--|--|
| TreeSet( ) | 第一种形式构造一个空的树集合，该树集合将根据其元素的自然顺序按升序排序。 |
|TreeSet(Collection c)|第二种形式构造一个包含了c的元素的树集合。|
|TreeSet(Comparator comp)|第三种形式构造一个空的树集合，它按照由comp指定的比较函数进行排序。|
|TreeSet(SortedSet ss)|第四种形式构造一个包含了ss的元素的树集合|

### Comparator（比较函数）

java.util
Interface Comparator\<T>
Type Parameters:
T - the type of objects that may be compared by this comparator

#### Compare

int compare(T o1,T o2)
Compares its two arguments for order. Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
**Returns:**
a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second. （返回一个正数，0，或负数，当第一个参数小于，等于，或者大于第二个参数时）

```java
compare（new Integer(1), new Integer(2));
//返回一个正数
compare（new Integer(2), new Integer(2));
//返回0
compare（new Integer(3), new Integer(2));
//返回负数
```

Note that it is always safe not to override Object.equals(Object)。

#### Add

Throws:
**ClassCastException** - if the specified object cannot be compared with the elements currently in this set
**NullPointerException** - if the specified element is null and this set uses natural ordering, or its comparator does not permit null elements

```java
package fourtyThird;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetTest2{
    public static void main(String[] args){
        TreeSet set = new TreeSet(new PersonComparator());//当排序的类可以自然排序时可以不指定比较方法

        Person p1 = new Person(10);
        Person p2 = new Person(20);
        Person p3 = new Person(30);
        Person p4 = new Person(40);

        set.add(p1);
        set.add(p2);//如果不告诉TreeSet类用什么方法去比较，就会抛出异常,也就是说TreeSet set = new TreeSet();，在调用set.add(p2)就会抛出ClassCastException异常。
        set.add(p3);
        set.add(p4);

        for(Iterator iter = set.iterator(); iter.hasNext();)
        {
            Person p = (Person)iter.next();
            System.out.println(p.score);
        }

    }
}

class Person{
    int score;

    public Person(int score){
        this.score = score;
    }

    public String toString(){
        return String.valueOf(this.score);
    }
}

class PersonComparator implements Comparator//告诉TreeSet如何比较Person类{
    public int compare(Object arg0, Object arg1){
        Person p1 = (Person) arg0;
        Person p2 = (Person) arg1;
        return p1.score -p2.score;//升序

    //  return p2.score - p1.score;//降序
    }
}

```

结果是：
10
20
30
40

```java
package fourtyThird;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetTest3 {
    public static void main(String[] args) {
        TreeSet set = new TreeSet(new MyComparator());

        set.add("C");
        set.add("A");
        set.add("B");
        set.add("E");
        set.add("a");
        set.add("F");
        set.add("D");

        for (Iterator iter = set.iterator(); iter.hasNext();) {
            String value = (String) iter.next();

            System.out.println(value);

        }

    }
    }

    class MyComparator implements Comparator {
    @Override
    public int compare(Object arg0, Object arg1) {
        String s1 = (String) arg0;
        String s2 = (String) arg1;

        return s2.compareTo(s1);
    }
}

```

结果是：
a
F
E
D
C
B
A

int compare(T o1,T o2)
Compares its two arguments for order. Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
s2.compareTo(s1);是降序

## HashSet

1. HashSet扩展AbstractSet并且实现Set接口。
2. 它创建一个类集，该类集使用散列表进行存储。散列表通过使用称之为散列法的机制来存储信息。
3. 在散列（hashing）中，一个关键字的信息内容被用来确定唯一的一个值，称为**散列码**（hash code）。而散列码被用来当做与关键字相连的数据的存储下标。关键字到其散列码的转换是自动执行的——你看不到散列码本身。你的程序代码也不能直接索引散列表。

    ```java
    package fourtyThird;

    public class HashTest1 {

        public static void main(String[] args) {
            String str="hello";
            int hash=0;
            for(int i=0;i<str.length();i++) {
                hash=31*hash+str.charAt(i);
                System.out.println(hash);
            }
        }
    }
    ```

    结果是
    104
    3325
    103183
    3198781
    99162322
    那么下面散列码的结果不同也就好解释了。s和t都还是String对象，散列码由内容获得，结果一样。sb和tb是StringBuffer对象，自身没有hashCode方法，只能继承Object的默认方法，散列码是对象地址，当然不一样了。

    ```java
    package fourtyThird;

    public class HashTest1 {

        public static void main(String[] args) {
            String s=new String("OK");
            String t="OK";
            StringBuffer sb=new StringBuffer(s);
            StringBuffer tb=new StringBuffer(t);
            int hash=0;
            System.out.println("s");
            for(int i=0;i<s.length();i++) {
                hash=s.hashCode();
                System.out.println(hash);
            }
            System.out.println("t");
            for(int i=0;i<t.length();i++) {
                hash=t.hashCode();
                System.out.println(hash);
            }
            System.out.println("sb");
            for(int i=0;i<sb.length();i++) {
                hash=sb.hashCode();
                System.out.println(hash);
            }
            System.out.println("tb");
            for(int i=0;i<tb.length();i++) {
                hash=tb.hashCode();
                System.out.println(hash);
            }
        }
    }
    ```

    结果是
    s
    2524
    2524
    t
    2524
    2524
    sb
    1975012498
    1975012498
    tb
    1808253012
    1808253012
    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20181224223400833.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzkwNzMzMg==,size_16,color_FFFFFF,t_70)

4. 散列法的优点在于即使对于大的集合，它允许一些基本操作如add( ),contains( ),remove( )和size( )方法的运行时间保持不变。它创建一个类集，该类集使用散列表进行存储。散列表通过使用称之为散列法的机制来存储信息。
5. hashCode方法必须与equals方法必须兼容
    如果我们自己定义了一个类，想对这个类的大量对象组织成散列表结构便于查找。有一点一定要注意：就是hashCode方法必须与equals方法向兼容。

    ```java
    class Employee {
        int id;
        String name = "";

        // 相同id对象具有相同散列码
        public int hashCode() {
            return id;
        }

        // equals必须比较id
        public boolean equals(Employee x) {
            if (this.id == x.id)
                return true;
            else
                return false;
        }
    }
    ```

    public HashSet()
    Constructs a new, empty set; the backing HashMap instance has default initial capacity (16) and load factor (0.75).（构造一个新的空集；支持HashMap实例具有默认的初始容量（16）和负载因子（0.75）。）

6. 散列表又称为哈希表。散列表算法的基本思想是：以结点的关键字为自变量，通过一定的函数关系（散列函数）计算出对应的函数值，以这个值作为该结点存储在散列表中的地址。当散列表中的元素存放太满，就必须进行再散列，将产生一个新的散列表，所有元素存放到新的散列表中，原先的散列表将被删除。
7. 在Java语言中，通过负载因子(loadfactor)来决定何时对散列表进行再散列。例如：如果负载因子是0.75，当散列表中已经有75%的位置已经放满，那么将进行再散列。负载因子越高(越接近1.0)，内存的使用效率越高，元素的寻找时间越长。负载因子越低(越接近0.0)，元素的寻找时间越短，内存浪费越多。

8. HashSet类的缺省负载因子是0.75。

    |构造方法|  解释|
    |--|--|
    | HashSet( ) |第一种形式构造一个默认的散列集合。  |
    |HashSet(Collection c)|第二种形式用c中的元素初始化散列集合。|
    |HashSet(int capacity)|第三种形式用capacity初始化散列集合的容量。|
    |HashSet(int capacity, float fillRatio)|第四种形式用它的参数初始化散列集合的容量和填充比（也称为加载容量）。填充比必须介于0.0与1.0之间，它决定在散列集合向上调整大小之前，有多少能被充满。具体的说，就是当元素的个数大于散列集合容量乘以它的填充比时，散列集合被扩大。对于没有获得填充比的构造函数，默认使用0.75|
    HashSet没有定义更多的其他方法。

    ```java
    package fourtyThird;

    import java.util.HashSet;
    import java.util.Iterator;

    public class HashTest1 {

        public static void main(String[] args) {
            HashSet set = new HashSet();
            set.add("3");
            set.add("6");
            set.add("5");
            set.add("1");
            set.add("9");
            Iterator iter = set.iterator();
            while(iter.hasNext()){
                String value = (String)iter.next();  
                System.out.println(value);
            }
        }
    }
    ```

    结果是：
    1
    3
    5
    6
    9

    • 重要的是，**注意：散列集合并没有确保其元素的顺序**，因为散列法的处理通常不让自己参与创建排序集合。如果需要排序存储，另一种类集——**TreeSet**将是一个更好的选择。
