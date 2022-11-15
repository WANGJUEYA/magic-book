---
title: Python
keywords: Python
summary: Python
tags:
  - Python
categories:
  - 04-Programming-Languages
  - Python
date: 2022-10-15 22:17:33
---

## Python基础知识


标识符 英文/数字/下划线

单下划线开头

_foo 不能直接访问的类属性(private)需提供接口 不能用 from xxx import * 导入

双下划线开头

__foo 私有成员

双下划线开头结尾

__foo__ 特殊方法专用标识 __init__() 构造函数

成员属性(字段)/实例属性(map)/类属性(静态公有变量)

![图片](Python/Python属性解释.png)

关键字

![图片](Python/Python关键字.png)

数据类型(5)

Number(数字) String(字符串) List(列表) Tuple(元组) Dictionatry(字典)

## Python函数

```python
def func_name(param_name):
    print(param_name)
```

## 安装包

```bash
cd {安装路径}/Scripts
pip install {package}
```

## 字符串转json

```python
import json
# len(str) > 0
json.loads(str)
```

## 发送HTTP请求

### 引入 requests 包
```python
import requests
```

+ 发送请求(包括请求行、方法类型、头、体) & 常见的请求方式有get、post、put、delete

### 发送get请求

格式：requests.get() (内容： url必填； params选填：url参数字典)

#### 无参数的get请求
```python
res = requests.get(url='https://yz.chsi.com.cn/zsml/pages/getMl.jsp')
# 打印响应主体内容，字符串格式
print(res.text)
```
#### 有参数的get请求
```python
res = requests.get(url='https://yz.chsi.com.cn/zsml/code/zy.do',
                   params={"q": '0101'})
# 打印响应主体内容，字符串格式
print(res.text)
```

### 发送post请求
> 知识扩展

+ requests.post() post请求分为5种，常用的有三种，如下
  + ① application/x-www-form-urlencod (form表单)；
  + ② raw (纯文本格式)：有5种格式，分别为json/xml/Html/Text/JavaScrip
  + ③ multipart/form-data (复合式表单)

#### 有正文体的post请求

##### form表单(application/x-www-form-urlencod) ----data 后跟字典

> eg.1: 带参数的查询接口

```python
res = requests.post(url='http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getSupportCityString',
headers={"Content-Type": "application/x-www-form-urlencoded"},
data={"theRegionCode": 3113})
print(res.text)
```
> eg.2: 带账号名密码的登录接口

```python
res = requests.post(url='http://123.56.99.53:9000/event/api/admin/',
headers={"Content-Type": "application/x-www-form-urlencoded"},
data={"username": "admin", "password": "MTIzYWRtaW4="})
print(res.text)
```
##### 纯文本格式(raw)

> ~ xml格式    ----data    后跟字符串

```python
res = requests.post(url='http://ws.webxml.com.cn/WebServices/WeatherWS.asmx',
headers ={"Content-Type": "text/xml; charset=utf-8",
"SOAPAction": "http://WebXml.com.cn/getSupportCityString"},
data = '''<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
<soap:Body>
<getSupportCityString xmlns="http://WebXml.com.cn/">
<theRegionCode>string</theRegionCode>
</getSupportCityString>
</soap:Body>
</soap:Envelope>''')
print(res.text)
```

> ~json格式     ----- json  后跟字典(常用)， or ----data  后跟json字符串

```python
res = requests.post(url='http://123.56.99.53:5000/event/weather/getWeather/',
headers={"Content-Type": "application/json"},
json={"theCityCode": "1001"})
print(res.text)
```

json后面跟的是字典即json={"theCityCode": "1001"})，可改成 data=‘{"theCityCode": "1001"})’；字典能自动转为json字符串，所以一般用字典表示；建议纯json正文的接口使用第一种方式，除非是较长的字符串

> 复合式表单(multipart/form-data ) ------上传二进制文件    ---- key value (接口名称：文件名）

```python
res = requests.post(url='http://123.56.99.53:9001/api/uploadFile/',
headers={"Cookie": "uid=1;token=44c972f05d76fdd93c31f9c2b65bb098f308cdfc"
#"Content-Type": "multipart/form-data"},
#有的接口不需要写正文体格式，老服务器一般需要写
              files={"myfile1": open('D:\全力以富\1.docx', 'rb')
                     #"myfile2: open(上传多个文件)"})
print(res.text)
```
上传接口 files={‘文件路径’,‘rb'}  rb表示

## 问题解决

### pycharm提示ModuleNotFoundError: No module named `requests`

File > Settings > Project: {project-name} > Python Interpreter