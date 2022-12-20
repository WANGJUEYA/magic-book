---
title: Regular expression

date: 2022-10-15 14:44:14

summary: Regular expression | 正则表达式介绍及好用表达式示例

tags:
    - regex
categories:
    - 04-Programming-Languages
    - Assistant-File-Types
---

在线练习网站 https://regexone.com/

## 常用实例

### 匹配第10次出现的逗号

```
(([^,]*,){10})
```

```
VALUES ('0001PlatParent', '0001', NULL, '1', '0', '-1', NULL, 'plat', '/plat', 'PlatBasicLayout', NULL, 'N', '大屏菜单(固定位于首位)', 'home', NULL, 'N', 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

VALUES (([^,]*,){12})(([^\']*\'){1})([^\']*)

VALUES $1 '{"zh-CN":"$5","en-US":"welcome"}

```

### IDEA 下划线转驼峰(java不支持)

+ http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
+ `\w`范围为`_0-9a-zA-Z`包含下划线

```text
_?([0-9a-zA-Z])([0-9a-zA-Z]*)
```
```text
\U$1\E\L$2\E
```
