---
title: 新时间API
p: 后端/Java/Java8新特性/新时间API
date: 2019-12-12 15:33:36
tags: Java
categories: [Java,Java8新特性]
---
## 新的时间API特点

> 线程安全

## 使用 LocalDate、LocalTime、LocalDateTime

- LocalDate、LocalTime、LocalDateTime 类的实例是不可变的对象，分别表示使用 ISO-8601日历系统的日期、时间、日期和时间。它们提供了简单的日期或时间，并不包含当前的时间信息。也不包含与时区相关的信息。

注：ISO-8601日历系统是国际标准化组织制定的现代公民的日期和时间的表示法
{% asset_img 常用时间方法.png 常用时间方法%}

## Instant 时间戳

用于“时间戳”的运算。它是以Unix元年(传统的设定为UTC时区1970年1月1日午夜时分)开始所经历的描述进行运算

## Duration 和 Period

- Duration:用于计算两个“时间”间隔
- Period:用于计算两个“日期”间隔

## 日期的操纵

- TemporalAdjuster : 时间校正器。有时我们可能需要获取例如：将日期调整到“下个周日”等操作。
- TemporalAdjusters : 该类通过静态方法提供了大量的常用 TemporalAdjuster 的实现。
例如获取下个周日：`LocalDate nextSunday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));`

## 解析与格式化

java.time.format.DateTimeFormatter 类：该类提供了三种格式化方法：
    - 预定义的标准格式
    - 语言环境相关的格式
    - 自定义的格式

## 时区的处理

Java8 中加入了对时区的支持，带时区的时间为分别为：
ZonedDate、ZonedTime、ZonedDateTime
其中每个时区都对应着 ID，地区ID都为 “{区域}/{城市}”的格式
例如 ：Asia/Shanghai 等
ZoneId：该类中包含了所有的时区信息
getAvailableZoneIds() : 可以获取所有时区时区信息
of(id) : 用指定的时区信息获取 ZoneId 对象

## 与传统日期处理的转换

{% asset_img 时间转换.png 时间转换%}

```java
//DateTimeFormatter:格式化时间/日期
@Test
public void test5() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
    LocalDateTime l = LocalDateTime.now();
    String strDate = l.format(dateTimeFormatter);
    System.out.println(strDate);
    DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
    System.out.println(l.format(dateTimeFormatter1));
    LocalDateTime of = LocalDateTime.parse(l.format(dateTimeFormatter1), dateTimeFormatter1);
    System.out.println(of);
}
```
