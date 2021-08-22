---
title: rsync+inotity实时监控同步工具
date: 2021-04-25 10:01:26
tags: [rsync,inotity]
categories: [rsync,inotity]
---
## rsync linux同步工具

[rsync官方网站](https://rsync.samba.org/)

### 安装

[rsync下载地址](https://github.com/WayneD/rsync/releases)

1.  tar zxvf rsync-3.2.3.tar.gz
2.  cd rsync-3.2.3
3. ./configure --prefix = /opt/rsync
4. make
5. make install

如果configure命令无法执行，请根据文档进行安装环境
[rsync解压环境配置](https://github.com/WayneD/rsync/blob/master/INSTALL.md)

```shell script
sudo yum -y install epel-release
sudo yum -y install gcc g++ gawk autoconf automake python3-pip
sudo yum -y install acl libacl-devel
sudo yum -y install attr libattr-devel
sudo yum -y install xxhash-devel
sudo yum -y install libzstd-devel
sudo yum -y install lz4-devel
sudo yum -y install openssl-devel
python3 -mpip install --user commonmark
```

#### 服务器端

将以下几个文件再rsync目录下创建

1. rsyncd.conf
2. rsyncd.lock
3. rsyncd.motd
4. rsyncd.secrets
5. rsyncd.log

#### rsyncd.conf

```shell script
port = 873
uid = root # 指定运行该服务的权限
gid = root
use chroot = no
max connections = 2000
timeout 600
read only = false # 是否只读,注意：如果需要从客户端推送文件到服务端一定要配置此行
lock file = /opt/rsync/rsyncd.lock
log file = /opt/rsync/rsyncd.log # 指定日志文件
pid file = /opt/rsync/rsyncd.pid
motd file = /opt/rsync/rsyncd.motd

[neo4j] # 定义同步项目名 可以定义多个同步项目
path = /opt/data/neo4j # 指定源路径
hosts allow = *
#hosts deny = 0.0.0.0/32
auth users root  #该用户系统中存在且对后面指定的备份目录拥有权限
secrets file = /opt/rsync/rsyncd.secrets # 用户名密码
comment = ocpyang neo4j
```

#### rsyncd.secrets

```shell script
root:root # 用户名:密码
```

配置权限

```shell script
chmod 600 rsyncd.secrets
```

#### rsyncd.motd

```shell script
++++++++++++++++++++++++++++++++++++  
Wlecome to ocpyang  rsync services         
++++++++++++++++++++++++++++++++++++
```

#### 启动

```shell script
rsync --daemon --config=/opt/rsync/rsyncd.conf
netstat -lntp | grep 873 # 检查是否启动
```

#### 停止

```shell script
pkill rsync
```

#### 客户端

配置rsyncd.secrets

```shell script
root # 单独配置密码，和服务端的密码一致
```

配置权限

```shell script
chmod 600 rsyncd.secrets
```

rsync 的详细参数
- -v, --verbose 详细模式输出
- -q, --quiet 精简输出模式
- -c, --checksum 打开校验开关，强制对文件传输进行校验
- -a, --archive 归档模式，表示以递归方式传输文件，并保持所有文件属性，等于-rlptgoD
- -r, --recursive 对子目录以递归模式处理
- -R, --relative 使用相对路径信息
- -b, --backup 创建备份，也就是对于目的已经存在有同样的文件名时，将老的文件重新命名为~filename。可以使用--suffix选项来指定不同的备份文件前缀。
- --backup-dir 将备份文件(如~filename)存放在在目录下。
- -suffix=SUFFIX 定义备份文件前缀
- -u, --update 仅仅进行更新，也就是跳过所有已经存在于DST，并且文件时间晚于要备份的文件。(不覆盖更新的文件)
- -l, --links 保留软链结
- -L, --copy-links 想对待常规文件一样处理软链结
- --copy-unsafe-links 仅仅拷贝指向SRC路径目录树以外的链结
- --safe-links 忽略指向SRC路径目录树以外的链结
- -H, --hard-links 保留硬链结
- -p, --perms 保持文件权限
- -o, --owner 保持文件属主信息
- -g, --group 保持文件属组信息
- -D, --devices 保持设备文件信息
- -t, --times 保持文件时间信息
- -S, --sparse 对稀疏文件进行特殊处理以节省DST的空间
- -n, --dry-run现实哪些文件将被传输
- -W, --whole-file 拷贝文件，不进行增量检测
- -x, --one-file-system 不要跨越文件系统边界
- -B, --block-size=SIZE 检验算法使用的块尺寸，默认是700字节
- -e, --rsh=COMMAND 指定替代rsh的shell程序
- --rsync-path=PATH 指定远程服务器上的rsync命令所在路径信息
- -C, --cvs-exclude 使用和CVS一样的方法自动忽略文件，用来排除那些不希望传输的文件
- --existing 仅仅更新那些已经存在于DST的文件，而不备份那些新创建的文件
- --delete 删除那些DST中SRC没有的文件
- --delete-excluded 同样删除接收端那些被该选项指定排除的文件
- --delete-after 传输结束以后再删除
- --ignore-errors 及时出现IO错误也进行删除
- --max-delete=NUM 最多删除NUM个文件
- --partial 保留那些因故没有完全传输的文件，以是加快随后的再次传输
- --force 强制删除目录，即使不为空
- --numeric-ids 不将数字的用户和组ID匹配为用户名和组名
- --timeout=TIME IP超时时间，单位为秒
- -I, --ignore-times 不跳过那些有同样的时间和长度的文件
- --size-only 当决定是否要备份文件时，仅仅察看文件大小而不考虑文件时间
- --modify-window=NUM 决定文件是否时间相同时使用的时间戳窗口，默认为0
- -T --temp-dir=DIR 在DIR中创建临时文件
- --compare-dest=DIR 同样比较DIR中的文件来决定是否需要备份
- -P 等同于 --partial
- --progress 显示备份过程
- -z, --compress 对备份的文件在传输时进行压缩处理
- --exclude=PATTERN 指定排除不需要传输的文件模式
- --include=PATTERN 指定不排除而需要传输的文件模式
- --exclude-from=FILE 排除FILE中指定模式的文件
- --include-from=FILE 不排除FILE指定模式匹配的文件
- --version 打印版本信息
- --address 绑定到特定的地址
- --config=FILE 指定其他的配置文件，不使用默认的rsyncd.conf文件
- --port=PORT 指定其他的rsync服务端口
- --blocking-io 对远程shell使用阻塞IO
- -stats 给出某些文件的传输状态
- --progress 在传输时现实传输过程
- --log-format=FORMAT 指定日志文件格式
- --password-file=FILE 从FILE中得到密码
- --bwlimit=KBPS 限制I/O带宽，KBytes per second
- -h, --help 显示帮助信息

## inotity linux监控工具

inotify介绍-- 是一种强大的、细颗粒的、异步的文件系统监控机制，*&####&*_0_*&####&*内核从2.6.13起，加入Inotify可以监控文件系统中添加、删除、修改移动等各种事件，利用这个内核接口，就可以监控文件系统下文件的各种变化情况。

### 安装

```shell script
yum install -y inotify-tools
# 查看inotify-tools包的工具程序
rpm -ql inotify-tools
```

### inotifywait 参数说明

<table>
    <tr>
        <th>参数名称</th>
        <th>参数说明</th>
    </tr>
    <tr>
        <td>-m,–monitor</td>
        <td>始终保持事件监听状态</td>
    </tr>
    <tr>
        <td>-r,–recursive</td>
        <td>递归查询目录</td>
    </tr>
    <tr>
        <td>-q,–quiet</td>
        <td>只打印监控事件的信息</td>
    </tr>
    <tr>
        <td>–excludei</td>
        <td>排除文件或目录时，不区分大小写</td>
    </tr>
    <tr>
        <td>-t,–timeout</td>
        <td>超时时间</td>
    </tr>
    <tr>
        <td>–timefmt</td>
        <td>指定时间输出格式</td>
    </tr>
    <tr>
        <td>–format</td>
        <td>指定时间输出格式</td>
    </tr>
    <tr>
        <td>-e,–event</td>
        <td>后面指定删、增、改等事件</td>
    </tr>
</table>

### inotifywait events事件说明

<table>
    <tr>
        <th>事件名称</th>
        <th>事件说明</th>
    </tr>
    <tr>
        <td>access</td>
        <td>读取文件或目录内容</td>
    </tr>
    <tr>
        <td>modify</td>
        <td>修改文件或目录内容</td>
    </tr>
    <tr>
        <td>attrib</td>
        <td>文件或目录的属性改变</td>
    </tr>
    <tr>
        <td>close_write</td>
        <td>修改真实文件内容</td>
    </tr>
    <tr>
        <td>move</td>
        <td>移动，对文件进行移动操作</td>
    </tr>
    <tr>
        <td>create</td>
        <td>创建，生成新文件</td>
    </tr>
    <tr>
        <td>open</td>
        <td>打开，对文件进行打开操作</td>
    </tr>
    <tr>
        <td>delete</td>
        <td>删除，文件被删除</td>
    </tr>
</table>

### 使用

1. 创建inotify.sh文件

```shell script
host=172.18.219.228 #从服务器的主机地址
data_dir=/opt/data/neo4j/ #内容发布服务器上创建的同步数的路径
dst=neo4j #从服务器上导出的共享目录
username=root #从服务器上/etc#/rsyncd.passwd这个文件中定义的用户名
passfile=/opt/rsync/rsyncd.secrets # #从服务器密码文件
/usr/bin/inotifywait -mrq --timefmt '%d/%m/%y %H:%M' --format '%T %w%f%e' -e modify,delete,create,attrib $data_dir | while read files
        do
                /opt/rsync/bin/rsync -vzrtopg --progress --delete --password-file=$passfile $data_dir $username@$host::$dst
                echo "${files} was rsynced" >> /opt/rsync/rsync.log 2>&1 #写入日志
        done           
```

```shell script
# 运行 
bash inotify.sh &
ps -ef |grep test.sh
```

### 优化

[博客](https://www.cnblogs.com/ginvip/p/6430986.html)
