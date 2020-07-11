---
title: php文件操作
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

```php
resource fopen ( string $文件名, string 模式)
string fread ( resource $操作资源, int 读取长度)
bool fclose ( resource $操作资源 )
```

通过上面的函数我们来讲解资源类型的通常操作方式：

1. 打开资源
2. 使用相关函数进行操作
3. 关闭资源

#### fopen函数

fopen函数的功能是打开文件，参数主要有两个：

1. 文件打开的路径
2. 打开文件的模式

返回类型是一个资源类型，我们第一次遇到了之前基础类型的时候讲到的资源类型。
资源类型需要其他的函数来操作这个资源。所有的资源有打开就要有关闭。

##### fopen的模式

|模式|说明|
|--|--|
|r|只读方式打开，将文件指针指向文件头。|
|r+|读写方式打开，将文件指针指向文件头。|
|w|写入方式打开，将文件指针指向文件头并将文件大小截为零。如果文件不存在则尝试创建|
|w+|读写方式打开，将文件指针指向文件头并将文件大小截为零。如果文件不存在则尝试创建|
|a|写入方式打开，将文件指针指向文件末尾。如果文件不存在则尝试创建|
|a+|读写方式打开，将文件指针指向文件末尾。如果文件不存在则尝试创建之|
|x|创建并以写入方式打开，将文件指针指向文件头。如果文件已存在，则 fopen() 调用失败并返回 FALSE，并生成一条 E_WARNING 级别的错误信息。如果文件不存在则尝试创建|
|x+|创建并以读写方式打开，将文件指针指向文件头。如果文件已存在，则 fopen() 调用失败并返回 FALSE，并生成一条 E_WARNING 级别的错误信息。如果文件不存在则尝试创建|

#### fread函数

>函数的功能的功能是读取打开的文件资源。读取指定长度的文件资源，读取一部份向后移动一部份。至到文件结尾。

#### fclose函数

>fclose函数的功能是关闭资源。资源有打开就有关闭。

## 写入文件

### file_put_contents写入文件

```php
int file_put_contents ( string $文件路径, string $写入数据])
```

功能：向指定的文件当中写入一个字符串，如果文件不存在则创建文件。返回的是写入的字节长度

### fwrite配合fopen进行写入操作

```php
int fwrite ( resource $文件资源变量, string $写入的字符串 [, int 长度])
// 注：fwrite的别名函数是fputs
```

```php
<?php
   $filename = 'test.txt';
   $fp= fopen($filename, "w");
   $len = fwrite($fp, '我是一只来自北方的狼，却在南方冻成了狗');
   fclose($fp);
   print $len .'字节被写入了\n';
```

总结：

1. 不论有没有新建都会打开文件重新写入
2. 原有的文件内容会被覆盖掉
3. 文件不存在会创建

## 创建临时文件

我们之前创建的文件都是永久文件。

而创建临时文件在我们平时的项目开发中也非常有用。创建临时文件的几个好处：

文完后即删除

不需要去维护这个文件的删除状态

例如：我需要把A的文件内容转存B里面，把B的文件内容转存到C里面。

```php
resource tmpfile ( )
```

功能：创建一个临时文件，返回资源类型。关闭文件即被删除。

```php
//创建了一个临时文件
$handle = tmpfile();
//向里面写入了数据
$numbytes = fwrite($handle, '写入临时文件');
//关闭临时文件，文件即被删除
fclose($handle);
echo  '向临时文件中写入了'.$numbytes . '个字节';

向临时文件中写入了18个字节
```

## 移动、拷贝和删除文件

### 重命名文件

```php
bool rename($旧名,$新名);
这个函数返回一个bool值，将旧的名字改为新的名字。
```

```php
//旧文件名
$filename = 'test.txt';
//新文件名
$filename2 = $filename . '.old';
//复制文件
rename($filename, $filename2);
```

### 复制文件

```php
bool copy(源文件,目标文件)
功能：将指定路径的源文件，复制一份到目标文件的位置。
```

```php
//旧文件名
$filename = 'copy.txt';
//新文件名
$filename2 = $filename . '_new';
//修改名字。
copy($filename, $filename2);
```

### 删除文件

