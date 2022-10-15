---
title: Maven
summary: Maven
tags:
  - TODO
  - Maven
categories:
  - 07-Programming-Assistant
date: 2022-10-15 15:27:39
---

## 将不在官方仓库中的jar包手动安装在本地

```shell
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.2.0 -Dpackaging=jar -Dfile=E:\oracle\ojdbc14-10.2.0.2.0.jar
```
