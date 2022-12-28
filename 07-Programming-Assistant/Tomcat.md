---
title: Tomcat
summary: Tomcat
tags:
  - Tomcat
categories:
  - 07-Programming-Assistant
date: 2022-10-15 14:51:23
---

+ 停止及重启应用

```shell
ps -ef | grep tomcat
ps aux | grep tomcat
kill -9 <No>
./bin/startup.sh
tail -f logs/catalina.out
```

+ 增加系统内存

```shell
CATALINA_OPTS="$CATALINA_OPTS -server -Xms4096m -Xmx4096m -XX:MaxNewSize=1024m -XX:PermSize=512M -XX:MaxPermSize=1024m"
JAVA_OPTS="$JAVA_OPTS -server -Xms4096m -Xmx4096m -XX:MaxNewSize=1024m -XX:PermSize=512M -XX:MaxPermSize=1024m"
```

+ 查看当前系统占用内存

+ `/conf/tomcat-users.xml`开启内存管理

```xml
<?xml version='1.0' encoding='utf-8'?>  
<tomcat-users>  
  <role rolename="tomcat"/>  
  <role rolename="manager-gui"/>  
  <user username="admin" password="admin" roles="manager-gui"/>  
  <user username="tomcat" password="tomcat" roles="tomcat"/>  
  <user username="both" password="tomcat" roles="tomcat"/>  
</tomcat-users>  
```
+ tomcat 管理页面查看 `Server Status` admin/admin
