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