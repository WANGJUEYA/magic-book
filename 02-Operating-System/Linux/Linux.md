---
title: Linux
summary: Linux 常用命令
categories:
 - 02-Operating-System
 - Linux
date: 2022-10-15 11:59:17
tags:
---

## 将上一条命令的结果作为下一条命令的参数

test文件夹示例

### xargs
```shell
touch test{1..10}.txt
ls # test10.txt test1.txt test2.txt test3.txt test4.txt test5.txt test6.txt test7.txt test8.txt test9.txt
ls ../test/|grep -v test3 #查找不包含test3的文件
ls ../test/|grep -v test3|xargs rm -rf # 删除不包含test3的文件
ls # test3.txt 
```

### 使用反引号
```shell
touch test{1..10}.txt
ls # test10.txt test1.txt test2.txt test3.txt test4.txt test5.txt test6.txt test7.txt test8.txt test9.txt
rm -rf `ls ../test/|grep -v test2` # 将反引号内的结果作为下一条命令的参数
ls # test2.txt
```

### 使用find exec命令
```shell
touch test{1..10}.txt
ls # test10.txt test1.txt test2.txt test3.txt test4.txt test5.txt test6.txt test7.txt test8.txt test9.txt
find ../test/ -name test2.txt -exec rm -rf {} \; #这里的{}和\;是成对使用的。#将find的查找结果作为参数
# test10.txt  test1.txt  test3.txt  test4.txt  test5.txt  test6.txt  test7.txt  test8.txt  test9.txt
```

### 使用$( )
```shell
touch test{1..10}.txt
ls # test10.txt test1.txt test2.txt test3.txt test4.txt test5.txt test6.txt test7.txt test8.txt test9.txt
rm -rf $(ls ../test|grep -v test2) # 将$( )的执行结果作为rm -rf 的参数
ls # test2.txt
```

## 常用命令

```shell
pwd # 查看当前路径
```

### 查看编辑文件信息

```shell
stat install.log # 查看文件信息
ls install.log # 查看文件信息

# 修改文件信息
touch 
# -a : 仅修改access time。
# -c : 仅修改时间，而不建立文件。
# -d : 后面可以接日期，也可以使用 –date=”日期或时间”
# -m : 仅修改mtime。
# -t : 后面可以接时间，格式为 [YYMMDDhhmm]
```

## 文件操作

```shell
find / -name $name # 搜索匹配的文件名
cp -r $source $direct # 复制文件
rm -rf {path} # 强制删除文件夹及文件

mv A B   # 将目录A重命名为B
mv /a /b/c # 将/a目录移动到/b下，并重命名为c
mv abc 123 # 将一个名为abc的文件重命名为123，如果当前目录下也有个123的文件的话，这个文件是会将它覆盖的。


chmod 777 $file # 权限不足
chmod -Rf 777 $floder # 权限不足
```

### 其他命令
```shell
wget
ll
tail
rename
```

## 软件安装

```shell
whereis nginx # 查看软件安装路径
which nginx # 查看运行文件所在路径
ps -ef | grep tomcat # 查看运行进程
kill -9 PID # 杀死运行进程
lsof -i:port # 查看端口占用
```

## Tools

### vim

+ 开始编辑

```shell
vim filename.suffix
i # 进入编辑
```

+ 退出编辑

```shell
Esc
:wq 保存并退出
:q! 不保存直接退出
```

+ 搜索命令

```shell
/ # 进入后直接搜索; 编辑模式需要Esc退出编辑再使用 /
/关键字 # 向下查找
?关键字 # 向上查找
/关键字\> # 匹配末尾
/\<关键字 # 匹配开头
/\<关键字\> # 匹配全部
:set ignorecase # 不区分大小写
:set noignorecase # 恢复大小写敏感
:set hlsearch # 高亮搜索
:set incsearch # 递进搜索; 每输入一个字符, 搜索一次
n # 移动当下一个匹配处
shift + n # 移动到上一个匹配处
* # 光标移动到关键字任意位置按键; 搜索选定的关键字
```

+ 其他命令
```shell
gg # 跳到第一行
10gg # 跳到最后一行
shift + g # 跳到最后一行
```

### yum

该方法安装的软件版本无法控制

```shell
yum install git -y
rpm -qa | grep git
git --version
ls /usr/libexec/git-core
```


## 开启/关闭防火墙

```shell
firewall-cmd --list-ports # 列出所有防火墙端口
firewall-cmd --query-port=9010/tcp  # 查询端口防火墙状态
firewall-cmd --add-port=9010/tcp --permanent # 增加防火墙端口
systemctl restart firewalld # 重启防火墙
firewall-cmd --query-port=9010/tcp
firewall-cmd --list-ports
```
