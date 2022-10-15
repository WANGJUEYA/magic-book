---
title: Git
summary: Git
tags:
  - TODO
  - 代码仓库
  - Git
categories:
  - 07-Programming-Assistant
date: 2022-10-15 15:38:43
---

## 常用命令

### 绑定git地址

```shell
git init
git remote add origin http://github.com/example.git
git add .
git commit -m ":tada: init"
git branch --set-upstream-to=origin/master master
git push
```

### 拉取分支

```shell
git pull origin master
git clean -d -fx
```

+ `-n` 显示将要删除的文件和目录
+ `-x` 删除忽略文件已经对git来说不识别的文件
+ `-d` 删除未被添加到git的路径中的文件
+ `-f` 强制运行

### 忽略本地历史提交冲突强制合并

```shell
git remote add origin https://192.168.100.49:8081/zhcloud/zh-web.git
git fetch platform
git merge platform/master --allow-unrelated-histories
```

### 切换分支

```shell
git checkout master
git checkout -b project
git branch
```

### 回退数据库版本

```shell
git reset --hard 版本信息SHA
git push -f -u origin prod
```

### 标签

```shell
git tag # 打印所有标签
git tag -l 1.*.* # 打印所有标签
git checkout 1.0.0 # 查看对应标签状态
git tag 1.0.0-light # 创建轻量标签
git tag -a 1.0.0 -m "这是备注信息" # 创建带备注标签(推荐)
git tag -a 1.0.0 0c3b62d -m "这是备注信息" # 针对特定commit版本SHA创建标签
git tag -d 1.0.0 # 删除标签(本地)
git push origin --tags # 将本地标签发布到远程仓库
git push origin 1.0.0 # 指定版本发送
git push origin --delete 1.0.0 # 删除远程仓库对应标签
```

### 全局信息查看

```shell
git remote show origin # 代码源
git remote remove origin # 删除代码源
git config -l # 全局级配置，如果没有仓库级别的特殊配置，默认读取这个配置; 仓库级配置，一般一个项目配置一次
git config --global user.name "name"
git config --system --unset credential.helper # 用户密码更改后重新设置
git config --global http.sslVerify false # 解决SSL验证的问题
git config --global --unset http.proxy # 解决SSL验证的问题
```

### 子模块

```shell
# 添加子模块
git submodule add git@github.com:WANGJUEYA/hexo-theme-christmas-tree.git ./themes/christmas-tree
# 删除子模块缓存(用于手动删除子模块后提示仍然存在的问题)
git rm -r --cached themes/christmas-tree
# 首次拉取包含子模块的代码(windows子模块ssh验证有问题, 试用git的命令行客户端 git-bash 处理)
git clone git@github.com:WANGJUEYA/B612-Factory.git --recursive
```

## 使用ssh协议拉取代码

+ 配置全局配置

```bash
$ git config --global user.name "GitHub用户名"
$ git config --global user.email "GitHub绑定邮箱"
```

+ 输入命令, 一直回车生成文件

```bash
$ ssh-keygen -t rsa -C "{注册邮箱地址}"
```

+ 打开 `C:\Users\用户名\.ssh\id_rsa.pub`, 复制内容
+ 在gitee及github设置中新增ssh
+ 之后设置remote代码源时需要指定为ssh协议 `git@github.com:username`

## Gitmoji

[An emoji guide for your commit messages](https://gitmoji.dev/)

|icon|show|key|remark|
| :---: | :---: | :---: | :---|
|🎨|:art:|art|Improve structure / format of the code.|
|⚡️|:zap:|zap|Improve performance.|
|🔥|:fire:|fire|Remove code or files.|
|🐛|:bug:|bug|Fix a bug.|
|🚑️|:ambulance:|ambulance|Critical hotfix.|
|✨|:sparkles:|sparkles|Introduce new features.|
|📝|:memo:|memo|Add or update documentation.|
|🚀|:rocket:|rocket|Deploy stuff.|
|💄|:lipstick:|lipstick|Add or update the UI and style files.|
|🎉|:tada:|tada|Begin a project.|
|✅|:white_check_mark:|white_check_mark|Add, update, or pass tests.|
|🔒️|:lock:|lock|Fix security issues.|
|🔐|:closed_lock_with_key:|closed_lock_with_key|Add or update secrets.|
|🔖|:bookmark:|bookmark|Release / Version tags.|
|🚨|:rotating_light:|rotating_light|Fix compiler / linter warnings.|
|🚧|:construction:|construction|Work in progress.|
|💚|:green_heart:|green_heart|Fix CI Build.|
|⬇️|:arrow_down:|arrow_down|Downgrade dependencies.|
|⬆️|:arrow_up:|arrow_up|Upgrade dependencies.|
|📌|:pushpin:|pushpin|Pin dependencies to specific versions.|
|👷|:construction_worker:|construction_worker|Add or update CI build system.|
|📈|:chart_with_upwards_trend:|chart_with_upwards_trend|Add or update analytics or track code.|
|♻️|:recycle:|recycle|Refactor code.|
|➕|:heavy_plus_sign:|heavy_plus_sign|Add a dependency.|
|➖|:heavy_minus_sign:|heavy_minus_sign|Remove a dependency.|
|🔧|:wrench:|wrench|Add or update configuration files.|
|🔨|:hammer:|hammer|Add or update development scripts.|
|🌐|:globe_with_meridians:|globe_with_meridians|Internationalization and localization.|
|✏️|:pencil2:|pencil2|Fix typos.|
|💩|:poop:|poop|Write bad code that needs to be improved.|
|⏪️|:rewind:|rewind|Revert changes.|
|🔀|:twisted_rightwards_arrows:|twisted_rightwards_arrows|Merge branches.|
|📦️|:package:|package|Add or update compiled files or packages.|
|👽️|:alien:|alien|Update code due to external API changes.|
|🚚|:truck:|truck|Move or rename resources (e.g.: files, paths, routes).|
|📄|:page_facing_up:|page_facing_up|Add or update license.|
|💥|:boom:|boom|Introduce breaking changes.|
|🍱|:bento:|bento|Add or update assets.|
|♿️|:wheelchair:|wheelchair|Improve accessibility.|
|💡|:bulb:|bulb|Add or update comments in source code.|
|🍻|:beers:|beers|Write code drunkenly.|
|💬|:speech_balloon:|speech_balloon|Add or update text and literals.|
|🗃️|:card_file_box:|card_file_box|Perform database related changes.|
|🔊|:loud_sound:|loud_sound|Add or update logs.|
|🔇|:mute:|mute|Remove logs.|
|👥|:busts_in_silhouette:|busts_in_silhouette|Add or update contributor(s).|
|🚸|:children_crossing:|children_crossing|Improve user experience / usability.|
|🏗️|:building_construction:|building_construction|Make architectural changes.|
|📱|:iphone:|iphone|Work on responsive design.|
|🤡|:clown_face:|clown_face|Mock things.|
|🥚|:egg:|egg|Add or update an easter egg.|
|🙈|:see_no_evil:|see_no_evil|Add or update a .gitignore file.|
|📸|:camera_flash:|camera_flash|Add or update snapshots.|
|⚗️|:alembic:|alembic|Perform experiments.|
|🔍️|:mag:|mag|Improve SEO.|
|🏷️|:label:|label|Add or update types.|
|🌱|:seedling:|seedling|Add or update seed files.|
|🚩|:triangular_flag_on_post:|triangular_flag_on_post|Add, update, or remove feature flags.|
|🥅|:goal_net:|goal_net|Catch errors.|
|💫|:dizzy:|dizzy|Add or update animations and transitions.|
|🗑️|:wastebasket:|wastebasket|Deprecate code that needs to be cleaned up.|
|🛂|:passport_control:|passport_control|Work on code related to authorization, roles and permissions.|
|🩹|:adhesive_bandage:|adhesive_bandage|Simple fix for a non-critical issue.|
|🧐|:monocle_face:|monocle_face|Data exploration/inspection.|
|⚰️|:coffin:|coffin|Remove dead code.|
|🧪|:test_tube:|test_tube|Add a failing test.|
|👔|:necktie:|necktie|Add or update business logic|
|🩺|:stethoscope:|stethoscope|Add or update healthcheck.|
|🧱|:bricks:|bricks|Infrastructure related changes.|
|🧑‍💻|:technologist:|technologist|Improve developer experience|
|💸|:money_with_wings:|money_with_wings|Add sponsorships or money related infrastructure.|
|🧵|:thread:|thread|Add or update code related to multithreading or concurrency.|