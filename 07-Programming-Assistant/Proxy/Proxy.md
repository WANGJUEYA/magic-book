---
title: Proxy
summary: Proxy
tags:
  - 代理
  - 正向代理
  - 反向代理
categories:
  - 07-Programming-Assistant
  - Proxy
date: 2023-07-14 09:00:00
---

HTTP代理软件是指可以将客户端的HTTP请求转发给目标服务器，并将服务器的响应返回给客户端的软件。

## 正向代理 (Forward Proxy)

正向代理是最常见的代理类型，它充当客户端和服务器之间的中间人。

客户端将请求发送到代理服务器，代理服务器会将请求转发给服务器，并将服务器响应返回给客户端。 

正向代理常用于绕过网络访问限制和保护客户端隐私。

一些常见的正向代理软件包括Squid、Privoxy、Polipo等。

## 反向代理 (Reverse Proxy)

反向代理是位于服务器端的代理服务器，充当服务器和客户端之间的中间人。

当客户端向服务器发送请求时，反向代理会将请求转发到真正的服务器，然后将服务器的响应返回给客户端。

反向代理通常用于负载均衡、缓存加速和网络安全。

一些常见的反向代理软件包括[Nginx](/nginx)、HAProxy、Apache等。

👉 用[HTTP-Proxy-Servlet](/http-proxy-servlet)实现反向代理
