---
title: Jenkins
keywords: Jenkins
summary: Jenkins
tags:
  - Jenkins
categories:
  - 07-Programming-Assistant
date: 2023-01-31 10:51:15
---

## 更改jenkins默认工作目录

> 查看jenkins默认工作目录

+ Manage Jenkins > System Configuration(系统配置) > 主目录`/root/.jenkins`

> 更改jenkins默认工作目录

1. 本项目使用tomcat部署, 停用tomcat
2. 更改系统全局参数`JENKINS_HOME`到新地址(更改前将原来的主目录复制到新目录)
```shell
vim /etc/profile # 修改系统环境变量
# 文件中新增一条jenkins环境变量
export JENKINS_HOME=/data/jenkins
```
3. 重启tomcat(jenkins重启)
