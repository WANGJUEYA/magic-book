---
title: Svn
summary: Svn
tags:
  - 代码仓库
  - Svn
categories:
  - 07-Programming-Assistant
date: 2022-10-20 16:46:43
---

## 复制svn代码仓库

```shell
# 生成仓库可导出文件
svnadmin dump /var/svnrepos/10bjq > bjq.dump
# 创建新的仓库
svnadmin create /var/svnrepos/10bjq-ssww
# 导入仓库备份
svnadmin load /var/svnrepos/10bjq-ssww  < bjq.dump
# 给新仓库用户授权
vim /var/svnrepos/conf/authz
# 删除仓库(不可逆)
rm -rf /var/svnrepos/10bjq-ssww
# 任何配置项修改需要重启svn服务器
service svnserve restart
# 检查svn服务运行状态
service svnserve status
```
