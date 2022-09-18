考试注意事项

选对考场和岗位

考试期间不要随意退出



# 计算机组成原理

# 操作系统

## linux

### 操作命令

+ wget 
+ ll 
+ rm -rf
+ tail 

# 计算机网络

#### TCP/IP 七层协议模型

#### HTTP状态码

# 数据结构

## 数组

## MAP

### SET

### LISTS

## 链表

### 堆

### 栈

## 树

### 树的遍历

树的广度遍历、树的深度遍历（先序、中序、后序）

### 二叉树

### AVL(平衡二叉树)

### 红黑树

## 图

+ 图的遍历

# 算法

## 排序算法
### 冒泡排序
### 选择排序
### 快速排序

# 设计模式(23)

## 单例模式

使用案例: 数据库->创建和关闭连接耗费大量资源

## 工厂模式

## 构建者模式

# 编程语言

## 面向过程与面向对象

+ 说说对低耦合高内聚的看法

## 汇编

## C

## C++

## Python

## JAVA

###  基本数据类型

###  异常

### 序列化反序列化

## SQL

### 数据库三大范式
+ 设计一张满足三范式的选课系统
### 聚合查询
+ 查询总成绩前三的学生
### 分页查询
+ 自己实现分页器
### 优化SQL

##  JS、HTML、CSS

+ 行级元素、块级元素
+ 水平居中

+ 如何异步发送两个ajax请求
+ request、cookie、session的区别
### 相关框架
#### vue

+ 解释一下vue渐进式

+ 生命周期/生命周期函数

  + beforeCreate
  + created
  + beforeMount
  + mounted
  + beforeUpdate
  + updated
  + beforeDestory
  + destoryed

+ 如何给一个组件绑定 class 和 style

+ 组件间通信

      parent->child   props
      child->parent   this.$emit
      global          this.$root.$emit('method',args...)  this.$root.$on('method',(args...)=>{})

+ mixin 混入, 可复用功能
+ 调试
	- vue调试插件
	- 改变分辨率
	- 抓包软件

#### css选择器

+ 基本选择器: 
  - 标签选择器：统一定义常用标签的基本样式 $(‘body’)
  - 类选择器：“class”，以.开头后面接自己定义的类名 $(‘.class)
  - ID选择器：“id”，以#开头后面接名字 $(‘#id’)
  - 通配选择器：用*表示，可以可以一次性定义所有元素 

+ 高级选择器: 
  - 后代选择器：用空格隔开
  - 交集选择器：用.隔开
  - 并集选择器：用，隔开
  - 伪类选择器：

#### 前端优化的方案



#  框架与生态
## Spring

### IoC 控制反转
将设计好的对象交给容器控制

### DI 依赖注入

允许程序运行时动态生成对象、执行对象方法(反射实现)

### AOP 面向切面编程

Aspect，Joint point，Pointcut, Advice

## spring cloud
熔断器、断路器、总线
## Hadoop
### 底层原理 -> HDFS MapReduce
#### hive

基于hadoop构建的一套数据仓库分析系统，提供丰富的查询方式

# 项目协同开发与运维
## 编译器
idea、vs code...
## 部署环境
tomcat
docker
k8s



