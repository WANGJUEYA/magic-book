---
title: Elasticsearch
summary: Elasticsearch
tags:
  - TODO
  - Database
  - Elasticsearch
categories:
  - 06-Frameworks-and-Components
date: 2022-10-15 15:10:25
---

## 常用查询命令

### 查询版本号 (7.6.2)

```http request
GET http://192.168.100.38:9200
```

### 查询健康情况

```http request
GET http://192.168.100.38:9200/_cluster/health?pretty
```

### 查询所有节点数

```http request
GET http://192.168.100.38:9200/_cat/nodes?pretty
```

### 查询所有节点信息

```http request
GET http://192.168.100.38:9200/_cat/allocation?pretty
```

### 查询所有索引

```shell
curl -XGET http://192.168.100.38:9200/_cat/indices?pretty
```

```http request
GET http://192.168.100.38:9200/_cat/indices?v&pretty
```

### 删除索引[可通配符]

```shell
curl -XDELETE http://192.168.100.38:9200/kms_knowledge*
```

### 查询数据结构

```http request
GET kms_knowledge_check/_mapping?pretty
```

### 查询索引的所有数据

```http request
GET kms_knowledge_check/_search?pretty
```

#### attachment

+ 查询

```http request
GET _ingest/pipeline
```

+ 删除

```http request
DELETE _ingest/pipeline/simple_attachment
```

+ 新增

```http request
PUT _ingest/pipeline/my_attachment
```

```json
{
  "description": "Extract attachment for knowledge",
  "processors": [
    {
      "attachment": {
        "field": "knowContent"
      }
    }
  ]
}
```

+ 关联数据

```http request
PUT kms_knowledge/_doc/my_id?pipeline=my_attachment
```

```json
{
  "knowContent": "e1xydGYxXGFuc2kNCkxvcmVtIGlwc3VtIGRvbG9yIHNpdCBhbWV0DQpccGFyIH0="
}
```

+ 查询数据

```http request
GET kms_knowledge/_doc/my_id
```

```http request
GET kms_knowledge_test/_search
```

```json
{
  "query": {
    "match": {
      "attachment.content": "测试"
    }
  }
}
```

+ 更新数据

```http request
POST kms_knowledge/_update/string
```

```json
{
  "script": {
    "source": "TemporalAccessor tem = DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm:ss', Locale.CHINA).parse(ctx._source['sysCreateTime']); ctx._source['countHot'] = 1 + tem.getLong(ChronoField.EPOCH_DAY);"
  }
}
```
