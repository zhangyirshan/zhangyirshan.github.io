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
