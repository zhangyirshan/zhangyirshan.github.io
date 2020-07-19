---
title: Java的LLambda性能分析
date: 2020-07-13 10:14:10
tags: [性能分析,Java]
categories: [性能分析,Java]
---
## for循环和lambda的性能比较

### 代码

```Java
public class main {
    public static void main(String[] args) {
        List<User> listUser = getListUsers();
        //一般forEach
        long startSimpleTime = System.currentTimeMillis();
        for (User user : listUser) {
            user.toString();
        }
        long endSimpleTime = System.currentTimeMillis();
        System.out.println("Simple:" + (endSimpleTime - startSimpleTime));

        //java8中新的forEach
        long startLambda = System.currentTimeMillis();
        listUser.forEach(User::toString);
        long endLambda = System.currentTimeMillis();
        System.out.println("Lambda:" + (endLambda - startLambda));

        //java8中新的stream+forEach
        long startStream = System.currentTimeMillis();
        listUser.stream().forEach(User::toString);
        long endStream = System.currentTimeMillis();
        System.out.println("Stream:" + (endStream - startStream));

        //java8中新的parallelStream+forEach
        long startParallelStream = System.currentTimeMillis();
        listUser.parallelStream().forEach(User::toString);
        long endParallelStream = System.currentTimeMillis();
        System.out.println("ParallelStream:" + (endParallelStream - startParallelStream));
    }

    private static List<User> getListUsers() {
        List<User> listUser = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            listUser.add(new User("user" + i, i));
        }
        return listUser;
    }

}
```

### 结果

```Java
集合中数量为1
Simple:0
Lambda:55
Stream:2
ParallelStream:4
```

```Java
集合中数量为10
Simple:0
Lambda:38
Stream:2
ParallelStream:4
```

```Java
集合中数量为100
Simple:1
Lambda:47
Stream:2
ParallelStream:5
```

```Java
集合中数量为1000
Simple:2
Lambda:61
Stream:3
ParallelStream:6
```

```Java
集合中数量为10000
Simple:7
Lambda:48
Stream:6
ParallelStream:9
```

```Java
集合中数量为100000
Simple:36
Lambda:76
Stream:13
ParallelStream:9
```

```Java
集合中数量为1000000
Simple:1028
Lambda:131
Stream:93
ParallelStream:45
```

```Java
集合中数量为10000000
Simple:3514
Lambda:621
Stream:642
ParallelStream:381
```

### 结论

在数量级1~10000之间，采用普通的**for循环性能最优**，但是几毫秒的性能差异并不是太影响在性能峰值之外的区域，考虑到代码简洁等其他因素，还是可以采用stream流循环的形式。
在数量级10000~10000000之间，在不考虑线程安全的情况下采用并行流**ParallelStream性能最优**，在单线程的情况下**stream的性能最优**。

**因此无论何时，都不要直接使用xxxList.foreach()的方式来进行循环遍历，在只考虑性能或性能占比较大的情况下推荐使用stream流和ParallelStream流的形式进行遍历**
