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