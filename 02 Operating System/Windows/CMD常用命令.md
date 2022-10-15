---
title: CMD常用命令
date: 2022-10-15 14:37:24
summary: CMD常用命令
tags:
    - cmd
---

## 常用命令

# 按类型合并文件

```bash
dir
type *.sql >> ../init_database.sql
```

### 关闭java进程

```bash
netstat -aon|findstr "8080"
taskkill -PID 8372 -F
```

### 控制面板-卸载程序

```bash
appwiz.cpl
```

### 刷新DNS缓存

``` bash
ipconfig /flushdns
```

### 批处理文件 bat

```bash
:: 注释内容
```
