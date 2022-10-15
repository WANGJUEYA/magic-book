---
title: npm

date: 2022-10-15 15:29:17

summary: npm

tags:
- TODO
- npm

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