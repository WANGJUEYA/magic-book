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
show tables; # use db; 显示指定数据库得所有表名
exit; # 退出mysql命令行
quit; # 退出mysql命令行
select * from information_schema.tables where table_schema = 'databasename';

SHOW DATABASES; # 显示所有数据库
use databasename; # 进入某个数据库
show tables; # 显示数据库下所有表名
quit; # 退出数据库, 执行导出程序
mysqldump -u root -p mydatabase > /tmp/mydatabase.sql; # 导出数据库
mysqldump -u root -p --databases mydatabase1 mydatabase2 > /tmp/mydatabase.sql; # 导出多个数据库
mysql -uroot -p"yourpassword" mydatabase < /tmp/mydatabase.sql
```

### mysql关联查询时字符集不匹配

+ 如果使用数据库复制迁移且建表语句未设置统一字符集，可能会导致关联查询时出现字符集不匹配异常

```sql
-- 查询建表语句
SHOW CREATE TABLE {TABLE_NAME};
-- 查询所有字段字符集
SHOW FULL COLUMNS FROM {TABLE_NAME};
-- 查看服务器设置
SHOW VARIABLES LIKE 'character_set_server';
-- 改变表或字段的字符集
ALTER TABLE {TABLE_NAME} CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
-- 改变某个具体字段的字符集
ALTER TABLE {TABLE_NAME} MODIFY {FIELD_NAME} {FIELD_TYPE} CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```