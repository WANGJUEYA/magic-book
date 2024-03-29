---
title: Hexo
summary: Hexo | Github 自定义博客生成工具 快速搭建发布个人博客
tags:
  - hexo
  - node
  - npm
  - git
  - gitee
  - github
categories:
  - 07-Programming-Assistant
date: 2022-09-17 16:27:00
---

Hexo | Github 自定义博客生成工具

<!--more-->

## [hexo安装](https://hexo.io/zh-cn/)

+ [npm安装全局组件](/node/#%E5%85%A8%E5%B1%80%E5%AE%89%E8%A3%85%E7%BB%84%E4%BB%B6)

``` bash
$ npm install hexo-cli -g
$ npm install yarn -g
```

+ [初始化项目](https://hexo.io/docs/writing.html)

``` bash
$ hexo init B612-Factory
```

+ [项目启动](https://hexo.io/docs/server.html) `http://localhost:4000`

``` bash
$ hexo server
```

+ [生成静态文件](https://hexo.io/docs/generating.html)

``` bash
$ hexo generate
```

+ [部署到远程站点](#git发布)

``` bash
$ hexo deploy
```

## hexo 使用

### 创建文章

```bash
$ hexo new [layout] <title> [-p <directory>/<filename>]
```

+ 您可以在命令中指定文章的布局（layout），默认为 post，可以通过修改 _config.yml 中的 default_layout 参数来指定默认布局。

### Front-matter 选项详解

`Front-matter` 选项中的所有内容均为**非必填**的。但我仍然建议至少填写 `title` 和 `date` 的值。

| 配置选项    | 默认值                 | 描述  |
| ---------- | --------------------- | ------------------------------------------------------------ |
| layout     | config.default_layout | 布局 |
| title      | 文章的文件名            | 标题 |
| date       | 文件建立日期            | 建立日期  |
| updated    | 文件更新日期            | 更新日期 |
| tags       | -                     | 标签（不适用于分页）；没有顺序和层次 |
| categories | -                     | 分类（不适用于分页）；具有顺序性和层次性 |

#### 示例

```yaml
---
title: Hexo Front-matter
date: 2022-10-13 15:00:00
categories:
  - [ 分类1, 分类1-1 ]
  - [ 分类1, 分类1-2 ]
  - [ 分类2 ]
tags:
  - 标签1
  - 标签2
---
```

#### 摘要补充

+ Front-matter summary|excerpt
+ 文章需要截断的地方增加 `<!--more-->`，首页就会显示这条指令以上的所有内容，隐藏接下来的所有内容

### Hexo 将文件内容作为代码片段插入到博客中

```js
/* global hexo */

'use strict';

const fs = require('fs');

const fileCode = async (data) => {
    let content = data.content
    const regExp = /\[@fileCode]\((.*)\)/g;
    let match = null;
    const matches = [];
    while ((match = regExp.exec(content)) !== null) {
        matches.push(match)
    }
    const readFiles = matches.map(item => {
        const file = item[1];
        const splits = file.split('.');
        const fileType = splits.length > 1 ? splits[splits.length - 1] : '';

        let path = 'source/' + data.source;
        path = path.replace('.md', '/' + file)
        let fileContent = path
        return new Promise((resolve, reject) => {
            fs.readFile(path, (err, data) => {
                if (err) {
                    console.error('读取文件错误', path);
                    resolve(path);
                } else {
                    fileContent = data.toString();
                    const res = '```' + fileType + '\n' + fileContent + '\n```\n';
                    content = content.replaceAll(item[0], res)
                    resolve(content);
                }
            });
        })
    });

    await Promise.all(readFiles);
    data.content = content

    return data
}

// https://hexo.io/zh-cn/api/filter
// https://www.jianshu.com/p/c8964c5ffd7a
hexo.extend.filter.register('before_post_render', async function (data) {
    const {config: themeCfg} = this.theme;
    if (themeCfg.fileCode.enable && data.fileCode) {
        return await fileCode(data);
    } else {
        return undefined;
    }
}, 1);
```

## [更多配置](https://hexo.io/zh-cn/docs/configuration)

### [主题切换](https://hexo.io/zh-cn/docs/themes) `theme`

+ 在样式文件夹中下载合适的主题

``` bash
$ cd ./B612-Factory/themes
$ git clone https://github.com/WANGJUEYA/hexo-theme-christmas-tree.git christmas-tree
```

+ 修改hexo根目录 `_config.yml` 中 `theme` 对应配置 `配置必填项见主题说明`

```yml
theme: christmas-tree
```

+ 可在hexo [主题仓库](https://hexo.io/themes/) 挑选心仪的主题样式替换

    + [christmas-tree](https://wangjueya.github.io/) ☞ ```https://github.com/WANGJUEYA/hexo-theme-christmas-tree.git```
    + [matery](http://blinkfox.com//) ☞ ```https://github.com/blinkfox/hexo-theme-matery.git```
    + [fluid](https://hexo.fluid-dev.com/) ☞ ```https://github.com/fluid-dev/hexo-theme-fluid.git```
    + [tree](https://wujun234.com/) ☞ ```https://github.com/wujun234/hexo-theme-tree.git```
    + [Theme Yuzu](https://cerallin.github.io/) ☞ ```https://github.com/Cerallin/hexo-theme-yuzu.git```

### 图片存储 `post_asset_folder`

+ 使用绝对路径
    + md相关URL资源引用
+ 使用相对路径

    + 修改hexo根目录 `_config.yml` 中 `theme` 对应配置 `配置必填项见主题说明`

  ```yml
  post_asset_folder: true # 修改之后会开启 Hexo 的文章资源文件管理功能
  ```

    + 开启资源管理后, Hexo 将会在我们每一次通过 `hexo new <title>` 命令创建新文章时自动创建一个同名文件夹,  `![name](./example.jpg)` 即可访问(该方法仅支持生成文档路径为一级,
      相对路径需要手动书写)

+ 使用 markdown 和 hexo 均支持的相对路径

安装插件

``` bash
$ yarn add hexo-asset-img
```

```![image file label]（markdown-file-name/local-image.png） -> {% asset_img label local-image.png %}```

![图片引用](Hexo/example.jpg "图片引用")


### 自动将目录生成分类

``` bash
$ yarn add hexo-auto-category
```

```yml
# hexo-auto-category
auto_category:
  enable: true
  depth:
```

## git发布

+ 生成并配置ssh

    + 配置全局配置
  ```bash
  $ git config --global user.name "GitHub用户名"
  $ git config --global user.email "GitHub绑定邮箱"
  ```
    + 输入命令, 一直回车生成文件
  ```bash
  $ ssh-keygen -m PEM -t rsa -b 4096 -C "{注册邮箱地址}"
  ```

    + 打开 `C:\Users\用户名\.ssh\id_rsa.pub`, 复制内容
    + 在gitee及github设置中新增ssh

+ 安装部署所需要的插件

```bash
yarn add hexo-deployer-git
```

+ 修改hexo根目录 `_config.yml` 对应发布配置

```yml
deploy:
  type: git
  repository:
    github: git@github.com:用户名/用户名.github.io.git
    gitee: git@gitee.com:用户名/用户名.git
  branch: master
```

+ 点击发布即可 (gitee需要在`Gitee Pages 服务`手动更新)

## 自定义插件

+ [hexo 教程之为 hexo 编写一个插件](https://blog.csdn.net/godread_cn/article/details/122031503)

## 其他插件介绍

### 流程图

+ Hexo插件使用教程: https://blog.17lai.site/posts/cf0f47fd/#Mermaid
+ mermaid语法教程: https://mermaid-js.github.io/mermaid/#/

```text
{% mermaid %}
[内容]
{% endmermaid %}
```

{% mermaid %}
graph TD
A[Christmas] -->|Get money| B(Go shopping)
B --> C{Let me think}
C -->|One| D[Laptop]
C -->|Two| E[iPhone]
C -->|Three| F[Car]
{% endmermaid %}
```text
{% mermaid %}
graph TD
A[Christmas] -->|Get money| B(Go shopping)
B --> C{Let me think}
C -->|One| D[Laptop]
C -->|Two| E[iPhone]
C -->|Three| F[Car]
{% endmermaid %}
```

### 脑图 `hexo-markmap`

+ https://github.com/MaxChang3/hexo-markmap


{% markmap 400px %}
- links
- **inline** ~~text~~ *styles*
- multiline
  text
- `inline code`
{% endmarkmap %}

### 公式

> https://wangjueya.github.io/markdown/#%E5%85%AC%E5%BC%8F%E6%B8%B2%E6%9F%93mathjax

## 快捷启动可用项目

> 需要环境 `git` `npm`

``` bash
$ git clone git@github.com:WANGJUEYA/B612-Factory.git --recursive
$ cd B612-Factory
$ npm install
$ npm run server
```

+ 如果本地ssh密钥不为空会有 `Permission denied`报错, 使用 git-bash 客户端
+ 用自己的markdown文件替换 `./source/_posts` 下所有文件

## 参考链接

+ [利用Gitee+Hexo搭建个人网站](https://zhuanlan.zhihu.com/p/269420507)
+ [Hexo系列 | Hexo的基本使用](https://zhuanlan.zhihu.com/p/85037427)
+ [Hexo+GitHub+Gitee搭建静态博客](https://blog.csdn.net/qq_44573890/article/details/107693424?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EOPENSEARCH%7ERate-5-107693424-blog-108146314.pc_relevant_aa&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EOPENSEARCH%7ERate-5-107693424-blog-108146314.pc_relevant_aa&utm_relevant_index=6)
