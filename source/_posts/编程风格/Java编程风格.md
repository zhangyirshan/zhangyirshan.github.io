---
title: Java编程风格
p: 编程风格/Java编程风格
date: 2019-12-12 17:51:30
tags: 编程风格
categories: [编程风格,Java编程风格]
---

## 实体类编写规范

```java
/**
 * 通过卷宗主键和创建人Id获得批注列表，以最后修改时间升序排列
 * @return 批注实体列表
 * @author dingjsh
 * time 2018年1月28日上午10:56:08
 * @version 4.0.0
 */
@ApiOperation(value = "根据卷宗Id和创建人id查询批注", notes = "用于iframe的pdf渲染批注位置")
@GetMapping(value = "/iframe/{caseId}", produces = "application/json;charset=UTF-8")
@ApiResponses({ @ApiResponse(code = 200, message = "操作成功, 返回数据."),
@ApiResponse(code = 400, message = "参数不正确"), })
public ResponseEntity<List<JzpzEntity>> getPzForPdf(@PathVariable(name = "caseId") @ApiParam(value = "caseId", required = true) String caseId) {}
```

@ApiOperation是批注类，用在swagger上。
@GetMapping不仅要设置value还要设置produces属性,并根据程序功能进行修改

## 工具类编写规范

```java
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SortUtil
```

@NoArgsConstructor(access = AccessLevel.PRIVATE) lombok中用来生产私有构造方法的注解
工具类不会被继承也不会被声明实例对象，因此要用final修饰，是为了防止其他程序员不小心声明对象浪费内存。

## 判断语句

如果只有两种情况，那就用if/else，就算else可以省略也不要省略，这样为了方便代码阅读者理清的思维逻辑，可以更加快速的读懂你的代码

## 循环编写

如果集合中的数据量在1w以下，用普通的循环遍历即可，如果在1w以上，不考虑线程安全问题使用并行流parallelStream，考虑线程安全问题使用stream
