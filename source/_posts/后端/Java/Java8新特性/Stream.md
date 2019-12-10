---
title: Stream
p: 后端/Java/Java8新特性/Stream
date: 2019-12-10 10:09:16
tags: Java
categories: [Java,Java8新特性]
---
##　强大的Stream API

Java8中有两大最为重要的改变。第一个是 Lambda 表达式；另外一个则是 **Stream API(java.util.stream.*)**。
Stream 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。使用Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数据库查询。也可以使用 Stream API 来并行执行操作。简而言之，Stream API 提供了一种高效且易于使用的处理数据的方式。

### 什么是 Stream

流(Stream) 到底是什么呢？
是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。
**“集合讲的是数据，流讲的是计算！”**
注意：
    1. Stream 自己不会存储元素。
    2. Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。
    3. Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。

### Stream 的操作三个步骤

- 创建 Stream
    一个数据源（如：集合、数组），获取一个流
- 中间操作
    一个中间操作链，对数据源的数据进行处理
- 终止操作(终端操作)
    一个终止操作，执行中间操作链，并产生结果

{% asset_img Stream流.png Stream流%}

### 创建tream

Java8 中的 Collection 接口被扩展，提供了
两个获取流的方法：
    - default Stream\<E> stream() : 返回一个顺序流
    - default Stream\<E> parallelStream() : 返回一个并行流

1. 由数组创建流
2. 由值创建流
3. 由函数创建流：创建无限流

```java
@Test
public void test1(){
    //1.可以公共Collection系列集合提供的stream()或parallelStream()
    List<String> list = new ArrayList<>();
    Stream<String> stream = list.stream();

    //2. 通过Arrays中的静态方法stream()获取数组流
    Employee[] employees = new Employee[10];
    Stream<Employee> stream1 = Arrays.stream(employees);

    //3. 通过Stream类中的静态方法of()
    Stream<String> stream2 = Stream.of("aa", "bb", "cc");

    //4. 创建无限流
    //迭代
    Stream<Integer> iterate = Stream.iterate(0, (x) -> x + 2);
    iterate.limit(10).forEach(System.out::println);

    //生成
    Stream.generate(() -> (int) (Math.random() * 100))
    .limit(5).forEach(System.out::println);
}
```

### Stream 的中间操作

多个**中间操作**可以连接起来形成一个**流水线**，除非流水线上触发终止操作，否则**中间操作不会执行任何的处理！而在终止操作时一次性全部处理，称为“惰性求值”。**

#### 筛选与切片

|方法|描述|
|--|--|
|filter(Predicate p) |接收 Lambda ， 从流中排除某些元素。|
|distinct()|筛选，通过流所生成元素的 hashCode() 和 equals() 去除复元素|
|limit(long maxSize) |截断流，使其元素不超过给定数量。|
|skip(long n) |跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补|

```java
@Test
public void test2(){
    List<Employee> emps = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 59, 6669.99),
            new Employee("王五", 18, 3333.99),
            new Employee("赵六", 18, 2222.99),
            new Employee("田七", 18, 5555.99),
            new Employee("java", 18, 5555.99)
    );

    Stream<Employee> stream = emps.stream();
    //中间操作：不会执行任何操作
    Stream<Employee> employeeStream = stream.filter(e -> e.getAge() > 35);
    //终止操作，执行终止操作才会操作中间操作，否则中间操作不会执行
    //一次性执行全部内容，即“惰性求值”
    employeeStream.forEach(System.out::println);
}

List<Employee> emps = Arrays.asList(
        new Employee("张三", 18, 9999.99),
        new Employee("李四", 59, 6669.99),
        new Employee("王五", 18, 3333.99),
        new Employee("赵六", 18, 2222.99),
        new Employee("田七", 18, 5555.99),
        new Employee("田七", 18, 5555.99),
        new Employee("田七", 18, 5555.99),
        new Employee("田七", 18, 5555.99),
        new Employee("java", 18, 5555.99)
);
@Test
public void test2(){

    Stream<Employee> stream = emps.stream();
    //中间操作：不会执行任何操作
    Stream<Employee> employeeStream = stream.filter(e -> e.getAge() > 35);
    //终止操作，执行终止操作才会操作中间操作，否则中间操作不会执行
    //一次性执行全部内容，即“惰性求值”
    employeeStream.forEach(System.out::println);
}

@Test
public void test3(){
    emps.stream().filter(e -> {
        System.out.println("短路");
        return e.getSalary() > 5000;
    }).limit(2).forEach(System.out::println);
}

@Test
public void test4(){
    emps.stream().filter(employee -> {
        System.out.println("跳过");
        return employee.getSalary() > 5000;
    }).skip(2).forEach(System.out::println);
}

@Test
public void test5(){
    emps.stream().filter(employee -> employee.getSalary() > 5000)
    .distinct().forEach(System.out::println);
}
```

#### 映射

|方法|描述|
|--|--|
|map(Function f|接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。|
|mapToDouble(ToDoubleFunction f)|接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。|
|mapToInt(ToIntFunction f) |接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。|
|mapToLong(ToLongFunction f) |接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。|
|flatMap(Function f)|接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流|

```java
@Test
public void test6(){
    List<String> list = Arrays.asList("aaa","bbb","ccc","dddd","eeee");
    list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);

    emps.stream().map(Employee::getName).forEach(System.out::println);

    System.out.println("-----------------");

    Stream<Stream<Character>> streamStream = list.stream().map(StreamAPI1::filterCharacter);
    streamStream.forEach(sm -> {
        sm.forEach(System.out::println);
    });

    System.out.println("--------------");
    Stream<Character> characterStream = list.stream().flatMap(StreamAPI1::filterCharacter);
    characterStream.forEach(System.out::println);
}

public static Stream<Character> filterCharacter(String string) {
    List<Character> list = new ArrayList<>();
    for (Character character : string.toCharArray()) {
        list.add(character);
    }
    return list.stream();
}
```

#### 排序

|方法|描述|
|--|--|
|sorted() |产生一个新流，其中按自然顺序排序|
|sorted(Comparator comp) |产生一个新流，其中按比较器顺序排序|

```java
@Test
public void test7(){
    List<String> list = Arrays.asList("aaa","bbb","ccc","dddd","eeee");
    list.stream().sorted().forEach(System.out::println);

    emps.stream().sorted((a, b) -> a.getAge() == b.getAge() ? a.getName().compareTo(b.getName()) : a.getAge() - b.getAge())
    .forEach(System.out::println);
}
```

### 终止操作

终端操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如：List、Integer，甚至是 void 。

#### 查找与匹配

|方法|描述|
|--|--|
|allMatch(Predicate p) |检查是否匹配所有元素|
|anyMatch(Predicate p) |检查是否至少匹配一个元素|
|noneMatch(Predicate p) |检查是否没有匹配所有元素|
|findFirst() |返回第一个元素|
|findAny() |返回当前流中的任意元素|
|count() |返回流中元素总数|
|max(Comparator c) |返回流中最大值|
|min(Comparator c)| 返回流中最小值|
|forEach(Consumer c) |内部迭代(使用 Collection 接口需要用户去做迭代，称为外部迭代。相反，Stream API 使用内部迭代——它帮你把迭代做了)|
