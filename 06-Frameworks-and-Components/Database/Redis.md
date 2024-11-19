---
title: Redis
summary: Redis介绍与实战
tags:
  - redis
categories:
  - 06-Frameworks-and-Components
  - Database
date: 2022-10-15 13:23:30
---
## Redis 五种数据结构

+ String
+ Hash
+ List
+ Set
+ Sorted Set

## 安装部署

```shell
## 更新系统
sudo apt update && sudo apt upgrade -y
## Ubuntu 上安装 Redis
sudo apt install redis-server
## 查看redis版本
redis-cli --version
## 查看redis运行状态
systemctl status redis
## 命令连接redis
redis-cli -h 127.0.0.1 -p 6379 -a 123456
## 设置redis密码(重启后需要重置密码)
config set requirepass 123456
## 卸载redis
sudo apt-get purge --auto-remove redis-server
```

+ [Ubuntu安装特定版本Redis](https://blog.51cto.com/u_16213452/12150139)
```shell
## Ubuntu 上安装特定版本的 Redis
```

## JAVA实战 - Spring

### redis 单机与集群连接、功能测试注入

### redis 实现多项目数据软隔离

### redisTemplate 常用方法

+ 数据自增

```java
redisTemplate.opsForHash().increment(key(receive), mode + "_" + sender, 1);
```

+ 使用`pipeline`让数据批量自增

```java
// pipeline 命令拼接，数据量可以少一点
redisTemplate.executePipelined((RedisCallback<String>) connection -> {
    // 遍历需要批量处理的数据
    receives.stream().filter(StringUtils::isNotBlank).forEach(receive ->
            // rawKey & rawHashKey 利用 redisTemplate.getKeySerializer() & redisTemplate.getHashKeySerializer() 获得
            connection.hIncrBy(cacheRefreshUtils.rawKey(key(receive)), cacheRefreshUtils.rawHashKey(mode + "_" + sender), 1));
    // 必须返回null, 不返回null将抛出异常
    return null;
});
```