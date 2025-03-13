---
title: Docker
summary: Docker介绍及实战
tags:
    - docker
    - 自动部署
categories:
  - 07-Programming-Assistant
date: 2022-10-15 13:20:35
---
  
官网: https://docs.docker.com/get-started/
菜鸟教程: https://www.runoob.com/docker/docker-architecture.html

## 常用命令

### 查看启动的任务

```shell
docker ps
```

### 查看所有任务

```shell
docker ps -a
```

### 启动/停止任务

```shell
docker start $contaniner-id
docker stop $contaniner-id
```

### 其他常用命令

```shell
docker -v # 查看当前版本
```


## Windows10 安装 Docker 的详细教程

+ [官方下线地址](https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe?utm_source=docker)

## CentOS 安装 Docker

### 查看当前系统内核

**重要提示: docker内核版本必须是3.10+以上版本**

```shell
uname -r # 3.10.0-957.el7.x86_64
```

### 使用yum安装docker

```shell
# 卸载老版本docker及其相关依赖
yum remove docker docker-common container-selinux docker-selinux docker-engine
# 更新yum
yum update # 更新过程中有下载错误的依赖, 先按照提示忽略掉
# 安装 yum-utils，它提供了 yum-config-manager，可用来管理yum源
yum install -y yum-utils
# yum安装软件报错 `Configuration: OptionBinding with id “failovermethod” does not exist`
# vi CentOS-Epel.repo
# 备份文件报错 `Removing leading `/' from member names`
# tar zcPf /home/yum.repos.d.tar.gz /etc/yum.repos.d/
# 查看已安装软件版本
rpm -qa | grep yum-utils
# 设置yum源
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
# 更新索引
yum makecache fast
# 安装 docker-ce
yum install docker-ce docker-ce-cli containerd.io # 安装
# 启动Docker服务并设置开机自启
sudo systemctl start docker
sudo systemctl enable docker
# 验证安装
docker run hello-world
# 重启docker
systemctl restart docker
# 查看所有服务
Docker stats -a # --no-stream 展示当前状态就直接退出了，不再实时更新
# 查看所有镜像
docker ps
# 进入容器控制台
docker exec -it <容器名称或ID> /bin/bash
## 进入postgresql
psql -U <数据库用户名> -d <数据库名>
# 如果镜像使用默认配置（如官方镜像）：
psql -U postgres -d postgres
# 网易：http://hub-mirror.c.163.com
# 中科大镜像地址：http://mirrors.ustc.edu.cn/
# 中科大github地址：https://github.com/ustclug/mirrorrequest
# Azure中国镜像地址：http://mirror.azure.cn/
# Azure中国github地址：https://github.com/Azure/container-service-for-azure-china
# DockerHub镜像仓库: https://hub.docker.com/ 
# 阿里云镜像仓库： https://cr.console.aliyun.com 
# google镜像仓库： https://console.cloud.google.com/gcr/images/google-containers/GLOBAL （如果你本地可以翻墙的话是可以连上去的 ）
# coreos镜像仓库： https://quay.io/repository/ 
# RedHat镜像仓库： https://access.redhat.com/containers
# 创建或修改Docker配置文件
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["http://mirrors.ustc.edu.cn/"]
}
EOF

# 重启 docker 服务
sudo systemctl daemon-reload
sudo systemctl restart docker
# 查看镜像源
docker info | grep "Registry Mirrors"
```

### 异常问题处理

#### Docker 安装错误 repodata/repomd.xml: [Errno 14] HTTPS Error 404 - Not Found Trying other mirror.

```shell
vim /etc/yum.repos.d/docker-ce.repo
```

+ 修改文件中的下载地址
```repo
[docker-ce-stable]
name=Docker CE Stable - $basearch
# baseurl=https://download.docker.com/linux/centos/$releasever/$basearch/stable
baseurl=https://download.docker.com/linux/centos/7/$basearch/stable
```
+ 修改完成后重新安装

#### 误删除基础仓库

```shell
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
```

## 参考链接

+ [Windows10 Docker安装详细教程](https://zhuanlan.zhihu.com/p/441965046)
