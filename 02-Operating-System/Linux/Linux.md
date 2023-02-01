---
title: Linux
summary: Linux 常用命令
categories:
 - 02-Operating-System
 - Linux
date: 2022-10-15 11:59:17
tags:
---

## 常用命令

```shell
pwd # 查看当前路径
history # 搜索服务器上历史命令
history | grep tomcat # 搜索包含tomcat历史命令
# ? 如何查询某一个历史命令附近的命令?
ln -s /data/nodejs/node-v16.13.2-linux-x64/bin/yarn /usr/sbin/yarn # 全局命令
vim /etc/profile # 修改系统环境变量
```

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

### 文件信息

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

## 软件安装

```shell
whereis nginx # 查看软件安装路径
which nginx # 查看运行文件所在路径
ps -ef | grep tomcat # 查看运行进程
kill -9 PID # 杀死运行进程
lsof -i:port # 查看端口占用
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

## 内存监控

### ps 查看进程及进程中线程当前CPU使用情况

### free(总体RAM利用率)

```shell
free -h # 查看 Mem 及 Swap
free -s 3 # 每隔3s执行一次free
free -c 3 # 运行一定次数
free -h -s 5 -c 20 # 每个5s输出一次, 共输出20次
```

### top(每个进程内存利用率)

```shell
top # 查看每个进程内存占用率
top -H -p 31357 # 显示指定进程下耗时最高的线程
shift + m # 按内存占用率排序
q # 退出top监控
```

### htop(图形化显示RAM总体利用率)

```shell
yum -y install epel-release && yum install htop
htop
```

### java调优过程

+ https://dandelioncloud.cn/article/details/1439110455683014658/

```shell
jps [-mlv]# 查询java进程 -q 只展示pid -m 输出main参数 -l 主类完整包名 -v jvm 参数
jstack [option] 31357 > jstack.31357.log # 保存线程消耗最高的日志 -F 强制打印 -l 长列表  -m 混合模式
jmap # jvm使用情况 
jmap -heap [pid] # 查看整个JVM内存状态,要注意的是在使用CMS GC 情况下，jmap -heap的执行有可能会导致JAVA 进程挂起 
jmap -histo [pid] # 查看JVM堆中对象详细占用情况 
jmap -histo:live pid # 指定了live子选项，则只计算活动的对象 
jmap -dump:format=b,file=文件名 [pid] # 导出整个JVM 中内存信息
jvisualvm.exe # java 自带的jvm监控工具
pmap # - report memory map of a process(查看进程的内存映像信息)pmap命令用于报告进程的内存映射关系
```

## 其他命令
```shell
wget
ll
tail
rename
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


### htop

#### 基础命令

```shell
htop -h # 获取命令帮助
htop -v # 查看命令版本
htop -p 4035 # 查看指定进程信息
htop -u root # 查看指定用户信息
htop -d 50 # 指定刷新间隔; 单位 0.1s
htop -s M_SIZE # 指定列排序

```
#### 信息关键字

| 名称 | 解释 |
| :---: | :--- |
| PID | 进程标志号，是非零正整数 |
| USER | 进程所有者的用户名 |
| PR | 进程的优先级别 |
| NI | 进程的优先级别数值 |
| VIRT | 进程占用的虚拟内存值 |
| RES | 进程占用的物理内存值 |
| SHR | 进程使用的共享内存值 |
| S | 进程的状态，其中S表示休眠，R表示正在运行，Z表示僵死状态，N表示该进程优先值是负数 |
| %CPU | 该进程占用的CPU使用率 |
| %MEM | 该进程占用的物理内存和总内存的百分比 |
| TIME+ | 该进程启动后占用的总的CPU时间 |
| COMMAND | 进程启动的启动命令名称 |

#### 快捷键

| 名称 | 功能 |
| :---: | :--- |
| F1 | 获取功能键帮助 |
| F2 | 显示设置 |
| F3 | 搜索command内容 |
| F4 | 过滤 |
| F5 | 按照进程树展示，开启之后后面快捷键递进，即使用 F5 进行排序选择 |
| F6 | 排序选择 |
| F7 | 减小进程优先级 |
| F8 | 增加进程优先级 |
| F9 | 杀死进程, 可以使用上下键选择后回车杀死进程 |
| F10 | 退出 |

#### 参数说明

| 参数 | 参数说明 |
| :--- | :--- |
| -C --no-color	| 使用单色配色方案|
| -d --delay=DELAY	| 设置刷新间隔时间，单位十分之一秒|
| -h --help	| 打印帮助信息|
| -s --sort-key=COLUMN	| 按照指定列排序选择|
| -t --tree	| 默认值，按照树结构视图显示|
| -u --user=USERNAME	| 只显示指定用户进程信息|
| -p --pid=PID,[,PID,PID…]	| 只显示指定pid进程信息|
| -v --version	| 打印命令版本| 
