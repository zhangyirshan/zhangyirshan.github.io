---
title: python基础
p: 后端/python/python基础
date: 2020-05-19 21:17:10
tags: python
categories: python
---
## 变量

1. 在使用变量之前，需要对其赋值
2. 变量名可以报考字母、数字、下划线，但变量名不能以数字开头

### 字符串

#### 原始字符串

原始字符串的使用非常简单，只需要在字符串前加一个字母r即可，**注意，原始字符串不能在结尾放\会报错**

```python
str1 = r'C:\now'
str2 = r'C:\now\fish\a\'
会报错
```

##### 解决办法

1. 在后面拼接`str = r'C:\new\aaa\bbb'+'\\'`
2. 在后面加上[:-1] `str = r'C:\new\aaa\bbb\\'[:-1]`

#### 长字符串

如果想得到一个跨越多行的字符串，我们就需要使用三重引号`"""dsdssdsds"""`

#### 字符串的分片操作

```python
str1 = "I love you"
str1[:6] => 'I love'
```

#### 字符串的方法

```python
s = 'Wusirs22'
t1 = s.count('a') # 在字符串中查找指定字符有多少个
t2 = s.upper() # 将字符串中的字符全部大写
t3 = s.lower() # 将字符串中的字符全部小写
t4 = s.title() # 非字母隔开的每个单词的首字母大写
t5 = s.find('a') # 通过元素获取其索引,找到第一个就返回,找不到会返回-1。
t6 = s.index('a') # 通过元素获取其索引,找到第一个就返回，找不到会报错。
t7 = s.capitalize() # 将字符串中的首字母大写
t8 = s.replace('a','nb',2) # 替换,由什么替换成什么,替换几次
t9 = s.startswith('w') # 判断以什么为开头 可以切片
t10 = s.startswith('i',3,) # 判断以什么为开头 可以切片,取索引3后面的所有字符
t11 = s.endswith('i',3) # 判断以什么结尾，可以切片,取索引3后面的所有字符
t12 = s.swapcase() # 大小写反转
t13 = s.isupper() # 判断字符串中所有字母是否为大写，返回布尔值
t14 = s.islower() # 判断字符串中所有字母是否为小写，返回布尔值
t15 = s.isdigit() # 字符串只由数字组成
t16 = s.isalpha() # 字符串只由字母组成
t17 = s.isalnum() # 字符串由字母或数字组成
---------------------------------------------------------------
t18 = s.strip('W') # 默认去除字符串两边的空格，换行符，制表符,只是去除左边-->lstrip()去除右边-->rstrip(),可设置去除的字符
t19 = s.split() # 默认按照空格分割,
t20 = s.split('r') # 指定字符进行分割,
t21 = s.split('s',2) # 指定字符进行分割,且指定分割次数
---------------------------------------------------------------
join 连接符
l1 = ['wusir', 'alex', 'taibai'] # 操作列表时，列表里面的内容必须全部是字符串类型
t22 = ''.join(l1) # 默认连接
t23 = '-'.join(l1)   # 指定字符连接
---------------------------------------------------------------
字符串的格式化输出
第一种情况
m = '姓名:{} 年龄:{} 性别:{}'
w = m.format('烟雨江南',20,'男')
print(w)
第二种情况
m = '姓名:{0} 年龄:{1} 性别:{2} 我永远{1}岁'
w = m.format('烟雨江南',18,'男')
print(w)
第三种情况
m = '姓名:{name} 年龄:{age} 性别:{sex} 我永远{age}岁'
w = m.format(name = '烟雨江南',age = 18,sex = '男')
print(w)
```

### 整型

### 浮点型

e记法：科学计数法

```python
15000 == 1.5e4
0.001 == 1e-3
```

### 布尔类型

true为1，false为0

## 类型转换

```python
int('222')
int(3.2) == 3

float(520) == 520.0
float('23.4') = 23.4

str(5e2) == '5e+2'
```

## 类型检测

```python
type(5e0) -> float
type('ewew') -> str

isinstance(333,int) -> true
isinstance(false,bool) -> true
```

## 条件分支

```python
if 条件 :
    条件为真执行的操作
elif 条件 :
    条件为真执行的操作
else:
    条件为假执行的操作
```

## 循环

```python
while 条件 :
    条件为真执行的操作

for 目标 in 表达式:
    循环体
```

### range()

语法：range([start,] stop[, step=1])

- 这个BIF有三个参数，其中中括号括起来的两个表示可选
- step=1表示第三个参数默认为1
- range这个BIF的作用是生成一个从start参数的值开始到stop参数的值结束的数字序列

语法：range

## 操作符

### 算数操作符

```python
3 / 2 = 1.5
3 // 2 = 1

- True = -1

** 幂运算操作符
3 ** 2 == 9
```

### 逻辑运算符

```python
not 4 = False
```

### 优先级问题

幂运算 > 正负号 > 算数运算符 > 比较运算符 > 逻辑运算符

### 三元操作符

```python
small = x if x > y else y
如果x>y就将x给small否则将y给small
```

## 断言

assert这个关键字我们称之为断言，当这个关键字后边的条件为假的时候，程序自动崩溃并跑出AssertionError的异常

## 列表(list)

### 向列表中添加元素

append()向列表后添加1个元素

```python
member = [1,2,3]
member.append(1)
```

extend()向列表后追加列表

```python
member = [1,2,3]
member.extend([1,2,3])
member = [1,2,3,1,2,3]
```

insert()向列表中插入1个元素

```python
member = [1,2,3]
member.insert(1,"哈哈")
member = [1,"哈哈",2,3]
```

### 向列表中删除元素

remove()删除某一个元素，需要知道元素名，如果该数组中不存在该元素，会报错

```python
member = [1,"哈哈",2,3]
member.remove("哈哈")
member = [1,2,3]
```

`del 列表` 删除该列表,del的删除只是清除member元素内的索引值，也就是说member2 = member后del.member,member2依然有值，但是remove一个元素后悔影响memebr2的显示，因为他们指向同一处内存。

```python
member = [1,"哈哈",2,3]
del member
再次输出member会报错，需要再次声明
```

pop()默认删除列表的最后一个元素，括号中可以填写要删除的索引值，**有返回值**

```python
member = [1,"哈哈",2,3]
member.pop(2)
如果索引值大于数组长度会报错
```

### 列表分片（Slice）

- 利用索引值，每次我们可以从列表获取一个元素，但是我们总是贪心的，如果一次性需要获取多个元素，有没有办法实现呢？
    利用列表分片，我们可以简单的实现这个要求。

列表[开始索引:结束索引] 分片的列表并不会影响原列表，是原列表的拷贝，列表从开始索引到结束索引的前一位

```python
member = [1,"哈哈",2,3]
member[1:4] = ['哈哈', 2, 3]
member[1:] = ['哈哈', 2, 3]
member[:3] = [1, '哈哈', 2]

member2 = member[:]这是将memebr的值拷贝给member2，操作memeber不会影响memeber2

```

### 列表的常用操作符

#### 列表的比较

```python
list1 = [123]
list2 = [456]
list1 < list2 =>返回True
```

当列表中有多个元素，只比较第一个

#### 列表的加法

```python
list1 = [123]
list2 = [456]
list1 + list2 =>返回[123,456]
```

但是，列表的加法不能与非列表类型元素相加，也就是说list1+'123'是错误的，但是list1+[123]是正确的

#### 列表的乘法

```python
list1 = [123] * 2
list1 =>返回[123,123]
```

#### 列表的成员操作符

```python
list1 = [123,455,'455',[556,755]]
'455' in list1 =>返回True
556 in list1 =>返回False
556 in list1[3] =>返回True
```

### 列表方法

#### count()

统计参数在列表中的数量

```python
list1 = [123,455,'455',[556,755,123],123]
list1.count(123) => 2
```

#### index()

```python
list1 = [123,455,'455',[556,755,123],123]
list1.index(123,2,4) => 2

list1.index(123,1,5) => 4
list1.index(123,1,4) => not in list1
```

#### reverse()

反转列表

#### sort()

对列表进行从小到大的排序
有一个参数list1.sort(reverse=True)，设置后变成从大到小排序

第二个参数是起始位置，第三个参数是终止位置，范围包含起始位置但不包含终止位置

## 元组(typle)：带上了枷锁的列表

- 由于和列表是近亲关系，所以元组和列表在实际使用上是非常相似的。

### 创建和访问元组

创建列表使用中括号，但是创建元组使用小括号

```python
tuple1 = (1,2,3,4,5,6)
tuple1[5:] => 6
```

但是**只使用小括号创建的也有可能不是元组**

```python
tuple1 = (1)
type(tuple1) => class 'int'
```

如果你想创建的元组只有一个元素的话，必须在后面加上逗号

```python
tuple1 = (1,)
type(tuple1) => class 'tuple'
```

### 更新元组



### 时间复杂度

|Operation |Big-O Efficiency|
|-|-|
|index [] |O(1)|
|index assignment |O(1)|
|append |O(1)|
|pop() |O(1)|
|pop(i) |O(n)|
|insert(i,item) |O(n)|
|del operator |O(n)|
|iteration |O(n)|
|contains (in) |O(n)|
|get slice [x:y] |O(k)|
|del slice |O(n)|
|set slice |O(n+k)|
|reverse |O(n)|
|concatenate| O(k)|
|sort |O(n log n)|
|multiply |O(nk)|

## 常用函数

> dir(对象),返回这个对象可以调用的方法
