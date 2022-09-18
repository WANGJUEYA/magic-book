> 三方依赖包

1. 增加包版本

```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>swagger-bootstrap-ui</artifactId>
    <version>${swagger-bootstrap-ui.version}</version>
</dependency>
```

2. 删除原来swagger-ui包

```xml
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>${springfox-swagger2.version}</version>
</dependency>
```

2. 配置类加上注解 ```@EnableSwaggerBootstrapUI ```
3. yml配置登录名密码

```
# swagger配置
swagger:
  production: false
  basic:
    enable: true
    username: cplh_swagger
    password: ************
```

4. swagger关闭ui放行

```
// filterChainDefinitionMap.put("/swagger-ui.html", ANON);
```

5. swagger地址由原来的 ```/swagger-ui.html``` 变更为 ```/doc.html```

> cloud升级方法

注意cloud每个服务都应当升级swagger访问加密

```
https://swagger.io/docs/specification/2-0/describing-parameters/
```



```
Ctrl + h
```

