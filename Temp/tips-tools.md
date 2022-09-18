# git

> 绑定git地址
* <span style="color:red;">git init</span>
* <span style="color:red;">git remote add origin http://github.com/example.git</span>
* <span style="color:red;">git add .</span>
* <span style="color:red;">git commit -m "注释"</span>
* <span style="color:red;">git push</span>
* <span style="color:red;">git branch --set-upstream-to=origin/master master</span>

> 拉取分支
* <span style="color:red;">git pull origin master</span>
* <span style="color:red;">git clean -d -fx</span>
	+ <span style="color:red;">-n</span> 显示将要删除的文件和目录
	+ <span style="color:red;">-x</span> 删除忽略文件已经对git来说不识别的文件
	+ <span style="color:red;">-d</span> 删除未被添加到git的路径中的文件
	+ <span style="color:red;">-f</span> 强制运行

> fork平台分支
```
git remote add origin https://192.168.100.49:8081/zhcloud/zh-web.git
git fetch platform
git merge platform/master --allow-unrelated-histories
```

> 切换分支

* 切换到基础分支 <span style="color:red;">git checkout master</span>
* 创建并切换分支 <span style="color:red;">git checkout -b project</span>
* 查看当前分支 <span style="color:red;">git branch</span>

> 回退数据版本 
* <span style="color:red;">git reset --hard 版本信息SHA</span>
* <span style="color:red;">git push -f -u origin prod</span>

> 标签
* <span style="color:red;">git tag</span> 打印所有标签
* <span style="color:red;">git tag -l 1.*.*</span> 打印符合检索条件的标签
* <span style="color:red;">git checkout 1.0.0</span> 查看对应标签状态
* <span style="color:red;">git tag 1.0.0-light</span> 创建轻量标签
* <span style="color:red;">git tag -a 1.0.0 -m "这是备注信息"</span> 创建带备注标签(推荐)
* <span style="color:red;">git tag -a 1.0.0 0c3b62d -m "这是备注信息"</span> 针对特定commit版本SHA创建标签
* <span style="color:red;">git tag -d 1.0.0</span> 删除标签(本地)
* <span style="color:red;">git push origin --tags</span> 将本地标签发布到远程仓库
* <span style="color:red;">git push origin 1.0.0</span> 指定版本发送
* <span style="color:red;">git push origin --delete 1.0.0</span> 删除远程仓库对应标签


> 全局信息查看
* <span style="color:red;">git remote show origin</span>
* <span style="color:red;">git remote remove origin</span>
* <span style="color:red;">git config -l</span> 查看当前全部配置
  *全局级配置，如果没有仓库级别的特殊配置，默认读取这个配置*
* <span style="color:red;">git config --global user.name "name"</span>
  *仓库级配置，一般一个项目配置一次*
* <span style="color:red;">git config --global http.sslVerify false</span> 解决SSL验证的问题
* <span style="color:red;">git config --system --unset credential.helper</span> 用户密码更改后重新设置
* <span style="color:red;">git remote remove origin</span>
* git config --global --unset http.proxy

> 初始化web项目 - master已经创建readme

+ git init
+ git remote add origin
+ git pull origin master
+ git checkout -b platform
+ git remote add platform https://192.168.100.49:8081/zhcloud/zh-web.git
+ git pull platform master
+ git push origin platform
+ git checkout -b develop
+ git merge origin/platform 

> vue项目初始化

```
* git init
* git checkout -b project
* git branch

1. vue后台只拉取business分支

* git init
* git remote add origin https://192.168.100.49:8081/zh/zh-boot-vue.git
* git config core.sparsecheckout true # 开启部分拉取
* echo zh-boot-vue/pom.xml >> .git/info/sparse-checkout
* echo zh-boot-vue/zh-vue-business >> .git/info/sparse-checkout
* git pull origin master

​```
zh-boot-vue/pom.xml
zh-boot-vue/zh-vue-business
​```

2. vue前台拉取zh-web地址

* mkdir zh-project-vue
* cd zh-project-vue
* git init
* git remote add origin https://192.168.100.49:8081/zhcloud/zh-web.git
* git pull origin master


3. 删除子目录中的.git文件并提交初始化项目

* cd ..
* git add .
* git commit -m "init"
* git push origin project

### vue项目初始化 代码更新
1. vue 后端直接复制
2. vue 前端
```

# NPM

> 完全删除 node_modules

```
npm install rimraf -g
rimraf node_modules

运行打包好的代码
cd dist
npx serve -d

```

yarn

```
yarn remove package
# 新增升级都用该命令
yarn add package
```

# maven

* mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.2.0 -Dpackaging=jar -Dfile=E:\oracle\ojdbc14-10.2.0.2.0.jar

# negix

* <span style="color:red;">start nginx</span> 开启nginx

* <span style="color:red;">nginx -s stop</span> 快速关闭Nginx，可能不保存相关信息，并迅速终止web服务。

* <span style="color:red;">nginx -s quit</span> 平稳关闭Nginx，保存相关信息，有安排的结束web服务。

* <span style="color:red;">nginx -s reload</span> 因改变了Nginx相关配置，需要重新加载配置而重载。

* <span style="color:red;">nginx -s reopen</span> 重新打开日志文件。

* <span style="color:red;">nginx -c filename</span> 为 Nginx 指定一个配置文件，来代替缺省的。

* <span style="color:red;">nginx -t</span> 不运行，而仅仅测试配置文件。nginx 将检查配置文件的语法的正确性，并尝试打开配置文件中所引用到的文件。

* <span style="color:red;">nginx -v</span> 显示 nginx 的版本

# oracle



# Mysql

my.ini

```
[client]
# 设置mysql客户端默认字符集
default-character-set=utf8

[mysqld]
# 设置3306端口
port = 3306
# 设置mysql的安装目录
basedir=E:\\Mysql
# 设置 mysql数据库的数据的存放目录，MySQL 8+ 不需要以下配置，系统自己生成即可，否则有可能报错
# datadir=C:\\web\\sqldata
# 允许最大连接数
max_connections=20
# 服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=utf8
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
```

# elasticsearch

```
#集群地址
http://192.168.100.38:9200
http://192.168.100.38:9201
http://192.168.100.38:9202

# 查询版本号 (7.6.2)
GET http://192.168.100.38:9200

# 查询健康情况
GET http://192.168.100.38:9200/_cluster/health?pretty

# 查询所有节点数
GET http://192.168.100.38:9200/_cat/nodes?pretty

# 查询所有节点信息
GET http://192.168.100.38:9200/_cat/allocation?pretty

# 查询所有索引
curl -XGET http://192.168.100.38:9200/_cat/indices?pretty
GET http://192.168.100.38:9200/_cat/indices?v&pretty

# 删除索引[可通配符]
curl -XDELETE http://192.168.100.38:9200/kms_knowledge*

# 查询数据结构
GET kms_knowledge_check/_mapping?pretty

# 查询索引的所有数据
GET kms_knowledge_check/_search?pretty

## attachment
# 查询 GET _ingest/pipeline
# 删除 DELETE _ingest/pipeline/simple_attachment
# 新增
PUT _ingest/pipeline/my_attachment
{
  "description" : "Extract attachment for knowledge",
  "processors" : [
    {
      "attachment" : {
        "field" : "knowContent"
      }
    }
  ]
}
## 关联数据
PUT kms_knowledge/_doc/my_id?pipeline=my_attachment
{
  "knowContent": "e1xydGYxXGFuc2kNCkxvcmVtIGlwc3VtIGRvbG9yIHNpdCBhbWV0DQpccGFyIH0="
}
# 查询
GET kms_knowledge/_doc/my_id

GET kms_knowledge_test/_search
{
  "query": { "match": { "attachment.content": "测试" } }
}


POST kms_knowledge/_update/string
{
  "script": {
    "source": "TemporalAccessor tem = DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm:ss', Locale.CHINA).parse(ctx._source['sysCreateTime']); ctx._source['countHot'] = 1 + tem.getLong(ChronoField.EPOCH_DAY);"
  }
}


{
  "bool" : {
    "should" : [
      {
        "terms" : {
          "sysCreateUser" : [
            "4001xtgly"
          ],
          "boost" : 1.0
        }
      },
      {
        "terms" : {
          "sysUpdateUser" : [
            "4001xtgly"
          ],
          "boost" : 1.0
        }
      },
      {
        "wildcard" : {
          "knowTitle" : {
            "wildcard" : "*管*理*员*",
            "boost" : 1.0
          }
        }
      },
      {
        "terms" : {
          "sysRemark" : [
            "管理员"
          ],
          "boost" : 1.0
        }
      },
      {
        "match" : {
          "knowContent" : {
            "query" : "管理员",
            "operator" : "OR",
            "prefix_length" : 0,
            "max_expansions" : 50,
            "fuzzy_transpositions" : true,
            "lenient" : false,
            "zero_terms_query" : "NONE",
            "auto_generate_synonyms_phrase_query" : true,
            "boost" : 1.0
          }
        }
      },
      {
        "fuzzy" : {
          "attachment.content" : {
            "value" : "管理员",
            "fuzziness" : "AUTO",
            "prefix_length" : 0,
            "max_expansions" : 50,
            "transpositions" : true,
            "boost" : 1.0
          }
        }
      },
      {
        "wildcard" : {
          "annexNames" : {
            "wildcard" : "*管*理*员*",
            "boost" : 1.0
          }
        }
      }
    ],
    "adjust_pure_negative" : true,
    "boost" : 1.0
  }
}
```



# VUE

Cannot find module './BasicLayout'





# CMD

当前目录下文件

```bash
dir
```

```shell
netstat -aon|findstr "8080"
```

```shell
taskkill /pid 14484 -t -f
```

```shell
type *.sql >> ../init_database.sql
```

批处理文件 bat
```bash
:: 注释内容
```

