---
title: AOP理论与实战
keywords: AOP理论与实战
summary: AOP理论与实战
tags:
  - spring
  - AOP
categories:
  - 06-Frameworks-and-Components
  - Spring
date: 2022-10-19 17:30:19
---
  
### 父类的切面继承类重写后失效的问题

+ 项目上开启切面继承类有效

```java
@EnableAspectJAutoProxy(exposeProxy = true)
```

+ 子类调用不使用super, 从切面上下文中获取
```java
// return super.plusImportExcel(viewId, file, listenerBuilder);
return ((PublicServiceImpl) AopContext.currentProxy()).plusImportExcel(viewId, file, listenerBuilder);
```
