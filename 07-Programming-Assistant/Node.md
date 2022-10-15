---
title: npm
summary: npm
tags:
  - TODO
  - npm
categories:
  - 07-Programming-Assistant
date: 2022-10-15 15:29:17
---

## npm常用包

### 快速完全删除 node_modules

```shell
npm install rimraf -g # 全局安装
rimraf node_modules
```

### 运行打包好的代码

```shell
cd dist # 进入打包好的文件
npx serve -d
```

### yarn

```shell
yarn remove package
yarn add package # 新增升级都用该命令
```

## 使用本地依赖

+ [Node.js使用本地依赖](https://www.cnblogs.com/blacklsle/p/14787684.html)