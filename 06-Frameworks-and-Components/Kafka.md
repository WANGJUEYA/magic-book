---
title: Kafka部署及spring集成
keywords: Kafka部署及spring集成
summary: Kafka部署及spring集成
tags:
  - kafka
  - spring
date: 2022-10-16 10:43:30
---

## 基础介绍

### kafka入门及其使用

Kafka是由LinkedIn开发的一个分布式基于发布/订阅的消息系统，使用Scala编写，它以可水平扩展和高吞吐率而被广泛使用。

[kafka中文文档](http://kafka.apachecn.org/)

[简单说明什么是kafka](http://orchome.com/kafka/index)

### 关于kafka背景

最初是LinkedIn的一个内部基础设施系统，发现数据库难以处理持续数据流，因此产生了kafka，一开始用于社交网络的实时应用和数据流中。

```
可以认为kafka是一个流平台:在这个平台可以发布和订阅流.并将它保存、处理
可以作为消息系统、有点像实时版的hadoop，支持集群、高性能拥有诸多优点
```

##### 消息中间件是干啥用的，有啥好处

通过消息队列达到将业务异步解耦，设计变得更简单可以分布式，通过消息一致性【只要不丢失消息】保证数据最终到用户。增加业务系统异步能力，较小并发问题。比如验证码发送到用户。

```
【生产】和【消费】速度或稳定性不一致是使用消息中间件的重要原因
```

### kafka原理

##### 几个基本术语

##### Topic

Kafka将消息种子(Feed)分门别类，每一类的消息称之为一个主题(Topic).kafka集群存储消息是以top为类别记录的

##### Producer

发布消息的对象称之为主题生产者(Kafka topic producer)

##### Consumer

订阅消息并处理发布的消息的种子的对象称之为主题消费者(consumers)

##### Broker

已发布的消息保存在一组服务器中，称之为Kafka集群。集群中的每一个服务器都是一个代理(Broker). 消费者可以订阅一个或多个主题（topic），并从Broker拉数据，从而消费这些已发布的消息。

#### 4个核心API

应用程序使用 `Producer API` 发布消息到1个或多个topic（主题）。

应用程序使用 `Consumer API` 来订阅一个或多个topic，并处理产生的消息。

应用程序使用 `Streams API` 充当一个流处理器，从1个或多个topic消费输入流，并生产一个输出流到1个或多个输出topic，有效地将输入流转换到输出流。

`Connector API`允许构建或运行可重复使用的生产者或消费者，将topic连接到现有的应用程序或数据系统。例如，一个关系数据库的连接器可捕获每一个变化。

## 应用部署

+ 部署 zookeeper + kafka
+ 配置kafka SASL验证

## Spring集成kafka

### 方式1: spring-cloud-stream-binder-kafka

引入依赖包

```pom
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>
```

yml配置(无SASL验证)
```yaml
spring:
  cloud:
    stream:
      kafka:
        binder:
          brokers: ip:port
        output:
          destination: websocketMsg
          content-type: application/json
        input:
          destination: websocketMsg
          content-type: application/json
```
yml配置(SASL验证)

```yaml
spring:
  cloud:
    stream:
      kafka:
        binder:
          brokers: ip:port
          configuration:
            sasl.mechanism: PLAIN
            security.protocol: SASL_PLAINTEXT
            sasl.jaas.config: 'org.apache.kafka.common.security.plain.PlainLoginModule required username="kafka" password="password";'
      bindings:
        output:
          destination: websocketMsg
          content-type: application/json
        input:
          destination: websocketMsg
          content-type: application/json
```

### 方式二 在config下新建文件jaas.conf

```
KafkaServer {
    org.apache.kafka.common.security.plain.PlainLoginModule required
    username="admin"
    password="admin"
    user_admin="admin";
};
```