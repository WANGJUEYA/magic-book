---
title: MySQL
summary: MySQL
tags:
  - Database
  - MySQL
categories:
  - 06-Frameworks-and-Components
  - Database
date: 2022-10-15 14:50:39
---

my.ini

```
[client]
# 设置mysql客户端默认字符集
default-character-set=utf8

[mysqld]
# 设置3306端口
port = 3306
# 设置mysql的安装目录
basedir=E:\\Mysql
# 设置 mysql数据库的数据的存放目录，MySQL 8+ 不需要以下配置，系统自己生成即可，否则有可能报错
# datadir=C:\\web\\sqldata
# 允许最大连接数
max_connections=20
# 服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=utf8
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
```

### CentOS操作mysql

```shell
mysql -u root # 第一次进入数据库
set password for 'root'@'localhost' = password('xxxxxxxx') # 进入后修改密码
mysql -u root -p # 再次进入数据库
create database txsql; # 创建数据库
use databasename; # 进入某个数据库
exit; # 退出mysql命令行
quit; # 退出mysql命令行
```
