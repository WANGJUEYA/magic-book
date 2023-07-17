---
title: HTTP-Proxy-Servlet
summary: HTTP-Proxy-Servlet
tag: 
  - spring
  - Proxy
categories:
  - 07-Programming-Assistant
  - Proxy
date: 2023-07-14 09:00:00
---

## 应用场景

> 外网用户 A , 内网服务器 B (处理用户认证), 和资源提供服务器 C (无用户认证)
> 对于 A 需要访问 C 资源, 且需要保证访问权限控制, 故通过 B 完成接口代理。
> 在其他 A 不能直接访问 C, 但 B 能访问 C 的服务器也能使用该方案处理。

## Spring集成`HTTP-Proxy-Servlet`

### maven集成

+ 最新版本为 2.20.0; jdk8不适用

```xml
<dependency>
    <groupId>org.mitre.dsmiley.httpproxy</groupId>
    <artifactId>smiley-http-proxy-servlet</artifactId>
    <version>1.12.1</version>
</dependency>
```

### 增加yml配置
```yaml
proxy:
  configs:
    - key: yozo # 唯一标识的路径
      log: true # 是否打印日志
      servlet: # 请求服务地址, 为空时拼接 `/{key}/*`
      uri: ${yozosoft.prefix}
```

### 属性加载

```java
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = KmsProxyServletProperty.PREFIX)
public class ProxyServletProperty {
    public static final String PREFIX = "proxy";

    private List<ProxyAtom> configs;

    @Data
    public static class ProxyAtom {
        public String getServletName() {
            return String.format("%sProxyServlet", key);
        }

        /**
         * 代理唯一标识
         */
        private String key;

        /**
         * 是否开启日志, 默认关闭
         */
        private boolean openLog;

        /**
         * 本地地址(为空时返回 /{key}/*)
         */
        private String location;

        public String getLocation() {
            if (StringUtils.isBlank(location)) {
                if (StringUtils.isBlank(key)) {
                    throw new NullPointerException("Proxy key must be config");
                }
                return String.format("/%s/*", key);
            } else {
                return location;
            }
        }

        /**
         * 代理转发的地址
         */
        private String uri;
    }
}
```

### 列表代理批量增加

```java
@Slf4j
@Configuration
public class ProxyServletConfig implements ServletContextInitializer {
    @Resource
    private ProxyServletProperty property;

    @Override
    public void onStartup(ServletContext servletContext) {
        Optional.ofNullable(property.getConfigs()).orElse(new ArrayList<>()).forEach(item -> {
            ServletRegistration initServlet = servletContext.addServlet(item.getServletName(), ProxyServlet.class);
            initServlet.setInitParameter(ProxyServlet.P_TARGET_URI, item.getUri());
            initServlet.setInitParameter(ProxyServlet.P_LOG, String.valueOf(item.isOpenLog()));
            initServlet.addMapping(item.getLocation());
        });
    }
}
```

## 参考链接

+ [HTTP-Proxy-Servlet GitHub](https://gitcode.net/mirrors/mitre/HTTP-Proxy-Servlet)
+ [HTTP-Proxy-Servlet 集成文档](https://blog.csdn.net/qq_36256590/article/details/129710798)
+ [HTTP-Proxy-Servlet 注册多个代理](https://blog.csdn.net/zoeou/article/details/126967315)
