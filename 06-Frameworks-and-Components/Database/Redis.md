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

## Redis 国内镜像源

https://mirrors.huaweicloud.com/redis/

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
config set requirepass 123456 # redis-cli 进入服务器后处理
## 卸载redis
sudo apt-get purge --auto-remove redis-server
```

+ [Ubuntu安装特定版本Redis](https://blog.51cto.com/u_16213452/12150139)

```shell
## Ubuntu 上安装特定版本的 Redis
wget https://mirrors.huaweicloud.com/redis/redis-7.4.5.tar.gz
## 解压缩
tar xzf redis-7.4.5.tar.gz
cd redis-7.4.5
## 编译
make
## 编译完成后，使用运行测试，确保redis正常工作
make test
## 测试通过可以正常安装Redis
sudo make install # 安装过程中发现权限不足, 使用 make clean 之后重新安装
## 配置Redis; 默认配置信息`/etc/redis/redis.conf`
mkdir /etc/redis 
cp redis.conf /etc/redis 
vim /etc/redis/redis.conf 
# >>> 允许外网访问绑定: bind 0.0.0.0 # 不能是 127.0.0.1
# >>> 允许外网访问: protected-mode no
# >>> 配置服务: supervised systemd 
# >>> 配置密码: requirepass bjqqmzpWXNOECN!@#
## 使redis能够作为服务运行
vim /etc/systemd/system/redis.service # 粘贴后文文件内容
## 启动redis服务并设置开机自启动
# 重新加载配置文件
sudo systemctl daemon-reload
# 启动服务
sudo systemctl start redis.service
# 重启服务
sudo systemctl restart redis.service
# 检查服务状态
sudo systemctl status redis.service
# 服务自启动
sudo systemctl enable redis.service
# 查看日志详情
journalctl -u redis.service
# 创建用户和组
sudo groupadd -r redis
sudo useradd -r -g redis -s /bin/false redis
# 检测用户是否存在
id redis
```

+ redis.service

```config
[Unit]
Description=Redis In-Memory Data Store
After=network.target

[Service]
ExecStart=/usr/local/bin/redis-server /etc/redis/redis.conf
ExecStop=/usr/local/bin/redis-cli shutdown
User=redis
Group=redis
Restart=always
StartLimitIntervalSec=60  # 设置启动限制的时间窗口为60秒
StartLimitBurst=5         # 在时间窗口内允许的最大启动次数为5次

[Install]
WantedBy=multi-user.target
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