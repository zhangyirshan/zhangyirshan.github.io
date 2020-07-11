---
title: php文件包含
date: 2020-06-22 16:30:35
tags: php
categories: php
---
## 文件包含

> 定义：文件包含，就是在一个要运行的PHP脚本中，去将另外一个PHP脚本中的代码拿过来，并且可以使用其被包含的文件里的内容，或者说将自己的内容能够在另外一个被包含的文件中使用。

1. 文件包含基本语法：PHP中提供了四种文件包含的方法，分别是include和include_once，require和require_once，其中四种方式的用法完全一样
    `include/include_once/require/equire_once '文件所在路径及文件名';`
    `include/include_once/require/equire_once('文件所在路径及文件名');`
2. 文件包含的意义：文件包含的目的有两个
    - 向上包含：即先包含某个文件，目的为了使用某个文件中的代码或者数据（使用公共代码）
    - 向下包含：即先写好代码，后包含文件，目的是为了在被包含文件中使用当前的数据（使用已产生数据）
3. 文件包含的语法区别：四种包含方式都能够包含文件并使用
    include和require的区别在于，如果包含的文件不存在的时候，include只是报警告错误，而不影响自身代码执行；而require会报致命错误，而且中断代码执行
    include和**include_once**区别：include不论如何都会执行包含操作，而include_once会记录是否已经包含过对应文件，对同一文件多次包含只操作一次（对于函数/类这种结构不允许重复的，是个好方法）。
4. 文件包含原理：文件包含本质就是将被包含文件的所有代码，在进行包含操作那一行全部引入并运行。但是文件包含语句是在运行时才会执行，因此不能先访问被包含文件中的内容，后包含文件。

## php中require，include，use的区别

1. require，include都是导入文件，但是require如果找不到文件，直接error，程序退出；include是warning，继续执行；
2. use是使用命名空间，相当于java中的导包，前提是包中的文件需要提前require或者include进来。
3. namespace命名空间，相当于java中的package，定义一个包
4. use使用的时候后面的需要写全空间名+类名 ，例如命名空间Person1/Person; 其中Person1是命名空间，Person是类名；

```php
use Sdbean\Helpers\Controller;
use Sdbean\Service\GetHallBannerService;

include_once("../tools/toolIndex.php");
```

### 文件加载原理

PHP代码的执行流程

1. 读取代码（PHP程序）
2. 编译：将PHP代码转换成字节码（生成opcode）
3. zendengine来解析opcode，按照字节码去进行逻辑运算
4. 转换成对应的HTML代码

文件加载原理：

1. 当文件加载（include或者require）的时候，系统会自动的将被包含文件中的代码相当于嵌入到当前文件中
2. 加载位置：在哪加载，对应的文件中的代码嵌入的位置就是对应的include位置
3. 在PHP中被包含的文件时单独进行编译的

PHP文件在编译的过程中如果出现了语法错误，那么会失败（不会执行）；但是如果被包含文件有错误的时候，系统会在执行到包含include这条语句对的时候才会报错。

### include和require区别

include和include_once区别：
include系统会碰到一次，执行一次；如果对同一个文件进行多次加载，那么系统会执行多次；
include_once：系统碰到措辞，也只会执行一次

```php
include1.php

$a = 1;
define('PI',3.14);

include2.php

// 包含文件
include 'include1.php'; // 包含当前文件include2.php所在文件夹下的include1.php
echo $a,PI;
// 再次包含
include 'include1.php';

13.14
报错：PI常量已经存在
```

include的错误级别比较轻：不会阻止代码执行
require要求较高：如果包含出错代码不再执行（require后面的代码）

### 文件加载路径

文件在加载的时候需要指定文件路径才能保证PHP正确的找到对应的文件。

文件的加载路径包含两大类：

1. 绝对路径：
    从磁盘的根目录开始（本地绝对路径）
    Windows：盘符C:/路径/PHP文件
    Linux：/路径/PHP文件
    从网站根目录开始（网络绝对路径）
    /: 相对于网站主机名字对应的路径
    localhost/index.php->E:/server/xampp/htdoc/index.php
2. 相对路径：从当前文件所在目录开始的路径
    .或者./ : 表示当前文件夹
    ../ : 上级目录（当前文件夹的上一层文件夹）

绝对路径和相对路径的加载区别：

1. 绝对路径相对效率偏低，但是相对安全
2. 相对路径相对效率高些，但是容易出错（相对路径会发生改变）

### 文件嵌套包含

文件嵌套包含：一个文件包含另外一个文件，同时被包含的文件又包含了另外一个文件。
