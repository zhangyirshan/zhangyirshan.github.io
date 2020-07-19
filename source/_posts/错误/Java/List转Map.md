---
layout: duplicate
title: key问题
date: 2020-07-13 11:53:49
tags: [错误,Java]
categories: [错误,Java]
---
list转map的时候，忘记了map的key不能重复的问题；其实初衷就是想 利用map的key不能重复的问题.

将list转为map，原以为是遍历list的方式，存map，然后map的key重复的话，直接覆盖,但是java8 中stream 确给我挖了个坑

```java
/**
* List -> Map
* 需要注意的是：
* toMap 如果集合对象有重复的key，会报错Duplicate key ....
*  apple1,apple12的id都为1。
*  可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
*/
Map<Integer, Apple> appleMap = appleList.stream().collect(Collectors.toMap(Apple::getId, a -> a, (k1, k2) -> k1));
```

List里面的对象元素，以某个属性来分组，例如，以id分组，将id相同的放在一起：

```java
//List 以ID分组 Map<Integer,List<Apple>>
Map<Integer, List<Apple>> groupBy = appleList.stream().collect(Collectors.groupingBy(Apple::getId));
System.err.println("groupBy:"+groupBy);
{1=[Apple{id=1, name='苹果1', money=3.25, num=10}, Apple{id=1, name='苹果2', money=1.35, num=20}], 2=[Apple{id=2, name='香蕉', money=2.89, num=30}], 3=[Apple{id=3, name='荔枝', money=9.99, num=40}]}
```