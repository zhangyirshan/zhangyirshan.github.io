---
title: Stream
p: 后端/Java/Java8新特性/Stream
date: 2019-12-10 10:09:16
tags: Java
categories: [Java,Java8新特性]
---
## 什么是 Stream

Java8中有两大最为重要的改变。第一个是 Lambda 表达式；另外一个则是 **Stream API(java.util.stream.*)**。
Stream 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。使用Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数据库查询。也可以使用 Stream API 来并行执行操作。简而言之，Stream API 提供了一种高效且易于使用的处理数据的方式。

流(Stream) 到底是什么呢？
是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。
**“集合讲的是数据，流讲的是计算！”**
注意：
    1. Stream 自己不会存储元素。
    2. Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。
    3. Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。

## Stream 的操作三个步骤

- 创建 Stream
    一个数据源（如：集合、数组），获取一个流
- 中间操作
    一个中间操作链，对数据源的数据进行处理
- 终止操作(终端操作)
    一个终止操作，执行中间操作链，并产生结果

{% asset_img Stream流.png Stream流%}

## 创建Stream

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

## Stream 的中间操作

多个**中间操作**可以连接起来形成一个**流水线**，除非流水线上触发终止操作，否则**中间操作不会执行任何的处理！而在终止操作时一次性全部处理，称为“惰性求值”。**

### 筛选与切片

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

### 映射

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

### 排序

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

## 终止操作

终端操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如：List、Integer，甚至是 void 。

### 查找与匹配

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

```java
import java.util.Objects;

public class Employee {
    private String name;
    private int age;
    private double salary;
    private Status Status;


    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, int age, double salary, Employee.Status status) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        Status = status;
    }

    public Employee.Status getStatus() {
        return Status;
    }

    public void setStatus(Employee.Status status) {
        Status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return age == employee.age &&
                Double.compare(employee.salary, salary) == 0 &&
                Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, salary);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", Status=" + Status +
                '}';
    }

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public enum Status{
        FREE,
        BUSY,
        VOCATION;
    }
}
```

```java

List<Employee> emps = Arrays.asList(
        new Employee("张三", 42, 9999.99, Employee.Status.BUSY),
        new Employee("李四", 59, 6669.99, Employee.Status.FREE),
        new Employee("王五", 18, 3333.99, Employee.Status.BUSY),
        new Employee("赵六", 15, 2222.99, Employee.Status.VOCATION),
        new Employee("田七", 12, 5555.99, Employee.Status.VOCATION),
        new Employee("田七", 13, 5555.99, Employee.Status.VOCATION),
        new Employee("田七", 18, 5555.99, Employee.Status.FREE),
        new Employee("田七", 18, 5555.99, Employee.Status.BUSY),
        new Employee("java", 21, 5555.99, Employee.Status.BUSY)
);

@Test
public void test8(){
    boolean b1 = emps.stream().allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
    boolean b2 = emps.stream().anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
    boolean b3 = emps.stream().noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
    System.out.println(b1);
    System.out.println(b2);
    System.out.println(b3);

    Optional<Employee> first = emps.stream().sorted((e1, e2) -> -Double.compare(e1.getSalary(), e2.getSalary())).findFirst();
    System.out.println(first.get());

    Optional<Employee> any = emps.parallelStream().filter(e -> e.getStatus().equals(Employee.Status.FREE)).findAny();
    System.out.println(any.get());
}

@Test
public void test9(){
    long count = emps.stream().count();
    System.out.println(count);

    Optional<Employee> max = emps.stream().max(Comparator.comparingDouble(Employee::getSalary));
    Optional<Double> min = emps.stream().map(Employee::getSalary).min(Double::compareTo);
    System.out.println(max.get());
    System.out.println("salary" + min);
}
```

### 归约

|方法|描述|
|--|--|
|reduce(T iden, BinaryOperator b)|可以将流中元素反复结合起来，得到一个值。返回 T|
|reduce(BinaryOperator b)|可以将流中元素反复结合起来，得到一个值。返回 Optional\<T>|

> 备注：map 和 reduce 的连接通常称为 map-reduce 模式，因 Google 用它来进行网络搜索而出名。

```java
@Test
public void test10(){
    List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    Integer sum = list.stream().reduce(0, Integer::sum);
    System.out.println(sum);

    System.out.println("-------------");

    Optional<Double> reduce = emps.stream().map(Employee::getSalary).reduce(Double::sum);
    System.out.println(reduce.get());
}
```

### 收集

|方法|描述|
|--|--|
|collect(Collector c) |将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法|

Collector 接口中方法的实现决定了如何对流执行收集操作(如收集到 List、Set、Map)。但是 Collectors 实用类提供了很多静态方法，可以方便地创建常见收集器实例，具体方法与实例如下表：

|方法 |返回类型 |作用|举例|
|-----|--------|---|----|
|toList |List\<T> |把流中元素收集到List|List\<Employee> emps= list.stream().collect(Collectors.toList());|
|toSet |Set\<T>| 把流中元素收集到Set|Set\<Employee> emps= list.stream().collect(Collectors.toSet());|
|toCollection |Collection\<T>|把流中元素收集到创建的集合|Collection\<Employee>emps=list.stream().collect(Collectors.toCollection(ArrayList::new));|
|counting  |Long|计算流中元素的个数|long count = list.stream().collect(Collectors.counting());|
|summingInt|Integer|对流中元素的整数属性求和|inttotal=list.stream().collect(Collectors.summingInt(Employee::getSalary));|
|averagingInt |Double|计算流中元素Integer属性的平均值|doubleavg= list.stream().collect(Collectors.averagingInt(Employee::getSalary));|
|summarizingInt|IntSummaryStatistics|收集流中Integer属性的统计值。如：平均值|IntSummaryStatisticsiss= list.stream().collect(Collectors.summarizingInt(Employee::getSalary));|
|joining|String|连接流中每个字符串|String str= list.stream().map(Employee::getName).collect(Collectors.joining());|
|maxBy|Optional\<T>|根据比较器选择最大值|Optional<Emp>max= list.stream().collect(Collectors.maxBy(comparingInt(Employee::getSalary)));|
|minBy|Optional\<T>|根据比较器选择最小值|Optional<Emp> min = list.stream().collect(Collectors.minBy(comparingInt(Employee::getSalary)));|
|reducing|归约产生的类型|从一个作为累加器的初始值开始，利用BinaryOperator与流中元素逐个结合，从而归约成单个值|inttotal=list.stream().collect(Collectors.reducing(0, Employee::getSalar, Integer::sum));|
|collectingAndThen |转换函数返回的类型|包裹另一个收集器，对其结果转换函数|inthow= list.stream().collect(Collectors.collectingAndThen(Collectors.toList(), List::size));|
|groupingBy|Map\<K, List\<T>>|根据某属性值对流分组，属性为K，结果为V|Map<Emp.Status, List\<Emp>> map= list.stream().collect(Collectors.groupingBy(Employee::getStatus));|
|partitioningBy |Map<Boolean, List\<T>>|根据true或false进行分区|Map<Boolean,List<Emp>>vd= list.stream().collect(Collectors.partitioningBy(Employee::getManage));|

```java
@Test
public void test11(){
    List<String> collect = emps.stream().map(Employee::getName).distinct().collect(Collectors.toList());
    collect.forEach(System.out::println);
    System.out.println("---------------");
    Set<String> collect1 = emps.stream().map(Employee::getName).collect(Collectors.toSet());
    collect1.forEach(System.out::println);
    System.out.println("---------------");
    HashSet<String> collect2 = emps.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
    collect2.forEach(System.out::println);
    System.out.println("---------------");
    Double collect3 = emps.stream().collect(Collectors.averagingDouble(Employee::getSalary));
    System.out.println(collect3);
    //分组
    Map<Employee.Status, List<Employee>> collect4 = emps.stream().collect(Collectors.groupingBy(Employee::getStatus));
    System.out.println(collect4);
    //多级分组
    System.out.println("--------------");
    Map<Employee.Status, Map<String, List<Employee>>> collect5 = emps.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
        if (e.getAge() <= 20) {
            return "青年";
        } else if (e.getAge() <= 40) {
            return "中年";
        } else {
            return "老年";
        }
    })));
    System.out.println(collect5);
    //分区
    System.out.println("--------------");
    Map<Boolean, List<Employee>> collect6 = emps.stream().collect(Collectors.partitioningBy(e -> e.getSalary() > 7000));
    System.out.println(collect6);
    System.out.println("--------------");
    DoubleSummaryStatistics collect7 = emps.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
    System.out.println(collect7.getMax());
    System.out.println("--------------");
    String collect8 = emps.stream().map(Employee::getName).collect(Collectors.joining(",","[","]"));
    System.out.println(collect8);
}

结果为：
张三
李四
王五
赵六
田七
java
---------------
李四
张三
java
王五
赵六
田七
---------------
李四
张三
java
王五
赵六
田七
---------------
5556.323333333333
{VOCATION=[Employee{name='赵六', age=15, salary=2222.99, Status=VOCATION}, Employee{name='田七', age=12, salary=5555.99, Status=VOCATION}, Employee{name='田七', age=13, salary=5555.99, Status=VOCATION}], BUSY=[Employee{name='张三', age=42, salary=9999.99, Status=BUSY}, Employee{name='王五', age=18, salary=3333.99, Status=BUSY}, Employee{name='田七', age=18, salary=5555.99, Status=BUSY}, Employee{name='java', age=21, salary=5555.99, Status=BUSY}], FREE=[Employee{name='李四', age=59, salary=6669.99, Status=FREE}, Employee{name='田七', age=18, salary=5555.99, Status=FREE}]}
--------------
{VOCATION={青年=[Employee{name='赵六', age=15, salary=2222.99, Status=VOCATION}, Employee{name='田七', age=12, salary=5555.99, Status=VOCATION}, Employee{name='田七', age=13, salary=5555.99, Status=VOCATION}]}, BUSY={青年=[Employee{name='王五', age=18, salary=3333.99, Status=BUSY}, Employee{name='田七', age=18, salary=5555.99, Status=BUSY}], 老年=[Employee{name='张三', age=42, salary=9999.99, Status=BUSY}], 中年=[Employee{name='java', age=21, salary=5555.99, Status=BUSY}]}, FREE={青年=[Employee{name='田七', age=18, salary=5555.99, Status=FREE}], 老年=[Employee{name='李四', age=59, salary=6669.99, Status=FREE}]}}
--------------
{false=[Employee{name='李四', age=59, salary=6669.99, Status=FREE}, Employee{name='王五', age=18, salary=3333.99, Status=BUSY}, Employee{name='赵六', age=15, salary=2222.99, Status=VOCATION}, Employee{name='田七', age=12, salary=5555.99, Status=VOCATION}, Employee{name='田七', age=13, salary=5555.99, Status=VOCATION}, Employee{name='田七', age=18, salary=5555.99, Status=FREE}, Employee{name='田七', age=18, salary=5555.99, Status=BUSY}, Employee{name='java', age=21, salary=5555.99, Status=BUSY}], true=[Employee{name='张三', age=42, salary=9999.99, Status=BUSY}]}
--------------
9999.99
--------------
[张三,李四,王五,赵六,田七,田七,田七,田七,java]
```

## Stream API练习

```java
//交易员类
public class Trader {

    private String name;
    private String city;

    public Trader() {
    }

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader [name=" + name + ", city=" + city + "]";
    }

}
```

```java
//交易类
public class Transaction {

    private Trader trader;
    private int year;
    private int value;

    public Transaction() {
    }

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction [trader=" + trader + ", year=" + year + ", value="
                + value + "]";
    }

}

```

```java
public class TestTransaction {

    List<Transaction> transactions = null;

    @Before
    public void before(){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    //1. 找出2011年发生的所有交易， 并按交易额排序（从低到高）
    @Test
    public void test1(){
        transactions.stream().filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                //.sorted((v1,v2)->v2.getValue() - v1.getValue())   从高到低
                .forEach(System.out::println);
    }

    //2. 交易员都在哪些不同的城市工作过？
    @Test
    public void test2(){
        transactions.stream().map(transaction -> transaction.getTrader().getCity())
                .distinct().forEach(System.out::println);
    }

    //3. 查找所有来自剑桥的交易员，并按姓名排序
    @Test
    public void test3(){
        transactions.stream().map(Transaction::getTrader).filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName)).distinct()
                .forEach(System.out::println);
    }

    //4. 返回所有交易员的姓名字符串，按字母顺序排序
    @Test
    public void test4(){
        transactions.stream().map(transaction -> transaction.getTrader().getName())
                .sorted().forEach(System.out::println);
        System.out.println("----------------");
        String collect = transactions.stream().map(transaction -> transaction.getTrader().getName())
                .sorted().distinct().collect(Collectors.joining(",", "[", "]"));
        System.out.println(collect);
        System.out.println("----------------");
        transactions.stream().map(transaction -> transaction.getTrader().getName()).flatMap(TestTransaction::filterCharacter)
                .sorted(String::compareToIgnoreCase).forEach(System.out::print);
    }

    public static Stream<String> filterCharacter(String str){
        List<String> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch.toString());
        }

        return list.stream();
    }

    //5. 有没有交易员是在米兰工作的？
    @Test
    public void test5(){
        boolean milan = transactions.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(milan);
    }


    //6. 打印生活在剑桥的交易员的所有交易额
    @Test
    public void test6(){
        Integer cambridge = transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue).reduce(0, Integer::sum);
        Integer cambridge1 = transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.summingInt(Transaction::getValue));
        Integer cambridge2 = transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .mapToInt(Transaction::getValue).sum();

        System.out.println(cambridge);
        System.out.println(cambridge1);
        System.out.println(cambridge2);
    }


    //7. 所有交易中，最高的交易额是多少
    @Test
    public void test7(){
        Optional<Integer> max = transactions.stream().map(Transaction::getValue).max(Integer::compare);
        System.out.println(max.orElse(0));
    }

    //8. 找到交易额最小的交易
    @Test
    public void test8(){
        Optional<Transaction> min = transactions.stream().min(Comparator.comparingInt(Transaction::getValue));
        System.out.println(min.get());
    }
}
```
