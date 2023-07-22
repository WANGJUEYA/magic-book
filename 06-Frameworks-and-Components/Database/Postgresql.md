---
title: Postgresql
summary: Postgresql
tags:
  - Database
  - Postgresql
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
```
