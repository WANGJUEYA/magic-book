---
title: Postgresql
summary: Postgresql
tags:
  - Database
  - Postgresql
categories:
  - 06-Frameworks-and-Components
  - Database
date: 2023-07-16 20:24:00
---

## yum源安装

```shell
## rpm下载
sudo yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm
## 安装
# sudo yum remove postgresql-server
sudo yum install -y postgresql-server # -y 表示自动确认 
## 设置开启自启动
sudo /usr/lib/systemd/system/postgresql.service initdb
sudo systemctl enable postgresql-
sudo systemctl start postgresql
## 查看安装是否成功
sudo systemctl status postgresql
## 进入postgresql
psql -U <数据库用户名> -d <数据库名>
# 如果镜像使用默认配置（如官方镜像）：
psql -U postgres -d postgres
psql -U postgres # 这条语句登录数据库，不需要输入数据库密码，注意U是大写的。
## 查看所有数据库
SELECT datname FROM pg_database;
## 查询数据库中所有表
SELECT table_name FROM information_schema.table where table_schema = 'maxkb';
```
