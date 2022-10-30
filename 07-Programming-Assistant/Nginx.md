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