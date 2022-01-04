---
title: php连接Redis主从
date: 2022-01-04 13:38:25
tags: [php,redis]
categories: php
---

RedisMs.php是读取数据库代码，getByCommand中可按照自己需要配置命令

下面是调用demo

```php
require_once './RedisMs.php';

var_dump('begin');
$handler = RedisMs::getInstance(true);

$result1 = $handler->runCommand('set',['test',1]);
echo($result1);
$result2 = $handler->runCommand('get',['test']);
var_dump($result2);
```