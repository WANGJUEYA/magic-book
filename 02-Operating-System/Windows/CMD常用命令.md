---
title: CMD常用命令
summary: CMD常用命令
tags:
  - cmd
categories:
  - 02-Operating-System
  - Windows
date: 2022-10-15 14:37:24
---

## 常用命令

### 按类型合并文件

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

### 打印目录树 tree

```bash
tree >> ../tree.txt
```

### 删除文件夹

```bash
rmdir /s /q 文件夹名
```

### 测试远端端口并发送文件

```shell
telnet ip port
# 使用 ctrl + ] 进入命令行页面
# 输入字符串可发送命令
# https://learn.microsoft.com/zh-cn/windows-server/administration/windows-commands/telnet-send
sen {ao | ayt | brk | esc | ip | synch | <string>} [?]
```
