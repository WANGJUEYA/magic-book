---
title: Minio
summary: Minio - 简单高效的文件存储服务
tags:
  - minio
  - java
categories:
  - 06-Frameworks-and-Components
date: 2022-10-15 13:50:03
---

## minio部署

### 下载mc工具

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

## minio使用

+ [aws分片上传接口](https://docs.aws.amazon.com/AmazonS3/latest/API/API_CompleteMultipartUpload.html)
+ [使用aws接口操作minio](https://docs.aws.amazon.com/zh_cn/sdk-for-java/v1/developer-guide/examples-s3-transfermanager.html)
+ [Minio官方文档](https://min.io/docs/minio/linux/developers/java/minio-java.html)
+ [Minio官方文档-中文](http://docs.minio.org.cn/docs/master/java-client-api-reference)

### minio实现分片上传(同时实现秒传)

#### 引入依赖

```pom
<!-- aws s3 -->
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>s3</artifactId>
</dependency>
```

#### 逻辑

+ 根据前端传回的文件大小及md5比较文件是否已存在, 已存在则秒传, 未存在创建预上传链接

+ 前端根据每个分片地址, 直连文件服务器上传每个分片(当然也可以经过后台转发)

+ 前端所有分片上传完毕后通知后端进行文件合并

#### 后端代码

```java
public class AwsS3Option {
    /**
     * 获取分片上传的uploadId
     */
    public String createMultipartUpload(String bucketName, String objectPath) {
        CreateMultipartUploadResponse createMultipartUploadResponse = awsS3Client.createMultipartUpload(CreateMultipartUploadRequest.builder()
                .bucket(bucketName).key(objectPath).build());
        return createMultipartUploadResponse.uploadId();
    }

    /**
     * 创建某一分片上传链接
     */
    public URL getUploadPartUrl(String uploadId, String bucketName, String objectPath, int contentNumber, long contentSize) {
        PresignedUploadPartRequest partRequest = awsS3Presigner.presignUploadPart(UploadPartPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .uploadPartRequest(UploadPartRequest.builder()
                        .uploadId(uploadId)
                        .bucket(bucketName)
                        .key(objectPath)
                        .partNumber(contentNumber)
                        .contentLength(contentSize)
                        .build())
                .build());
        return partRequest.url();
    }

    /**
     * 合并上传流
     *
     * @param partMd5 <partNumber, md5>
     * @return 返回最终结果的md5值
     */
    public String completeMultipartUpload(Map<Integer, String> partMd5, String uploadId, String bucketName, String objectPath) {
        List<CompletedPart> completedPartList = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : partMd5.entrySet()) {
            completedPartList.add(CompletedPart.builder().partNumber(entry.getKey()).eTag(entry.getValue()).build());
        }

        awsS3Client.completeMultipartUpload(CompleteMultipartUploadRequest.builder()
                .uploadId(uploadId).bucket(bucketName).key(objectPath)
                .multipartUpload(CompletedMultipartUpload.builder().parts(completedPartList).build())
                .build());
        HeadObjectResponse headObjectResponse = awsS3Client.headObject(HeadObjectRequest.builder().bucket(bucketName).key(objectPath).build());
        return headObjectResponse.eTag();
    }
}
```

#### 前端代码

mixin混入后使用 `that.uploadPartBefore(file).then(res => {})`调用

```js
import {formData, postAction} from '@/api/manage'
import SparkMD5 from 'spark-md5'
import {completeMultipartUpload, createMultipartUpload, preUploadPart} from '@/api/api'

/**
 * 一般共有方法 提取
 */
export const ZVueUploadPartMixin = {
    props: {
        createMultipartUploadUrl: {
            type: String,
            default: createMultipartUpload
        },
        preUploadPartUrl: {
            type: String,
            default: preUploadPart
        },
        completeMultipartUploadUrl: {
            type: String,
            default: completeMultipartUpload
        }
    },
    data() {
        return {
            progressFormat: 'Done',
            progressData: 0
        }
    },
    // 方法执行
    methods: {
        hexStringMd5(hex) {
            const buf = new ArrayBuffer(hex.length / 2)
            const int32View = new Int8Array(buf)
            let len = hex.length
            if (len % 2 !== 0) {
                return null
            }
            len /= 2
            for (let i = 0, pos = 0; i < len; i++, pos += 2) {
                let s = hex.substr(pos, 2)
                int32View[i] = parseInt(s, 16)
            }
            return md5(buf)
        },
        // 上传前md5计算
        uploadPartBefore(file) {
            let that = this
            return new Promise((resolve, reject) => {
                if (file.size <= 0) {
                    reject(new Error('不允许上传空文件!'))
                }
                // 上传中(数据分片、计算md5-用于秒传)
                that.createFilePart(file).then(fileMd5 => {
                    // 准备预上传id
                    let fileName = file.webkitRelativePath
                    fileName = fileName && fileName.length > 0 ? fileName : file.name
                    // 解决[]被tomcat拦截的问题; 解决 & # 无法上传的问题, 改用post方法
                    let param = {
                        dataTable: this.dataTable,
                        dataId: this.dataId,
                        type: this.type,
                        fileSize: file.size,
                        fileMd5: fileMd5,
                        fileName: fileName
                    }
                    postAction(this.createMultipartUploadUrl, formData(param)).then(res => {
                        if (res.success) {
                            // 预处理
                            file.preTreatment = true
                            // 秒传
                            if (res.result.saveFlag === 'TA') {
                                that.progressFormat = 'second!'
                                that.progressData = 100
                                resolve(res)
                            } else {
                                // 分片上传
                                file.requestData = res.result
                                that.progressFormat = 'Uploading!'
                                that.uploadPartProcess(file, 0).then(res => resolve(res)).catch(e => reject(e))
                            }
                        } else {
                            reject(res.message)
                        }
                    })
                })
            })
        },
        // 生成文件切片, 计算md5值
        createFilePart(file) {
            return new Promise(resolve => {
                let partSize = this.partSize
                const partBlob = {}
                let totalSize = file.size
                let subSize = totalSize % partSize
                let partNum = Math.floor(totalSize / partSize) + (subSize > 0 ? 1 : 0)
                const partsMd5 = new Array(partNum)

                for (let index = 0; index < partNum; index++) {
                    let currentSize = index === partNum - 1 && subSize > 0 ? subSize : partSize
                    const blob = file.slice(partSize * index, partSize * index + currentSize)
                    partBlob[index] = blob

                    // 计算分片的md5值
                    let fileReader = new FileReader()
                    fileReader.readAsArrayBuffer(blob)
                    fileReader.onload = e => {
                        partsMd5[index] = new SparkMD5.ArrayBuffer().append(e.target.result).end()
                        // 最后一次计算总的大小
                        let currentLength = Object.keys(partsMd5).length
                        if (currentLength === partNum) {
                            file.partTotal = partNum
                            file.partBlob = partBlob
                            file.partsMd5 = partsMd5
                            // 返回总的md5值
                            let fileMd5Hex = ''
                            for (let i = 0; i < partNum; i++) {
                                fileMd5Hex = fileMd5Hex + partsMd5[i]
                            }
                            file.md5Counting = false
                            resolve(this.hexStringMd5(fileMd5Hex) + '-' + partNum)
                        } else {
                            // md5增加提示信息, 防止md5时间过长用户以为发生意外(7次刷新一次, 防止过于频繁)
                            if (currentLength < partNum - 5 && currentLength % 7 === 0) {
                                file.md5Counting = true
                                this.progressData = 1
                                this.progressFormat = '(计算md5: ' + (currentLength + 1) + '/' + partNum + ')'
                            }
                        }
                    }
                }
            })
        },
        // 上传接口(启动分片上传)
        uploadPartProcess(file, contentIndex) {
            let that = this
            if (contentIndex >= file.partTotal) {
                return that.uploadPartAfter(file)
            } else {
                // 调用分片上传或合并
                return new Promise((resolve, reject) => {
                    postAction(this.preUploadPartUrl, formData({
                        filePath: file.requestData.filePath,
                        uploadId: file.requestData.id,
                        contentIndex: contentIndex + 1,
                        contentSize: file.partBlob[contentIndex].size,
                        contentMd5: file.partsMd5[contentIndex]
                    })).then(res => {
                        if (res.success) {
                            // 上传分片
                            let xhr = new XMLHttpRequest()
                            xhr.open('put', res.result)
                            xhr.overrideMimeType('application/octet-stream')
                            // 上传进度
                            xhr.upload.onprogress = e => {
                                if (e.lengthComputable) {
                                    that.progressData = (((contentIndex * that.partSize + e.loaded) / file.size) * 100).toFixed(2) * 1
                                }
                            }
                            // 上传结束后启动下一个分片
                            xhr.onreadystatechange = () => {
                                if (xhr.readyState === 4 && xhr.status === 200) {
                                    that.uploadPartProcess(file, contentIndex + 1).then(res => resolve(res)).catch(e => reject(e))
                                }
                            }
                            xhr.send(file.partBlob[contentIndex])
                        } else {
                            // 获取分片链接失败
                            reject(res.message)
                        }
                    })
                })
            }
        },
        // 上传结束后合并数据库
        uploadPartAfter(file) {
            let that = this
            // 合并分片
            let param = file.requestData
            param.partsMd5 = file.partsMd5
            param.fileSort = ''
            param.sysCreateTime = ''
            param.sysUpdateTime = ''
            return new Promise((resolve, reject) => {
                postAction(that.completeMultipartUploadUrl, formData(param)).then(res => {
                    if (res.success) {
                        that.progressFormat = 'Done!'
                        that.progressData = 100
                        resolve(res)
                    } else {
                        reject(res.message)
                    }
                }).catch(e => reject(e))
            })
        }
    },
    computed: {}

}
```
