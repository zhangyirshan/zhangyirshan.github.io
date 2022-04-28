---
title: Linux常用代码
p: 操作系统/Linux/Linux常用代码
date: 2022-04-28 21:01:59
tags: Linux
categories: Linux
---
## 获取当前文件绝对路径

```shell
DIRNAME=$(realpath $(dirname $0))
```

## 文件夹

### 判断文件夹下

```shell
if [ ! -d "/data/" ];then
  mkdir /data
  else
  echo "文件夹已经存在"
fi
```

### 判断文件夹下是否存在文件

```shell
# ./test是目录路径
IF[[ $(ls -A ./test) ]]; then
    # do something
    echo hello
fi
```
