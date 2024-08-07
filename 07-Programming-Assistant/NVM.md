## 常用命令

```shell
nvm list available # 查看所有可用node版本
nvm install 20.16.0 # 安装node版本
nvm use 20.16.0 # 切换node版本
pnpm config set store-dir <new path> # pnpm设置缓存路径
pnpm store path # pnpm查看缓存路径
```

[nvm介绍原文地址](https://gitcode.csdn.net/65e6e9c81a836825ed787ccc.html)

## 前言

文章基于 `windows环境` 使用nvm安装多版本nodejs。mac可能不适用。
最近公司有的项目比较老需要降低node版本才能运行，由于来回进行卸载不同版本的node比较麻烦；所以需要使用node工程多版本管理，后面自己就简单捯饬了一下nvm来管理node，顺便记录一下自己的使用过程以便于后续查找。

**注意：安装nvm时不能安装任何node版本（如存在请删除后再安装nvm），再检查环境变量，如果还有node.js相关也删掉，保证系统无任何node.js 残留。**
**卸载完之后cmd命令行输入 node -v 查看是否还能查到node信息，无的话表示删除干净。**

## 一、卸载node

如果已经安装了node，那么在安装nvm之前，需要先卸载node，如果没有安装可以直接跳过这一步到下一步了。

**删除前可查当前使用的node版本，方便后续决定使用哪个版本的node。**

- 控制面板 -> 卸载程序 -> 卸载nodejs
- 为了确保彻底删除node，看下node安装目录中还有没有node文件夹，有的话一起删除。
- 删除以下文件夹（如果存在的话）
- C:\Program Files (x86)\Nodejs
- C:\Program Files\Nodejs
- C:\Users{User}\AppData\Roaming\npm
- C:\Users{User}\AppData\Roaming\npm-cache
- 删除C:\Users\用户名 下的 `.npmrc`文件以及 `.yarnrc` 文件
- 环境变量中npm、node的所有相关统统删掉

## 二、nvm是什么？

nvm（node.js version management），是一个nodejs的版本管理工具。nvm和n都是node.js版本管理工具，为了解决node.js **各种版本存在不兼容现象** 可以通过它可以安装和切换不同版本的node.js。【可同时在一个环境中安装多个node.js版本（和配套的npm）】

## 三、nvm安装

### 1. 官网下载 nvm 包

[https://github.com/coreybutler/nvm-windows/releases](https://link.csdn.net/?target=https%3A%2F%2Fgithub.com%2Fcoreybutler%2Fnvm-windows%2Freleases%3Flogin%3Dfrom_csdn)

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/fc49a4e08d6d2876812dbb0683db67eb.png)

### 2. 安装 nvm-setup.exe

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/c4070c75ab0ded466c41a43a2655313a.png)

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/69616b85e591cdc159b3e3f9cadc72a8.png)

**注意安装路径的文件夹名称不要出现中文，空格等，否则后期npm使用的时候会出现符号格式不正确问题。**

继续点击 Next 就行。

### 3. 配置路径和下载镜像

安装完nvm后先不要着急安装node版本。

**找到nvm安装路径 =》找到 setting.txt 文件 =》新增两行信息，配置下载源**

```
node_mirror: https://npmmirror.com/mirrors/node/
npm_mirror: https://npmmirror.com/mirrors/npm/
```

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/dd8bdda9b92ae12b4440896c0e95c21c.png)

增加后如下，然后保存退出。

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/86759639c57a93f4d31023c2b0131c5f.png)

> **第一行是 nvm安装路径**
> **第二行是 nodejs路径**
> **第三行是 node下载镜像**
> **第四行是 npm下载镜像**

**注意：因为淘宝的镜像域名更换，由于 npm.taobao.org 域名HTTPS证书到期更换为 npmmirror.com，那么就会导致之前使用该镜像域名下载依赖的安装包会出现问题。**
执行报错信息如下：

```
>nvm ls available

Could not retrieve https://npm.taobao.org/mirrors/node/index.json.


Get "https://npm.taobao.org/mirrors/node/index.json": x509: certificate has expired or is not yet valid:
```

### 4. 检查nvm是否安装完成

win + R，调用cmd，输入`nvm`，出现下面这一堆就表示安装成功了。

如果安装不成功，查看之前自己安装的 node.js 有没有彻底删除、安装nvm过程中有没有漏掉什么，可重新卸载再安装一次nvm包。

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/e5202b83df1e26d847dc5d209696f0c5.png)

## 四、使用nvm安装node版本

win + R，调用cmd

安装完成后开始插入nodejs首先使用 `nvm list available` 查询可插入版本号，LST表示可插入稳定版本。【如未指定版本，建议安装LTS下的版本】

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/175515ce295190fb6345817b591f6866.png)

1. 安装指定node.js版本

```
nvm install 16.14.0
```

1. `nvm use` 切换node版本。(如果报错，用管理员身份打开重新 `nvm use` 你指定的版本）

```
nvm use 16.14.0
```

1. 安装完成后可以分别输入命令行 `node -v` 和 `npm -v`，检验node.js以及对应npm是否安装成功

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/bfba1a58e0119542b8bd7faa8a50283c.png)

1. `nvm list` 查看当前已安装的node.js版本，带*号的是正在使用的

```
nvm list
或者
nvm ls
```

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/06221e0afb17eea501246f6270c53c37.png)

1. 删除某node.js版本

```
nvm uninstall node版本号  //例如：nvm uninstall 16.14.0）即可删除对应版本
```

1. 比如我现在安装的是 v16.14.0 版本。

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/e38c77af77e5b4d976ea51442243a9f6.png)

v16.14.0 文件内部是

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/cfc4abf66666dabeee515548b2099528.png)

node_modules内部是这样的

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/bbd6cfe0a8ed17ab44965612a8b1de32.png)

## 五、修改npm默认镜像源为淘宝镜像

修改npm镜像源为淘宝镜像，加快npm包的下载速度，减少发生连接错误和超时的概率。

```
npm config set registry https://registry.npmmirror.com
```

检查是否设置淘宝镜像成功（会返回这个地址表示成功：https://registry.npmmirror.com）

```
npm config get registry
```

关于使用 淘宝镜像 `https://registry.npm.taobao.org` 报错的问题

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/f6b06a9329cb422864b05d32809908a2.png)

是因为从 2024 年1 月 22 日，淘宝原镜像域名（registry.npm.taobao.org）的 HTTPS 证书正式到期，这就导致旧的 npm 淘宝镜像在使用时出错了，所以遇到上述问题，或者还在使用旧的 npm 淘宝镜像，直接将 npm 源切换到新的源即可，文章链接：[技术资讯：npm 淘宝镜像正式到期，赶紧更新！](https://link.csdn.net/?target=https%3A%2F%2Fzhuanlan.zhihu.com%2Fp%2F679613901%3Flogin%3Dfrom_csdn)

```
// 1. 清空缓存
npm cache clean --force
// 2. 切换新源
npm config set registry https://registry.npmmirror.com
```

## 六、使用 nrm 来管理 npm 源

这部分想管理npm源就下载一下，不下载只要上一步完成切换淘宝源镜像也可以，不会影响下面的其他操作，看个人意愿了。
我们可以使用 nrm 来管理 npm 源，特别是当需要在不同的网络环境之间切换时，它可以提供更好的包管理体验。

> nrm（npm registry manager）是一个用于管理和切换 npm 源的命令行工具。它允许您在不同的 npm 源之间进行切换，以加快包的下载速度，或者解决特定源无法访问的问题。nrm 提供了一组命令，可以列出可用的 npm 源、添加新源、测试源的响应速度，并切换当前使用的源。

首先，通过以下命令来安装 nrm：

```
npm install -g nrm
```

以下是一些常见的 nrm 命令：

1）列出可用的源：当前配置的所有可用 npm 源以及它们的名称和 URL。

```
nrm ls
```

例如：

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/8b014536df0c615d206d1e0d112f9268.png)

2）切换源：将当前的 npm 源切换为指定的源。可以使用源的名称或 URL 作为 参数。

```
nrm use <registry>
```

3）添加源：添加一个新的 npm 源并指定其名称和 URL。

```
nrm add <registry> <url>
```

4）删除源：删除指定的 npm 源，需要提供源的名称或 URL 作为 参数。

```
nrm del <registry>
```

5）测试源的速度：测试指定源的响应速度，并显示测试结果。

```
nrm test <registry>
```

6）显示当前使用的源：当前正在使用的 npm 源的名称和 URL。

```
nrm current
```

## 七、 环境变量配置

### 1. 设置系统变量和用户变量的作用是什么呢？

答：为了命令行安装包时，将包安装到自己设置的目录下。

### 2. 配置步骤

#### 1）新增俩文件夹

1. 首先创建`"node_global"` 和 `“node_cache”`两个文件夹进行全局安装的时候安装对应的库到这两个文件。
   在nvm的 `nodejs` 安装路径 `C:\Program Files\nodejs` 新建两个文件夹命名为 `"node_global"` 和 `“node_cache”`。

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/321c254cf21e0378bce6394f171dca20.png)

1. 打开cmd命令行工具，输入以下两句操作（两个路径就是新建上面两个文件夹的路径，主要目的是方便后面window机器使用方便）

```
npm config set prefix "C:\Program Files\nodejs\node_global"
npm config set cache "C:\Program Files\nodejs\node_cache"
```

执行成功就无任何响应，没有异常的话，就在去到 环境变量设置处。

#### 2）设置环境变量

我的电脑右键 =》属性 =》高级系统设置 =》系统属性（高级）=》 环境变量，进入环境变量对话框。

如果环境变量未正确配置，输入`node -v` 会报错，系统将无法正确识别"node"命令。

需要我们设置的地方有两个：

> **1. 系统环境变量新增 NODE_PATH 变量**
> **2. 用户变量修改 path 变量**

1. 在【系统变量】新建环境变量 `NODE_PATH` 值为 `C:\Program Files\nodejs\node_global\node_modules`，
   其中`C:\Program Files\nodejs\node_global\node_modules` 就是上面创建的全局模块安装路径文件夹。

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/f67863657503b8540173a8139917ec25.png)

1. 修改 【用户变量】中的 `path` 变量
   这里我其实没有修改或者增加什么变量，是安装时候默认添加的。
   ![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/d82b69602aed9879257bc48e961c433e.png)
   ![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/85f124092dff15bdd4363019d4895a34.png)
2. 另外下载完 nvm 之后，系统环境变量和用户环境变量会默认帮我们配置好 `NVM_HOME` 和 `NVM_SYMLINK`

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/0d7dd9e26f23b220e97c238ee6b282eb.png)

1. 点击确定后配置完成。

## 八、测试安装全局模块

测试是否环境变量是否配置成功，在cmd窗口中输入以下指定全局安装express模块，安装成功的话表示环境变量配置成功。

```
npm install -g express
```

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/87bd94492cc23a7e362501960acdbecf.png)

## 九、卸载nvm

1. 先删除你当初所安装的nvm、nodejs的文件夹即可。
   ![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/526bca0a8cf493a749578e66aa2fe04d.png)

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/12063fecfb0d3fc27926fa475e2ddfd8.png)

1. 桌面右键 此电脑 – 点击属性 – 找到高级系统设置 – 环境变量。
2. 删除用户变量 和 系统变量中名为 `NVM_HOME` 和 `NVM_SYMLINK` 两个变量。其他的不要改。

![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/a4c9c0f9e7f8c64da6d3232ab43a3677.png)

1. 删除用户变量和系统变量中path中的 %NVM_HOME%，%NVM_SYMLINK% 两个属性，还有c盘中的node_cache，node_global，其他的不要改。
   ![在这里插入图片描述](https://i-blog.csdnimg.cn/blog_migrate/914badf5a4e7922bd3a250c400a6351c.png)

## 十、遇到的问题

> **1. 在安装 nvm 之前没有卸载掉 node，导致 nvm 安装完之后，node和npm都不可用。所以我们在拿到一个新电脑时候最好是先下载nvm，再安装node版本。**
> **2. 在第一次使用nvm安装node后，记得使用 nvm use 切换下node版本，以及用 nvm on 打开nodejs版本控制，不然这时候node和npm也都不可用。**
> 3. **使用 淘宝镜像 https://registry.npm.taobao.org 报错的问题，因为淘宝原镜像域名（registry.npm.taobao.org）的 HTTPS 证书正式到期； npm 淘宝镜像已经切换到了registry.npmmirror.com，使用命令行重新切换就可以了 npm config set registry https://registry.npmmirror.com**，文章链接：[技术资讯：npm 淘宝镜像正式到期，赶紧更新！](https://link.csdn.net/?target=https%3A%2F%2Fzhuanlan.zhihu.com%2Fp%2F679613901%3Flogin%3Dfrom_csdn)

可参考：
[使用nvm管理node.js版本以及更换npm淘宝镜像源](https://link.csdn.net/?target=https%3A%2F%2Fblog.csdn.net%2Fpdd11997110103%2Farticle%2Fdetails%2F115981856%3Flogin%3Dfrom_csdn)
[win10环境使用nvm安装多版本nodejs并配置环境变量](https://link.csdn.net/?target=http%3A%2F%2F681314.com%2FA%2FpaXERvv5T8%3Flogin%3Dfrom_csdn)
[window下安装并使用nvm（含卸载node、卸载nvm、全局安装npm）](https://link.csdn.net/?target=https%3A%2F%2Fblog.csdn.net%2FHuangsTing%2Farticle%2Fdetails%2F113857145%3Flogin%3Dfrom_csdn)
[安装并搭建Vue项目【安装node，设置npm镜像】](https://link.csdn.net/?target=https%3A%2F%2Fblog.csdn.net%2FyeahPeng11%2Farticle%2Fdetails%2F120606669%3Flogin%3Dfrom_csdn)