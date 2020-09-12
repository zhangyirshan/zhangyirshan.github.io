---
title: PHP编程风格
p: 编程风格/PHP编程风格
date: 2020-7-13 17:51:30
tags: 编程风格
categories: [编程风格,PHP编程风格]
---

## 方法调用

普通的方法调用要使用**对象->方法**的防止来进行调用，静态方法使用类::静态方法的方式
```php
$wishService->crystal($userNo,$cpId);

Controller::validatePostForm([
    "userNo" => "present",
    "cookie" => "present",
    "cpId" => "present",
]);
```

## DAO编写规范

用双引号包裹sql语句，因为php中双引号字符串里的内容可以翻译变量

```php
public function updateUserAnimationState($itemId, $userNo) {
    try {
        $pdo = $this->GetPdo();
        $sql = "INSERT INTO user_animation_state (user_id,role_id,type,animation_id,delsign) VALUES ($userNo,990,0,$itemId,0) ON DUPLICATE key UPDATE animation_id = $itemId";
        $this->logger->Debug($sql);
        $prepare = $pdo->prepare($sql);
        $prepare->execute();
        return $prepare->rowCount();
    } catch (Throwable $e) {
        $this->logger->Error($e);
        return false;
    }
}
```

## 对象判空

当需要判断数组不为空且长度大于0时
使用`empty($arr)`
如果希望对象等于NULL，`ISNULL($arr)`
长度大于0, `sizeof($arr) > 0`

## 对象是否相等

`strcamp($a,$b) === 0`,会将变量a，b转为字符串进行比较

## 循环

在使用foreach的&后 ，需要unset($value)释放变量防止后续改变之前的数组

```php
foreach ($result as &$value) {}
unset($value);
```

## 时间

date("Y-m-d") 

| 格式化方式 | 说明                                               | 格式化方式 | 说明                                               |
| ---------- | ---------------------------------------------------- | ---------- | ---------------------------------------------------- |
| Y          | 4位数字年，y为2位数字，如99即1999年     | Y          | 4位数字年，y为2位数字，如99即1999年     |
| m          | 数字月份，前面有前导0，如01。n 为无前导0数字月份 | m          | 数字月份，前面有前导0，如01。n 为无前导0数字月份 |
| F          | 月份，完整的文本格式，例如 January 或者 March | F          | 月份，完整的文本格式，例如 January 或者 March |
| M          | 三个字母缩写表示的月份，例如 Jan 或者 Mar | M          | 三个字母缩写表示的月份，例如 Jan 或者 Mar |
| d          | 月份中的第几天，前面有前导0，如03。j 为无前导0的天数 | d          | 月份中的第几天，前面有前导0，如03。j 为无前导0的天数 |
| w          | 星期中的第几天，以数字表示，0表示星期天 | w          | 星期中的第几天，以数字表示，0表示星期天 |
| z          | 年份中的第几天，范围0-366                  | z          | 年份中的第几天，范围0-366                  |
| W          | 年份中的第几周，如第32周                  | W          | 年份中的第几周，如第32周                  |
| H          | 24小时格式，有前导0，h为12小时格式     | H          | 24小时格式，有前导0，h为12小时格式     |
| G          | 24小时格式，无前导0，g为对应12小时格式 | G          | 24小时格式，无前导0，g为对应12小时格式 |
| i          | 分钟格式，有前导0                            | i          | 分钟格式，有前导0                            |
| s          | 秒格式，有前导0                               | s          | 秒格式，有前导0                               |
| A          | 大写上下午，如AM，a为小写                 | A          | 大写上下午，如AM，a为小写                 |

## CURL

```php
public function Curl(string $url, $param) {
        $curl = curl_init();
        curl_setopt($curl, CURLOPT_URL, $url);
        curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "POST");
        curl_setopt($curl, CURLOPT_POSTFIELDS,json_encode($param));
        curl_setopt($curl, CURLOPT_RETURNTRANSFER,true);
        curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                'Content-Type: application/json',
                'Content-Length: ' . strlen(json_encode($param)))
        );
        $data = curl_exec($curl);
        curl_close($curl);
        return $data;
    }
```