---
title: String类源代码渗析
date: 2019-11-22 16:25:27
tags: Java
categories: [Java,Java SE]
---

## 相等性的比较（==）

1. 对于原生数据类型来说，比较的是左右两边的值是否相等。
2. 对于引用类型来说，比较左右两边的引用是否指向同一个对象，或者说左右两
边的引用地址是否相同。
3. java.lang.Object 类。 java.lang 包在使用的时候无需显式导入，编译时由编译器自动
帮助我们导入。
4. API （Application Programming Interface），应用编程接口。
5. 当打印引用时，实际上会打印出引用所指对象的 toString()方法的返回值，因为每个
类都直接或间接地继承自 Object，而 Object 类中定义了 toString()，因此每个类都有
toString()这个方法。

## equals方法
该方法定义在 Object 类当中，因此 Java 中的每个类都具有该方法，对于 Object 类的 equals()方法来说，它是判断调用 equals()方法的引用与传进来的引用是否一致，即这两个引用是否指向的是同一个对象。对于 Object 类的 equals()方法来说，它等价于==。 
```java
public class StringTest {

	public static void main(String[] args) {
		
		String str = new String("aa");
		String str2 = new String("aa");
		
		System.out.println(str.equals(str2));
		
		System.out.println("-----------------");
		
		String str3 = "aa";
		String str4 = "aa";
		
		System.out.println(str4.equals(str));
		System.out.println(str3.equals(str2));
		
		System.out.println("-----------------");
		
		Object object = new Object();
		Object object2 = new Object();
		
		System.out.println(object.equals(object2));
		

	}

}
```
输出结果：

```
true
-----------------
true
true
-----------------
false

```
### Object中的equals方法
为什么第四个输出结果为false呢，下面我们看一下java中Object类的equals方法的源代码。

 1. 首先先打开你电脑中的jdk，把src压缩包解压。
 2. 然后我们进入到src\java\lang中Object.java中的equals方法。
 ```java
public boolean equals(Object obj) {
        return (this == obj);
    }
 ```
 
 3. 等号代表this指向的地址和obj指向的地址是相同的，也就是说这==两个对象是同一个对象==，但是object和object2不是同一个对象所以第四个结果是false。
 4. 
如果将第四个输出改为以下的情况
```java
System.out.println(object.equals(object));
```
结果是：true
### String中的equals方法
看完上述方法后我们会疑惑为什么第一个输出为true昵？
那说明String一定把equals方法Over Read（重写）了，那我们来看一下String类中的equals方法。

```java
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
            String anotherString = (String)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }
```

 1. 先看第一个if，里面的判断是如果自己等于自己，那么就直接返回true，后面都不用判断了。
 2. 如果不是不等的话，我们看一下第二个if，里面的条件是anObject是不是String的实例，也就是说anObject是不是一个字符串，如果不是字符串直接返回false。
 3. 如果是字符串类型，就显示的向下类型转换成String类。接下来一个字符一个字符比较，判断当前字符串与传进来的字符串的==内容是否一致==。
 4. 所以第第一个结果是true，==对于 String 对象的相等性判断来说，请使用 equals()方法，而不要使用==。 
### 代码总结

```java
public class EqualsTest {

	public static void main(String[] args) {
		Student s1 = new Student("zhangsan");
		Student s2 = new Student("zhangsan");
		
		System.out.println(s1 == s2);
		System.out.println(s1.equals(s2));

	}

}

class Student{
	String name;
	
	public Student(String name) {
		this.name = name;
	}
}
```
结果如下

```
false
false
```

 1. 第一个输出结果和第二个输出实际上是相等的，都代表==。
 2. 	
```java
public boolean equals(Object anObject) {//模仿String中的equals方法重写equals方法
		if(this == anObject) {
			return true;
		}
		
		if(anObject instanceof Student) {
			Student student = (Student)anObject;
			
			if(student.name.equals(this.name)) {
				return true;
			}
		}
		
		return false;
	}	
```
在Student类中重写equals方法来实现内容相同就相等。
## String详解
 
 1. String 是常量，其对象一旦创建完毕就无法改变。当使用+拼接字符串时，会生成新
的 String 对象，而不是向原有的 String 对象追加内容。
2. String Pool（字符串池）
3. 采用字面值方式赋值
```java
 String s = “aaa”;
```
1) 查找 String Pool 中是否存在“aaa”这个对象，如果不存在，则在 String Pool 中创建
一个“aaa” 对象，然后将 String Pool 中的这个“aaa”对象的地址返回来，赋给引
用变量 s，这样 s 会指向 String Pool 中的这个“aaa”字符串对象
2) 如果存在，则不创建任何对象，直接将 String Pool 中的这个“aaa”对象地址返回来，
赋给 s 引用。
4. 采用new的方式赋值
```java
 String s = new String(“aaa”);
```
1) 首先在 String Pool 中查找有没有“aaa”这个字符串对象，如果有，则不在 String Pool
中再去创建“aaa”这个对象了，直接在堆中（heap）中创建一个“aaa”字符串对
象，然后将堆中的这个“aaa”对象的地址返回来，赋给 s 引用，导致 s 指向了堆中
创建的这个“aaa”字符串对象。
2) 如果没有，则首先在 String Pool 中创建一个“aaa“对象，然后再在堆中（heap）创
建一个” aaa“对象，然后将堆中的这个” aaa“对象的地址返回来，赋给 s 引用，
导致 s 指向了堆中所创建的这个” aaa“对象。
## intern方法
我们先看一下java的官方文档

public String intern()
Returns a canonical representation for the string object. 
A pool of strings, initially empty, is maintained privately by the class String. 

When the intern method is invoked, if the pool already contains a string equal to this String object as determined by the equals(Object) method, then the string from the pool is returned. Otherwise, this String object is added to the pool and a reference to this String object is returned. 

It follows that for any two strings s and t, s.intern() == t.intern() is true if and only if s.equals(t) is true. 

All literal strings and string-valued constant expressions are interned. String literals are defined in section 3.10.5 of the The Java™ Language Specification.

Returns: 
a string that has the same contents as this string, but is guaranteed to be from a pool of unique strings. 

我来用实例解释一下上述文档
假如String pool中有"Hello"字符串
我们输入"Hello".intern()返回的是字符串池中的hello对象
假设没有"Hello"字符串
我们输入"Hello".intern()系统会现在字符串池中创建"Hello"，然后返回这个"Hello"

## StringBuffer
1. StringBuffer同样位于java.lang包的下面，是final class不能被继承。
2. 和Sting的关系：String是常量而StringBuffer是变量，我们可以更改StringBuffer的值，追加字符串。

```java
public class StringBufferTest {

	public static void main(String[] args) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("hello").append(" world").append(" welcome").append(100).append(false);
		
		String result = buffer.toString();
		
		System.out.println(result);
		
		String s = "abc";
		int a = 100;
		boolean b = true;
		
		String str = s + a + b;
		
		System.out.println(str);
		
		int c = 200;
		
		System.out.println(a + c);
		System.out.println(100 + 200);
		System.out.println("100" + 200);
	//	buffer = buffer.append("hello");上下两种表示形式是一样的并且追加后的buffer还是原来的buffer对象并没有改变
	//	buffer.append(" world");
	//	buffer.append(" welcome");
	}

}
```
输出的结果是：
hello world welcome100false
abc100true
300
300
100200

3. 共有四种构造方法，这里我们使用无参的，我们可以用toString方法将StringBuffer转换成String。append里面的参数不是字符串类型，也会给转换为字符串。
### 字符串的+法操作
4. 字符串之间的+代表拼接并不是加法运算，**只要加法运算中有一项为字符串，系统会把所有的变量全部转换为字符串。**
