---
title: Leetcode idea plugin template
tags:
  - IDEA
  - Leetcode
categories:
  - IDE
  - IntelliJ IDEA
date: 2022-09-18 13:11:00
---

```
D:\code\basic-java\src\main\java\com\jue\java\learn
```

```
$!velocityTool.camelCaseName(${question.titleSlug})/$!velocityTool.camelCaseName(${question.titleSlug})
```

```
${question.content}

package com.jue.java.learn.leetcode.editor.cn.$!velocityTool.camelCaseName(${question.titleSlug});

/**
 * @author JUE
 * @number ${question.frontendQuestionId}
 */
public class $!velocityTool.camelCaseName(${question.titleSlug}) {
    public static void main(String[] args) {
        Solution solution = new Solution();
    }
}

${question.code}
```

