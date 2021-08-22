---
title: PHP操作数据库的注意事项
date: 2020-07-10 22:16:23
tags: [错误,PHP]
categories: [错误,PHP]
---
## 关于时间的sql语句

**一定要将与时间比较的变量用单引号包起来**

```php
public function weekIn($cpId, $start, $end) {
    $pdo = $this->GetPdo();
    $sql = 'SELECT SUM(intimacy_add) as weekIn FROM cp_intimacy_record 
            WHERE delsign = 0 and cp_id = ' . $cpId . ' and createtime BETWEEN \'' . $start . '\' and \'' . $end . '\'';
    $prepare = $pdo->prepare($sql);
    $prepare->execute();
    return $prepare->fetchAll(PDO::FETCH_ASSOC);
}
```
