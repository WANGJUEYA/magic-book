---
title: Active Directory (AD域)
date: 2022-10-15 12:11:33
summary: WindowsServer AD域搭建 与 SpringBoot集成

tags:
   - AD域
   - spring

categories:
   - 操作系统
   - Windows Server
   - spring
---
  
## LADP

1. Lightweight Directory Access Protocol; 基于目录服务的轻量目录访问协议

2. 目录中的信息按照树型结构进行组织

3. Distinguished Name (DN)

   DN是用来引用条目的，DN相当于关系数据库表中的关键字（Primary Key）

   属性（Attribute）由类型（Type）和一个或多个值（Values）组成，相当于关系数据库中的字段（Field）由字段名和数据类型组成，只是为了方便检索的需要，LDAP中的Type可以有多个Value

Windows AD(Active Directory)域应该是LDAP的一个应用实例，而不应该是LDAP本身。Windows AD域的用户、权限管理应该是微软公司使用LDAP存储了一些数据来解决域控这个具体问题，AD域提供了相关的用户接口，我们可以把AD域当做微软定制的LDAP服务器。Active Directory先实现一个LDAP服务器，然后自己先用这个LDAP服务器实现了自己的一个具体应用。

## 搭建AD域服务器

[AD部署官方文档](https://docs.microsoft.com/zh-cn/windows-server/identity/ad-ds/deploy/ad-ds-deployment)
[C#实现AD域验证登录](https://www.cnblogs.com/BugBrother/p/6760504.html)

### 配置虚拟机服务ip

如果你的域控制器在虚拟机上，需要设置你的虚拟机网络连接方式为桥接模式，虚拟机处于无网状态，外部无法访问。

+ 第一步：添加角色功能=>安装'Active Directory域服务'
+ 第二步：配置服务器的ip地址
+ 第三步：开始配置域服务
  - 服务器域名192.168.6.128 
  - 根域名: adserver.cn
  - 目录服务还原模式(DSRM)密码:  zhboot@123456 
  - NetBIOS名称: ADSERVER

### 异常排插与解决

> :error: 域控制器升级的先决条件验证失败。
+ Active Directory 域服务所需的 TCP 端口已在该计算机上使用。你必须删除或重新配置当前使用这些端口(88、389、636、3268、3269)的服务。
+ 重新安装虚拟机及服务

## spring boot 访问AD域
pom依赖

```pom
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-ldap</artifactId>
</dependency>
```

yml配置

```yaml
spring:
  ldap:
    # 389 (该端口有可能报错 Unprocessed Continuation Reference(s))
    urls: ldap://192.168.6.128:3268
    base: dc=adserver,dc=cn
    # 下面为默认连接用户不配置将会报错 In order to perform this operation a successful bind must be completed on the connection.
    username: administrator@adserver.cn
    password: 
```

AD域用户实体

```java
@Data
@Entry(objectClasses = {})
public class LdapUser {
    @Id
    @JsonIgnore
    private Name id;
    @DnAttribute(value = "uid", index = 3)
    private String uid;
    @Attribute(name = "cn")
    private String commonName;
    @Attribute(name = "sn")
    private String suerName;
    private String userPassword;
}
```

repository
```java
@Repository
public interface NibeiLdapUserRepository extends LdapRepository<LdapUser> {
}
```
用户登录名密码校验

```java
// 校验AD用户是否正确 UserPrincipalName:[登录名@域名] sAMAccountName:[登录名]
String userPrincipalNameKey = "UserPrincipalName";
if (!ldapTemplate.authenticate("", new EqualsFilter(userPrincipalNameKey, ldapUserLogin.getUsername()).toString(), ldapUserLogin.getPassword())) {
	// 如果用户校验错误, 则抛出异常
    throw new BusinessException("AD用户校验失败");
}
```

## 参考连接

+ 服务搭建与服务集成

[Spring Boot中使用LDAP来统一管理用户信息](https://blog.csdn.net/HD243608836/article/details/120068640)

[SpringBoot集成 Windows2012 AD 认证服务](https://blog.csdn.net/jianxia801/article/details/88883874)

+ 异常处理

> # [LDAP: error code 49 - 80090308: LdapErr: DSID-0C0903C5, comment: AcceptSecurityContext error, data 52e, v2580

- When using the port 389 in the URL, change it to 3268.
- When using the port 636 in the URL (when connecting via SSL), change it to 3269.

More information about why it works this way can be found either in the [Microsoft Active Directory forums](https://social.technet.microsoft.com/Forums/en-US/e52b9154-b93a-4a3b-b6f2-0285f932da14/389-and-3268-port-difference?forum=winserverDS) or on [Stack Overflow](https://stackoverflow.com/questions/16412236/how-to-resolve-javax-naming-partialresultexception).

> [LDAP: error code 1 - 000004DC: LdapErr: DSID-0C090728, comment: In order to perform this operation a successful bind must be completed on the connection., data 0, v2580

+ 针对 ldapTemplate 必须有个可用的用户才行(本身用这个工具去验证用户的正确性; 故还应当找别的方法去处理)
+ 在实际生产过程中, 管理员密码不被交出且密码定时过期, 应当优化为重新注入配置bean连接AD域
  - [Spring Boot 2.0 项目实现自同步AD域账号](https://blog.csdn.net/weixin_34124577/article/details/92443749)
  - [Spring Boot集成AD域实现统一用户认证](https://blog.csdn.net/garyond/article/details/80224221)

## 附录

```java
package com.zngd.welding.autowelding.controller;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class AdTest {

    public static void connect(String host,String post,String username,String password) {
        DirContext ctx=null;
        Hashtable<String,String> HashEnv = new Hashtable<String,String>();
        HashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别(none,simple,strong)
        HashEnv.put(Context.SECURITY_PRINCIPAL, username); //AD的用户名
        HashEnv.put(Context.SECURITY_CREDENTIALS, password); //AD的密码
        HashEnv.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
        HashEnv.put("com.sun.jndi.ldap.connect.timeout", "3000");//连接超时设置为3秒
        HashEnv.put(Context.PROVIDER_URL, "ldap://" + host + ":" + post);// 默认端口389
        try {
            ctx = new InitialDirContext(HashEnv);// 初始化上下文
            System.out.println("身份验证成功!");
        } catch (AuthenticationException e) {
            System.out.println("身份验证失败!");
            e.printStackTrace();
        } catch (javax.naming.CommunicationException e) {
            System.out.println("AD域连接失败!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("身份验证未知异常!");
            e.printStackTrace();
        } finally{
            if(null!=ctx){
                try {
                    ctx.close();
                    ctx=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        AdTest.connect("172.17.1.1", "389", "liguowei@waopco.com", "");
    }
}
```

