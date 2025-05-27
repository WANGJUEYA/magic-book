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

https://www.runoob.com/manual/PostgreSQL/

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

## docker 安装 pgvector

### 使用自定义Docker镜像（推荐）

1. 创建Dockerfile

```dockerfile
# 使用官方PostgreSQL镜像（选择兼容的版本）
FROM postgres:13

# 安装构建依赖
RUN apt-get update && \
    apt-get install -y build-essential postgresql-server-dev-13 git

# 下载并编译pgvector
RUN git clone --branch v0.5.1 https://github.com/pgvector/pgvector.git /tmp/pgvector && \
    cd /tmp/pgvector && \
    make && \
    make install && \
    rm -rf /tmp/pgvector

# 清理不必要的依赖
RUN apt-get purge -y --auto-remove build-essential postgresql-server-dev-13 git && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
```

2. 构建镜像

```bash
docker build -t my-postgres-with-pgvector .
```

3. 运行容器并启用扩展

+ 通过初始化脚本自动启用扩展
    - 创建 init.sql 文件：
    ```sql
    CREATE EXTENSION IF NOT EXISTS vector;
    ```
    - 运行容器时挂载脚本：
```bash
docker run \
  --name my-pgvector \
  -e POSTGRES_PASSWORD=mysecretpassword \
  -v ./init.sql:/docker-entrypoint-initdb.d/init.sql \
  -d my-postgres-with-pgvector
``

+ 手动启用扩展

```bash
docker exec -it my-pgvector psql -U postgres -c "CREATE EXTENSION vector;"
```
### 使用现有社区镜像（快速）

```shell
docker run -d --name pgvector -e POSTGRES_PASSWORD=Password123@postgres -p 5432:5432 -v /path/to/docker/postgresql:/var/lib/postgresql/data ankane/pgvector

# 检查容器是否启动成功
docker exec -it pgvector psql -U postgres
# 在psql中执行
\dx
# 或
SELECT * FROM pg_extension;
# 退出容器
\q

# 运行外网连接容器内postgresql  # 该内容不需要，默认外网可连接，密码错误可能是由于旧的配置文件未删除
# 进入容器命令行
docker exec -it pgvector bash
# 备份原网络访问配置
cp /var/lib/postgresql/data/pg_hba.conf /var/lib/postgresql/data/pg_hba.conf.bk250411
# 追加认证规则到 pg_hba.conf
echo "host all all 0.0.0.0/0 scram-sha-256" >> /var/lib/postgresql/data/pg_hba.conf
# docker复制容器内文件
docker cp pgvector:/var/lib/postgresql/data/pg_hba.conf pg_hba.conf
# 退出容器后重新加载配置
docker exec -u postgres pgvector pg_ctl reload -D /var/lib/postgresql/data
```

### 创建用户及授权

```sql
-- 创建用户并设置密码
CREATE USER maxkb WITH PASSWORD 'maxkb';
-- 创建数据库并指定所有者
CREATE DATABASE maxkb OWNER maxkb;
-- 授予用户数据库权限
GRANT ALL PRIVILEGES ON DATABASE maxkb TO maxkb;
-- 查看所有数据库
\l
-- 连接到目标数据库并授予public模式权限
\c maxkb
GRANT USAGE, CREATE ON SCHEMA public TO maxkb;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO maxkb;
-- 启用向量扩展
CREATE EXTENSION vector;
-- 创建模式，不建议直接使用public库
create schema maxkb authorization maxkb;
-- 切换模式
set search_path to maxkb;
show search_path;
```
