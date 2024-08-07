---
title: npm
summary: npm
tags:
  - npm
categories:
  - 07-Programming-Assistant
date: 2022-10-15 15:29:17
---

## npm常用包

### 全局安装组件
```shell
npm install {package} -g 
```

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
yarn [global] add [package]@[version] # 安装包
yarn upgrade [package | package@tag | package@version | @scope/]... [--ignore-engines] [--pattern] # 更新包
yarn remove <package...> # 删除包
yarn list [--depth] [--pattern] # 列出所有包和它们的依赖
yarn run [script] [<args>] # 运行脚本
yarn upgrade --latest # 升级所有过时的包版本
# npm install -g yarn-upgrade-all # 全局升级
npx yarn-upgrade-all # 所有文件升级
```

#### npm与yarn基本命令对比

|npm|yarn|
|:---|:---|
| npm install react --save | yarn add react |
| npm uninstall react --save | yarn remove react |
| npm install react --save-dev | yarn add react --dev |
| npm update --save | yarn upgrade |

## 使用本地依赖

+ [Node.js使用本地依赖](https://www.cnblogs.com/blacklsle/p/14787684.html)


## vue-cli 3.0 脚手架cli-service配置说明

官方文档 https://cli.vuejs.org/zh/guide/mode-and-env.html

在`package.json`使用默认预设的项目中看到的内容

```json
{
  "scripts": {
    "serve": "vue-cli-service serve --open",
    "build": "vue-cli-service build",
    "lint": "vue-cli-service lint"
  }
}
```

serve配置

```text
  --open    服务器启动时打开浏览器
  --copy    将URL复制到服务器启动时的剪贴板 (直接到浏览器去粘贴就OK了 http://localhost:8080/)
  --mode    指定环境模式 (默认: development)
  --host    host 地址 (default: 0.0.0.0)
  --port    端口号 (default: 8080)
  --https   使用https (default: false)
```

build配置
```text
  --mode        指定环境模式 (default: production)
  --dest        指定输出目录 (default: dist)
  --modern      构建两个版本的 js 包：一个面向支持现代浏览器的原生 ES2015+ 包，以及一个针对其他旧浏览器的包。
  --target      允许您以项目库或Web组件的形式在项目内部构建任何组件 app | lib | wc | wc-async (default: app) ???
  --name        lib或者web组件库的名称 (default: "name" in package.json or entry filename)
  --no-clean    在构建项目之前不要删除输出目录(dist)
  --report      生成report.html以帮助分析包内容
  --report-json 生成report.json来帮助分析包内容
  --watch       监听 - 当有改变时 自动重新打包~
```

## node-sass 冲突问题

[node-sass 版本对应关系](https://www.npmjs.com/package/node-sass)