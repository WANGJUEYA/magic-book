---
title: DataGrip连接达梦数据库
tags:
  - Database
  - DataGrip
categories:
  - 07-Programming-Assistant
date: 2022-12-02 15:33:05
---

## a. 准备达梦驱动

+ 可从maven本地仓库中获取

```xml
<dependency>
    <groupId>com.dameng</groupId>
    <artifactId>DmJdbcDriver18</artifactId>
    <version>${dm-jdbc-driver.version}</version>
</dependency>
```

## b. 新建一个驱动

![新建一个驱动](DataGrip连接达梦数据库/新建一个驱动.png)

## c. 导入驱动需要的jar包

![导入jar包](DataGrip连接达梦数据库/导入jar包.png)

## d. 新建数据库连接

![新建数据库连接](DataGrip连接达梦数据库/新建数据库连接.png)

