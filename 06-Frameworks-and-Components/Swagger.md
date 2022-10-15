---
title: Swagger
summary: Swagger-自动生成RESTful文档
tags:
  - java
  - swagger
categories:
  - 06-Frameworks-and-Components
date: 2022-10-15 13:30:22
---

Swagger [官方文档](https://swagger.io/docs/specification/2-0/describing-parameters/)

## 使用第三方工具包

### 增加包版本

```xml

<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>swagger-bootstrap-ui</artifactId>
    <version>${swagger-bootstrap-ui.version}</version>
</dependency>
```

### 删除原来swagger-ui包

```xml

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>${springfox-swagger2.version}</version>
</dependency>
```

### 配置类加上注解 ```@EnableSwaggerBootstrapUI ```

### yml配置登录名密码

```
# swagger配置
swagger:
  production: false
  basic:
    enable: true
    username: cplh_swagger
    password: ************
```

### swagger关闭ui放行

```
// filterChainDefinitionMap.put("/swagger-ui.html", ANON);
```

### swagger地址由原来的 ```/swagger-ui.html``` 变更为 ```/doc.html```

### cloud升级方法

+ cloud每个服务都应当升级swagger访问加密
+ 网关需要放行对应的资源与请求

## 自定义简单校验(基于http安全协议)

+ 通过拦截器自定义拦截逻辑

```java

@Slf4j
@WebFilter(urlPatterns = {"/swagger*", "/swagger/*", "/swagger-resources/*", "/v3/*"}, filterName = "swaggerSecurityFilter")
public class SwaggerSecurityFilter implements Filter {
    public static final String SWAGGER_SECURITY_SESSION = "swaggerSecuritySession";
    @Value("${system-config.swagger-security:true}")
    private boolean security = true;
    @Value("${system-config.swagger-username:cplh_swagger}")
    private String username;
    @Value("${system-config.swagger-password:ZHbootvue@swagger}")
    private String password;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (this.security) {
            String swaggerSessionValue = (String) servletRequest.getSession().getAttribute(SWAGGER_SECURITY_SESSION);
            if (StringUtils.isNotBlank(swaggerSessionValue) && StringUtils.equals(swaggerSessionValue, this.username)) {
                // 如果session中有值且等于密码, 取消拦截
                chain.doFilter(request, response);
            } else {
                String auth = servletRequest.getHeader("Authorization");
                if (StringUtils.isNotBlank(auth)) {
                    String userAndPass = new String(Base64.getDecoder().decode(auth.substring(6)));
                    String[] upArr = userAndPass.split(":");
                    if (upArr.length != 2) {
                        this.writeForbiddenCode(httpServletResponse);
                        return;
                    }

                    String iptUser = upArr[0];
                    String iptPass = upArr[1];
                    if (StringUtils.isNotBlank(iptUser) && StringUtils.isNotBlank(iptPass)
                            && StringUtils.equals(iptUser, this.username) && StringUtils.equals(iptPass, this.password)) {
                        servletRequest.getSession().setAttribute(SWAGGER_SECURITY_SESSION, this.username);
                        chain.doFilter(request, response);
                        return;
                    }

                    this.writeForbiddenCode(httpServletResponse);
                    return;
                }
                this.writeForbiddenCode(httpServletResponse);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private void writeForbiddenCode(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        httpServletResponse.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"input Swagger Basic userName & password \"");
        httpServletResponse.getWriter().write("You do not have permission to access this resource");
    }
}
```

+ 增加请求头需要的通用参数

```java

@Configuration
@EnableOpenApi
@ConditionalOnProperty(prefix = SystemConfig.PREFIX, name = "swagger-open", havingValue = "true")
public class Swagger2Config implements WebMvcConfigurer {
    @Bean
    public Docket createRestApi(SystemConfig systemConfig) {
        //添加head参数配置start
        List<RequestParameter> pars = new ArrayList<>();
        pars.add(new RequestParameterBuilder().in(ParameterType.HEADER).required(false)
                .name(CommonConstant.ACCESS_TOKEN).description(CommonConstant.ACCESS_TOKEN).build());
        pars.add(new RequestParameterBuilder().in(ParameterType.HEADER).required(false)
                .name(SysClientConfigUtils.CLIENT_ID).description(SysClientConfigUtils.CLIENT_ID).build());
        pars.add(new RequestParameterBuilder().in(ParameterType.HEADER).required(false)
                .name(SysClientConfigUtils.CLIENT_SECRET).description(SysClientConfigUtils.CLIENT_SECRET).build());
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(systemConfig))
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(pars);
    }

    private ApiInfo apiInfo(SystemConfig systemConfig) {
        return new ApiInfoBuilder()
                //大标题
                .title("zh-boot 接口平台")
                //描述
                .description("zh-boot 接口平台")
                //版本号
                .version(systemConfig.getVersion())
                .build();
    }
}
```

