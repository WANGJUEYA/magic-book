---
title: Maven
summary: Maven
tags:
  - Maven
categories:
  - 07-Programming-Assistant
date: 2022-10-15 15:27:39
---

## 将不在官方仓库中的jar包手动安装在本地

```shell
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.2.0 -Dpackaging=jar -Dfile=E:\oracle\ojdbc14-10.2.0.2.0.jar
```
## 将不在官方仓库中的jar包手动发布到仓库
```shell
mvn deploy:deploy-file -Dpackaging=jar -Dfile=jar包文件地址,绝对路径 -DgroupId=组名 -DartifactId=项目名称 -Dversion=版本号 -DrepositoryId=私库id -Durl=私仓地址
mvn deploy:deploy-file -Dpackaging=pom -Dfile=pom.xml            -DgroupId=组名 -DartifactId=项目名称 -Dversion=版本号 -DrepositoryId=私库id -Durl=私仓地址
```

## 查找依赖

+ https://maven.apache.org/plugins/maven-dependency-plugin/tree-mojo.html
+ https://maven.apache.org/plugins/maven-dependency-plugin/examples/filtering-the-dependency-tree.html

```shell
mvn dependency:tree >> tree.txt # 将依赖数存储到文本
mvn dependency:tree -Dincludes='com.alibaba' # 查看依赖树中包含某个groupId的依赖链（-Dincludes后面跟上groupId）
mvn dependency:tree -Dincludes='com.alibaba':fastjson: # 查看依赖树中包含某个groupId和artifactId的依赖链（注意-Dincludes后面是等于号）
mvn dependency:tree -Dincludes=:fastjson: # 查看依赖树中包含某个artifactId的依赖链（artifactId前面加上冒号）
```
