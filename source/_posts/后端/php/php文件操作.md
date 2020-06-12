---
title: php语法基础
p: 后端/php/php文件操作
date: 2020-06-11 22:15:09
tags: php
categories: php
---
## 读取文件

### readfile读取文件

> 传入一个文件路径，输出一个文件。

```php
int readfile ( string $文件名)


//linux类的读了方式
readfile("/home/paul/test.txt");
//windows类的读取方式
readfile("c:\\boot.ini");
```

注意：上面的代码中windows的斜线是\斜线，可能会转义掉一些字符。因此，我们写的时候写上两个斜线。

### file_get_contents打开文件

> 传入一个文件或文件路径，打开这个文件返回文件的内容。文件的内容是一个字符串。

```php
string file_get_contents ( string filename)


//假设我们有一个多行的文件叫NoAlike.txt，没有的话你可以新建一个这个文件
$filename = 'NoAlike.txt';


//打开这个文件，将文件内容赋值给$filestring
$filestring = file_get_contents($filename);

//因为每一行有一个回车即\n，我用\n来把这个字符串切割成数组
$filearray = explode("\n", $filestring);

//把切割成的数组，下标赋值给$key,值赋值给$val，每次循环将$key加1。
while (list($key, $val) = each($filearray)) {
    ++$key;
    $val = trim($val);

    //用的单引号，单引号不解释变量进行了拼接而已
    print 'Line' . $key .':'.  $val.'<br />';
}
```

### fopen、fread、fclose操作读取文件

