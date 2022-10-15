---
title: HTTP-Status-codes
summary: Http状态码及用例
tags:
  - 大视频在线预览
  - 断点续传(下载)
  - java
  - StreamingResponseBody
categories:
  - 03-Computer-Network
date: 2022-10-15 14:09:24
---
## 什么是HTTP状态码

**HTTP状态码**是服务端返回给客户端（因为这里是web开发，这里的客户端指浏览器客户端）的`3位数字代码`。

这些状态码相当于浏览器和服务器之间的对话信息。它们相互沟通两者之间的事情是正常运行了还是运行失败了或者发生了一些其他的事情（如Continue）。了解状态码有助于你快速的诊断错误，减少网站的停机时间等等。

## 状态码分类

状态码共分为五类，以1-5数字开头进行标识，如下：

- **1xxs - 信息性**：服务器正在处理请求。
- **2xxs - 成功信息**：请求已经完成，服务器向浏览器提供了预期的响应。
- **3xxs –重定向**：你的请求被重定向到了其他地方。服务器收到了请求，但是有某种重定向。
- **4xxs – 客户端错误**：客户端发生错误，导致服务器无法处理请求。
- **5xxs – 服务端错误**：客户端发出了有效的请求，但是服务器未能正确处理请求。

### 1xxs状态码

#### **100 Continue**：表明目前为止，所有的请求内容都是可行的，客户端应该继续请求，如果完成，则忽略它。
#### **101 Switching Protocol**：该状态码是响应客户端`Upgrade`标头发送的，并且指示服务器也正在切换协议。
#### **103 Early Hints**：主要用于与`Link`链接头一起使用，以允许用户代理在服务器仍在准备响应时开始预加载资源。

### 2xxs状态码

#### **200 OK**：请求成功。成功的含义取决于HTTP方法：

- `GET`：资源已被提取并在消息正文中传输。
- `HEAD`：实体标头位于消息正文中。
- `POST`：描述动作结果的资源在消息体中传输。
- `TRACE`：消息正文包含服务器收到的请求信息。（方法不安全，一般不用）

说到了HTTP的方法，可以戳[HTTP请求方法](https://link.zhihu.com/?target=https%3A//www.runoob.com/http/http-methods.html)这个解析教程来了解一下。

#### **201 Created**：请求已经成功，并因此创建了一个新的资源。这通常是在`PUT`或`POST`请求之后发送的响应。
#### **202 Accepted**：请求已经接收到，但是没有响应，没有结果。意味着不会有一个异步的响应去表明当前请求的结果，预期另外的进程和服务去处理请求，或者批处理。
#### **204 No Content**：服务器成功处理了请求，但不需要返回任何实体内容，并且希望返回更新了的元信息。遇到`复杂请求`时候，浏览器会发送一个`OPTION`方法进行预处理返回响应。

关于复杂请求和简单请求，可以参考这篇文章[CORS非简单请求](https://link.zhihu.com/?target=https%3A//mabiao8023.github.io/2018/03/30/CORS%25E9%259D%259E%25E7%25AE%2580%25E5%258D%2595%25E8%25AF%25B7%25E6%25B1%2582/)。

#### **205 Reset Content**：服务器已经成功处理了请求，但是没有返回任何内容。与204响应不同，返回此状态码的响应要求请求者重置文档视图。
#### **206 Partial Content**：表服务器已经成功处理了部分GET请求

##### 基于206状态码实现断点续传

+ 视频预览时用户可以进行暂停、继续、拖动进度等操作(基于浏览器对206支持)
+ 视频下载将在浏览器下载栏展示, 而非用户点击下载后, 大视频无响应很长时间才弹出存储框(基于浏览器对206支持)
+ 上述优点基于nginx等代理服务转发也可以实现, 但nginx无权限控制, 且用户访问记录需要另外处理

##### java代码示例

先返回请求头状态, 再通过`StreamingResponseBody`异步将文件流写入方法体

```java
public class myController {
    @GetMapping("/download")
    public void download(@ApiParam(value = "下载的id", required = true)
                         @RequestParam(value = "downloadIds") String downloadIds,
                         HttpServletResponse response) throws IOException {
        List<String> downloadList = BaseStringUtils.strToList(downloadIds);
        // 权限校验-下载权限
        fileAuthorityUtil.checkAuthority(KmsCloudAuthorityType.DOWNLOAD, downloadList);
        response.setCharacterEncoding("UTF-8");
        // 设置文件类型及名称
        String fileName = kmsCloudFileManageService.downloadFileName(downloadList);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType(new MimetypesFileTypeMap().getContentType(fileName));
        StreamingResponseBody streamingResponseBody = outputStream ->
                myService.download(outputStream, downloadList);
        streamingResponseBody.writeTo(response.getOutputStream());
    }
}
```

```java
public class MyService {
    @Override
    public void download(OutputStream outputStream, List<String> downloadList) throws IOException {
        if (downloadList.size() == 1) {
            KmsCloudFileManage single = getById(downloadList.get(0));
            // 单文件对象直接下载
            if (KmsCloudFileType.OBJECT.code().equals(single.getType())) {
                ResponseInputStream<GetObjectResponse> responseInputStream =
                        awsS3Option.downloadInputStream(single.getPath());
                Utils.copy(responseInputStream, outputStream);
                return;
            }
        }
        ZipOutputStream zos = new ZipOutputStream(outputStream);
        for (String downloadId : downloadList) {
            List<KmsCloudFileManage> kmsCloudFileManages = list(new LambdaQueryWrapper<KmsCloudFileManage>()
                    .eq(KmsCloudFileManage::getType, KmsCloudFileType.OBJECT.code())
                    .like(KmsCloudFileManage::getPath, downloadId));
            // 获取相对路径的名称
            for (KmsCloudFileManage kmsCloudFileManage : kmsCloudFileManages) {
                String path = kmsCloudFileManage.getPath();
                String relativePath = path.substring(path.lastIndexOf(downloadId));
                String realPath = transformPath(false, relativePath);

                zos.putNextEntry(new ZipEntry(realPath));
                ResponseBytes<GetObjectResponse> responseBytes = awsS3Option.downloadBytes(path);
                zos.write(responseBytes.asByteArray());
                zos.closeEntry();
            }
        }
        zos.close();
    }
}
```

> 备注：使用的最多的2xxs状态码是200和204，在遇到204状态码的时候，要注意一下自己发的请求是不是复杂请求。

### 3xxs状态码

#### **301 Moved Permanently**：被请求的资源已`永久`移动到新位置，并且将来任何对此资源的引用都应该使用响应返回的若干个URI之一。
#### **302 Found(Previously "Moved temporarily")**：请求的资源现在`临时`从不同的URI响应请求。由于这样的重定向是临时的，客户端应当继续向原有地址发送以后的请求。只有在`Cache-Control`或`Expires`中进行了指定的情况下，这个响应才是可缓存的。
#### **303 See Other**：对当前的请求的响应可以在另一个URI上被找到，而且客户端应该采用`GET`的方式访问那个链接。这个方法的存在主要是为了允许由脚本激活的POST请求输出重定向到一个新的资源。
#### **304 Not Modified**：如果客户端发送了一个带条件的 GET 请求且该请求已被允许，而文档的内容（自上次访问以来或者根据请求的条件）并没有改变，则服务器应当返回这个状态码。304 响应禁止包含消息体，因此始终以消息头后的第一个空行结尾。请求的时候一般结合`If-Modified-Since`头部使用。
#### **307 Temporary Redirect**：307的意义如上302。与历史上302不同的是`在重新发出原始请求时不允许更改请求方法`。比如，使用POST请求始终就该用POST请求。

> 备注：307和303已经替代了历史上的302状态码，现在看到的临时重定向的状态码是307。详细内容可到维基百科上查看。

### 4xxs状态码

#### **401 Unauthorized**：这意味着你的登录凭证无效。服务器不知道你是谁，这时，你需要尝试重新登录。
#### **403 Forbidden**：服务器已经理解请求，但是拒绝执行它。与401不同，403知道是你登录了，但是还是拒绝了你。
#### **404 Not Found**：请求失败，你请求所希望得到的资源未在服务器上发现。
#### **410 Gone**：被请求的资源在服务器上已经不再可用，而且没有任何已知的转发地址。
#### **422 Unprocessable Entity**：请求格式良好，但是由于语义错误而无妨遵循。这时候要检查下自己的传参格式语义有没有正确了。
#### **429 Too Many Requests**：用户在给定的时间内发送了太多请求（“限制请求速率”）。在DDOS攻击中就可以使用到了。

### 5xxs状态码

#### **500 Internal Server Error**：服务器内部错误，服务器遇到了不知道如何处理的情况。常常因为后端代码逻辑异常或代码边界条件未处理。
#### **503 Service Unavailable**：服务器没有准备好处理请求。常见的原因是服务器因维护或重载而停机。
#### **504 Gateway Timeout**：网关超时，服务器未能快速的做出反应。请求接口返回pedding时间过长基本就是这个问题。
    
## 参考链接

[小结HTTP状态码](https://zhuanlan.zhihu.com/p/65887537)