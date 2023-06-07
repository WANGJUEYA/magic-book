---
title: ThreadLocal
summary: ThreadLocal | 异步线程间变量传递
tags:
- Stream
  categories:
- 04-Programming-Languages
- Java
  date: 2023-06-07 16:00:00
---

### Spring请求的一次性缓存

Spring项目中部分参数配置不想通过接口层层传入, 使用一次性缓存在本次请求中全局共用这些缓存

```java
public class ContextCacheHolder {
    private static final ThreadLocal<HashMap<String, Object>> LOCAL_CACHE = new ThreadLocal<>();
}
```

### Stream 并行流使用一次性缓存失败

```java
keys.parallelStream().forEach(e -> // dosometing )
```
+ [参考链接] https://blog.csdn.net/Laugh_xiaoao/article/details/122521584
