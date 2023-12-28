---
title: GitBook
summary: GitBook
tags:
 - 代码仓库
 - GitBook
categories:
 - 07-Programming-Assistant 
date: 2023-12-14 20:44:36
---
 
## 简介

GitBook是一个将markdown文件生成电子书的工具，可以使用在线版本，也可以本地安装 gitbook-cli 本地化电子书

## 安装及命令简介

+ https://chrisniael.gitbooks.io/gitbook-documentation/content/index.html
+ https://zhuanlan.zhihu.com/p/34946169

+ 常用命令

```shell
# 全局安装gitbook脚手架
npm install gitbook-cli -g
# 查看版本及初始化安装
gitbook --version
# 设置一个样板书
gitbook init
# 将书籍创建到指定目录
gitbook init ./directory

```

### 安装问题及解决方案

#### 安装过程Windows环境下安装异常 `gitbook-cli\node_modules\npm\node_modules\graceful-fs\polyfills.js:287 > cb.apply is not a function`

找到对应文件
`C:\Users\${username}\AppData\Roaming\npm\node_modules\gitbook-cli\node_modules\npm\node_modules\graceful-fs\polyfills.js`

注释掉下面三行代码即可

```polyfills.js
  // fs.stat = statFix(fs.stat)
  // fs.fstat = statFix(fs.fstat)
  // fs.lstat = statFix(fs.lstat)
```
