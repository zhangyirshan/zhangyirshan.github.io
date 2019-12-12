---
title: Optional类
p: 后端/Java/Java8新特性/Optional类
date: 2019-12-12 09:13:51
tags: Java
categories: [Java,Java8新特性]
---
## Optional 类

> Optional\<T> 类(java.util.Optional) 是一个容器类，代表一个值存在或不存在，原来用 null 表示一个值不存在，现在 Optional 可以更好的表达这个概念。并且可以避免空指针异常。

常用方法：
Optional.of(T t) : 创建一个 Optional 实例
Optional.empty() : 创建一个空的 Optional 实例
Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
isPresent() : 判断是否包含值orElse(T t) : 如果调用对象包含值，返回该值，否则返回
torElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
flatMap(Function mapper):与 map 类似，要求返回值必须是Option

```java
public class TestOptional {
    @Test
    public void test1(){
        Optional<Employee> employee = Optional.of(new Employee());
        Optional<Employee> employee1 = Optional.of(null);
        Employee emp = employee.get();
        System.out.println(emp);
    }
    // 快速定位空指针异常位置

    @Test
    public void test2(){
        Optional<Employee> employee = Optional.empty();
        System.out.println(employee.get());
    }
    // 快速定位空指针异常位置

    @Test
    public void test3(){
//        Optional<Employee> o = Optional.ofNullable(new Employee());
        Optional<Employee> o = Optional.ofNullable(null);
//        if (o.isPresent()) {
//            System.out.println(o.get());
//        }
//        Employee employee = o.orElse(new Employee("张三", 19, 29393, Employee.Status.FREE));
//        System.out.println(employee);

        Employee employee1 = o.orElseGet(() -> new Employee("李四", 19, 29393, Employee.Status.FREE));
        System.out.println(employee1);
    }
    //Employee{name='李四', age=19, salary=29393.0, Status=FREE}

    @Test
    public void test4(){
        Optional<Employee> op = Optional.ofNullable(new Employee("张三", 19, 29393, Employee.Status.FREE));
//        Optional<String> s = op.map(e -> e.getName());
//        System.out.println(s.get());
        Optional<String> s2 = op.flatMap(e -> Optional.of(e.getName()));
        System.out.println(s2.get());
    }
    //张三
}
```
