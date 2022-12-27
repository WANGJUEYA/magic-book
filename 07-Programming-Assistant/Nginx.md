---
title: Nginx
summary: Nginx
tags:
  - Nginx
categories:
  - 07-Programming-Assistant
date: 2022-10-15 15:13:24
---

## 常用命令

```shell
$ nginx -v # 显示 nginx 的版本
$ start nginx # 开启nginx
$ nginx -s stop # 快速关闭Nginx，可能不保存相关信息，并迅速终止web服务。
$ nginx -s quit # 平稳关闭Nginx，保存相关信息，有安排的结束web服务。
$ nginx -s reload # 因改变了Nginx相关配置，需要重新加载配置而重载。
$ nginx -s reopen # 重新打开日志文件。
$ nginx -c filename # 为 Nginx 指定一个配置文件，来代替缺省的。
$ nginx -t # 不运行，而仅仅测试配置文件; nginx 将检查配置文件的语法的正确性，并尝试打开配置文件中所引用到的文件。
```

## CentOS7下安装Nginx

### 1. 安装所需环境
~~~shell
yum install gcc-c++
yum install -y pcre pcre-devel
yum install -y zlib zlib-devel
yum install -y openssl openssl-devel
~~~

### 2. 下载
+ 直接下载 .tar.gz 安装包，地址：https://nginx.org/en/download.html
~~~shell
wget -c https://nginx.org/download/nginx-1.22.1.tar.gz
~~~

### 3. 安装

+ [同一主机安装多个nginx](https://blog.csdn.net/u011066470/article/details/118321392)

~~~shell
tar -zxvf nginx-1.22.1.tar.gz
cd nginx-1.22.1
# 使用默认配置
./configure
# 指定安装路径(安装多个nginx需要配置不同安装路径)
./configure --prefix=/home/work/nginx-work
# 编译安装
make && make install
# 查找安装路径
whereis nginx
~~~

### 4. 启动
~~~shell
cd /usr/local/nginx/sbin/
./nginx 

# 此方式相当于先查出 nginx 进程 id 再使用 kill 命令强制杀掉进程。
./nginx -s stop
# 此方式是待 nginx 进程处理任务完毕后停止
./nginx -s quit

# 重新加载配置文件
./nginx -s reload
~~~

