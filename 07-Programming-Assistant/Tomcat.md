---
title: Tomcat
summary: Tomcat
tags:
  - Tomcat
categories:
  - 07-Programming-Assistant
date: 2022-10-15 14:51:23
---

### 停止及重启应用

```shell
ps -ef | grep tomcat
ps aux | grep tomcat
kill -9 <No>
./bin/startup.sh
tail -f logs/catalina.out
# 查询关键字前后十行信息
grep -C 10 'key word' /usr/local/apache-tomcat-9.0.65/logs/catalina.out
# 匹配最后十条数据
grep -C 10 'key word' /usr/local/apache-tomcat-9.0.65/logs/catalina.out | tail -10
# 导出十分钟内的日志
# sed -n '/开始时间/,/结束时间/p' catalina.out > 输出文件.log
sed -n '/2023-07-11 09:10*/,/2023-07-11 09:15*/p' catalina.out > log0915.log
```

### 增加系统内存

```shell
CATALINA_OPTS="$CATALINA_OPTS -server -Xms4096m -Xmx4096m -XX:MaxNewSize=1024m -XX:PermSize=512M -XX:MaxPermSize=1024m"
JAVA_OPTS="$JAVA_OPTS -server -Xms4096m -Xmx4096m -XX:MaxNewSize=1024m -XX:PermSize=512M -XX:MaxPermSize=1024m"
```

### 查看当前系统占用内存

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

### 升级https

#### 自己生成证书
```shell
keytool -genkey -alias mine -keypass password -keyalg RSA -keysize 1024 -validity 36500 -keystore mine.keystore -storepass password -deststoretype pkcs12
```

> 注: 自己生成的证书小程序无法使用, 需要去腾讯云平台下载ssl证书

[腾讯云ssl证书](https://console.cloud.tencent.com/ssl?source=DNSPod&page=console&from=productoverview)

#### 服务器配置

tomcat/conf/server.xml

```xml

<Connector acceptCount="100" disableUploadTimeout="true" enableLookups="false"
           keystoreFile="你的.keystore文件路径，这个文件名.keystore也要加上"
           keystorePass="你刚才设定的密钥库密码"
           port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
           maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
           clientAuth="false" sslProtocol="TLS"/>

```


### tomcat日志分割

#### 安装`cronolog`

```shell
# 快捷安装
yum localinstall http://rpmfind.net/linux/epel/7/x86_64/Packages/c/cronolog-1.6.2-14.el7.x86_64.rpm
# 安装成功版本查询
cronolog --version
```

+ [安装参考](https://blog.csdn.net/goudaozuihou/article/details/124896224)

#### 配置 `catalina.out`

```out
# 226行
if [ -z "$CATALINA_OUT" ] ; then
  CATALINA_OUT="$CATALINA_BASE"/logs/catalina.out
fi

# 修改为 =======>
if [ -z "$CATALINA_OUT" ] ; then
  CATALINA_OUT="$CATALINA_BASE"/logs/catalinaLog.%Y-%m-%d.out
fi

# 460 行，删除或者注释掉 =======> ps: tomcat9这行注释启动会报错, 不注释不影响使用
# touch "$CATALINA_OUT"

# 474行和484行两处修改

org.apache.catalina.startup.Bootstrap "$@" start \
  >> "$CATALINA_OUT" 2>&1 "&"

=======>

org.apache.catalina.startup.Bootstrap "$@" start 2>&1\
  | /sbin/cronolog "$CATALINA_BASE"/logs/catalinaLog.%Y-%m-%d.out >> /dev/null &

# 重新启动tomcat
```

+ [配置参考](https://blog.51cto.com/u_15077537/4252000)