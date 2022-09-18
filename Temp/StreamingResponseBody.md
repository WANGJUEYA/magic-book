```
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
                kmsCloudFileManageService.download(outputStream, downloadList);
        streamingResponseBody.writeTo(response.getOutputStream());
    }
```

```
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
```

# 优化方案
对于这种方案获取的视频文件流没有办法拖动进度条, 使用video.js也会报错
故采用 HTTP 206 状态码进行优化



# 原有方案

1. nginx文件服务器直接访问; 问题: 没有权限控制
2. 文件读成流再返回前端(速度过慢) - 考虑异步写入文件体
3. 利用@Async注解: http连接会中途关闭
4. spring内部转发(非异步连接会断开)

# 可能优化
1. contentSize 在使用压缩时没有办法提前获取 如果和最终结果不一致时将无法下载成功

# 查询标签
使用浏览器自带的下载进度条
异步将文件流写入方法体