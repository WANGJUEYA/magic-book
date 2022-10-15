---
title: Minio
date: 2022-10-15 13:50:03
summary: Minio - 简单高效的文件存储服务
tags:
    - minio
    - java
---

下载mc工具

```shell
wget https://dl.min.io/client/mc/release/linux-amd64/mc

# windows
https://dl.min.io/client/mc/release/windows-amd64/mc.exe
```

### mc 配置 minio服务端(使用默认local即可)

```shell
./mc config host ls
./mc config host add minio-server http://127.0.0.1:9000 minioadmin minioadmin
./mc config host remove minio-server
```

### 创建bucket

```shell
./mc mb minio-server/my-bucket
```

### 查看bucket

```shell
./mc ls minio-server
```

### 上传文件

```shell
# 上传一个文件
./mc cp /ect/host minio-server/my-bucket
# 上传一个文件夹
./mc cp /etc minio-server/my-bucket
```

### 删除文件

```shell
# 删除文件
./mc rm minio-server/my-bucket/ect/host
# 删除文件夹
./mc rm minio-server/my-bucket/ect
```

### 删除bucket

```shell
# 删除没有文件的桶
./mc rb minio-server/my-bucket

# 删除有文件的桶
./mc rb minio-server/my-bucket --force
```

