---
title: php数组与数据结构
p: 后端/php/php语法基础
date: 2020-06-03 22:15:09
tags: php
categories: php
---
## 数组

### 数组声明

1. 数组可以存入多个不同类型的数据，是一个复合数据类型。
2. 数组的英文是array，学一了一下最简单的数组声明。

```php
$shu = array(1 , 1.5 , true ,'天王盖地虎，小鸡炖蘑菇');

echo '<pre>';
var_dump($shu);
echo '</pre>';

结果是：
array(4) {
  [0]=>
  int(1)
  [1]=>
  float(1.5)
  [2]=>
  bool(true)
  [3]=>
  string(33) "天王盖地虎，小鸡炖蘑菇"
}
```

1. array(size = 4) 说明里面有4个元素
2. 0 => int 1 我们知道int是整型的意思，1是一个整型的数值。那前面的0,1,2,3和=>代表什么意思呢？
3. 最新前的0，1，2，3代表的是值的读取标识号，我们称之为下标或者键（英文：key）
4. => 是一个符号标准叫法叫作：键值对应符。因此，以后再看到 0=> int 1 可以这样来说。 下标访问符0对应整型的1。
5. 我们还称数组里面的键值对为元素，元素就是键值对的组合。

```php
$kele = array(
           '只有不断努力才能博得未来',
           'a' => 'NoAlike',
           'PHP中文网' ,
           '去PHP中文网学PHP',
           19 => '凤姐和芙蓉我都爱' ,
           '杨幂我最爱'
       );


//打印显示$kele
echo '<pre>';
var_dump($kele);
echo '</pre>';

结果是：
array(6) {
  [0]=>
  string(36) "只有不断努力才能博得未来"
  ["a"]=>
  string(7) "NoAlike"
  [1]=>
  string(12) "PHP中文网"
  [2]=>
  string(21) "去PHP中文网学PHP"
  [19]=>
  string(24) "凤姐和芙蓉我都爱"
  [20]=>
  string(15) "杨幂我最爱"
}
```

1. 索引数组若不强制声明他的下标，他的下标是从0开始的。（我们的第一个数组的值：只有不断努力才能博得未来。这个值的下标为0）。
2. 如果我指定过下标他的下标就为我指定的值。如下标为10和下标为19的，都是我指定过的值。
3. 若某个值（如NoAlike），强制指定了下标（下标为10）。在它后面加上的值（PHP中文网），不指定下标的话。他们的下标增长规律为最大值+1。

一、直接用之前未声明的变量，用变量名后面接中括号的方式声明数组。

```php
    //直接写一个变量后面加上中括号，声明变量
    $qi[] = '可口可乐';
    $qi[10] ='百事可乐';
    echo '<pre>';
    var_dump($qi);
    echo '</pre>';
```

二、每次用array()写的太麻烦了，还可以不用写array哟，更简单。

### 增加元素

```php
$minren = array(
           '杨幂',
           '王珞丹',
           '刘亦菲',
           '黄圣依'
       );
//如何向这$minren这个数组中增加元素呢

//猜猜范冰冰的下标是多少？
$minren[] = '范冰冰';

$minren[100] = '范爷';

//它的下标又为几呢？
$minren[] = '李晨';
```

### 删除元素

```php
$minren = array(
           '杨幂',
           '王珞丹',
           '刘亦菲',
           '黄圣依',
           '范冰冰'
       );


//假设我不喜欢：黄圣依，如何将黄圣依给删掉掉呢？

//如果删除掉后范冰冰的下标为多少呢？

//如果在后面再追加一个元素，会填掉：“黄圣依”留下来的空吗？

unset($minren[3]);

$minren[] = '金星';


echo '<pre>';

var_dump($minren);

echo '</pre>';
```

## 遍历数组

### ​foreach遍历关联数组

```php
foreach( 要循环的数组变量 as [键变量 =>] 值变量){
//循环的结构体
}
```

### list、each函数遍历数组

list ( mixed $变量1 [, mixed $变量n ] )

```php
<?php
list($one, $two, $three) = array(2 => '张三', '李四', '王五');
echo '$one----' . $one . '<br />';
echo '$two----' . $two . '<br />';
echo '$three----' . $three . '<br />';
?>
$one----
$two----
$three----张三
```

1. 因为是一一对应原则，$one找不到下标为0的数组元素，$two找不到下标为1的数组元素，只有$three找到了下标为2的数组元素
2. 在list($one, $two, $three)，我只写了三个变量。对应完成，无需再对应后面的变量了，丢弃李四和王五。

array each ( array &$array )

```php
<?php

//定义一个变量叫$kongjie(空姐)
$kongjie=[
   'gao'=>'穿黑衣服的',
   'shou'=>'退特别长特别细',
   'mei'=>'好白',
   ];

//第一次each
$data = each($kongjie);

echo '<pre>';
var_dump($data);
echo '</pre>';

echo '-----华丽丽分割线------<br />';


//第2次each
$data = each($kongjie);

echo '<pre>';
var_dump($data);
echo '</pre>';

echo '-----华丽丽分割线------<br />';

//第3次each【执行到了最后一个元素了】
$data = each($kongjie);

echo '<pre>';
var_dump($data);
echo '</pre>';

echo '-----华丽丽分割线------<br />';

//第4次【此时，后面已没有可操作的元素了，看返回什么】
$data = each($kongjie);

echo '<pre>';
var_dump($data);
echo '</pre>';

echo '-----华丽丽分割线------<br />';

?>

array(4) {
  [1]=>
  string(15) "穿黑衣服的"
  ["value"]=>
  string(15) "穿黑衣服的"
  [0]=>
  string(3) "gao"
  ["key"]=>
  string(3) "gao"
}

-----华丽丽分割线------

array(4) {
  [1]=>
  string(21) "退特别长特别细"
  ["value"]=>
  string(21) "退特别长特别细"
  [0]=>
  string(4) "shou"
  ["key"]=>
  string(4) "shou"
}

-----华丽丽分割线------

array(4) {
  [1]=>
  string(6) "好白"
  ["value"]=>
  string(6) "好白"
  [0]=>
  string(3) "mei"
  ["key"]=>
  string(3) "mei"
}

-----华丽丽分割线------

bool(false)

-----华丽丽分割线------

```

1. 读一次向后移动一次【可以想象有一个记录的箭头在移动】，将其中的每个元素拆解成一个新数组。
2. 读取到最后，没有可操作的元素了，所以返回了false。

### list、each配合使用

```php
<?php

//定义一个变量叫$kongjie(空姐)
$kongjie=[
   'gao'=>'穿黑衣服的',
   'shou'=>'腿特别长特别细',
   'mei'=>'好白',
   ];

list($key,$value) = each($kongjie);

echo $key. '-----' .$value .'<br />';

?>

gao-----穿黑衣服的

<?php

//定义一个变量叫$kongjie(空姐)
$kongjie=[
   'gao'=>'穿黑衣服的',
   'shou'=>'退特别长特别细',
   'mei'=>'好白',
   ];

while(list($key,$value) = each($kongjie)){

   echo $key. '-----' .$value .'<br />';

}

?>
gao-----穿黑衣服的
shou-----退特别长特别细
mei-----好白
```

## PHP中的替代语法

PHP中具体有哪些替代语法呢？
PHP应该在HTML中制作数据输出，输出通常伴有条件判断和循环操作，因此PHP提供了对应分支结构和循环结构的替代语法：全部都是对应的一个模式：
左大括号{使用冒号替代:
有大括号}使用end+对应的起始标记替代

```php
if,switch,for,while,foreach
都可以进行替代
```
