---
title: Squid
summary: Squid
tags:
  - 代理
  - 正向代理
categories:
  - 07-Programming-Assistant
  - Proxy
date: 2023-11-08 14:43:00
---

## 软件安装

### windows安装Squid

在 Windows 平台上安装 Squid 分为以下几个步骤：

1. 下载 Squid for Windows  
   访问 [Squid 官方网站（https://www.squid-cache.org/）](https://www.squid-cache.org/) ，在下载页面找到适用于 Windows 的 Squid 版本，下载对应的 zip 文件。
2. 解压文件  
   将下载的 zip 文件解压到一个合适的位置，例如 C:\Squid。
3. 配置 Squid  (不配置可以使用服务器自己的dns解析)
   在解压后的 Squid 文件夹中，找到 squid.conf 文件，根据你的需求进行配置，例如设置缓存大小、代理规则等。
   + 如果被代理的请求地址不是 `443`, 需要增加一条放行规则 `acl SSL_ports port 9301`
4. 启动 Squid 服务 (Windows 可以在`服务`面板中重启 Squid)
   打开命令提示符，进入 Squid 文件夹，输入以下命令启动 Squid 服务：

```
   squid -N  
```

   至此，Squid 已在 Windows 平台上成功安装并启动。
   注意：Squid 默认监听 3128 端口，你可以根据需求修改 squid.conf 文件中的端口设置。同时，Squid 需要一定的系统资源，请在设置代理规则时根据实际需求进行调整。 
   
5. 若要停止 Squid 服务，只需在命令提示符中输入以下命令：

```
squid -K  
```

更多详细配置选项，请参阅 Squid 官方文档：https://www.squid-cache.org/docs/squid/2.7/configuration_guide/index.html

### 使用代理发送请求判断是否生效

```shell
curl -x127.0.0.1:3128 -I https://host:port/path
```

## java使用

```java
RestTemplate restTemplate=new RestTemplate();
SimpleClientHttpRequestFactory simpleClientHttpRequestFactory=new SimpleClientHttpRequestFactory();
// 添加代理 ip 和 port 即可
simpleClientHttpRequestFactory.setProxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",3128)));
restTemplate.setRequestFactory(simpleClientHttpRequestFactory);
```

### 调用过程发生异常

+ Unable to tunnel through proxy. Proxy returns "HTTP/1.1 403 Forbidden"

vim /etc/squid/squid.conf
增加对应端口的放行规则

```
# https放行
acl SSL_ports port 9301
# http放行
acl Safe_ports port XX
```
重启服务

+ Unable to tunnel through proxy. Proxy returns "HTTP/1.1 503 Service Unavailable"

通过 `curl -x127.0.0.1:3128 -I https://host:port/path` 命令发现报错 ` ERR_DNS_FAIL 0` 

通过命令 `ipconfig /all` 查询当前环境的 dns 服务器, 配置对应的dns服务器地址

修改  /etc/squid/squid.conf 文件，修改dns服务器配置 `dns_nameservers 配置的dns服务器`

