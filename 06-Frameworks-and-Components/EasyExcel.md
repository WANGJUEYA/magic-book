---
title: EasyExcel
summary: EasyExcel-高效导入导出工具
tags:
  - excel
  - java
categories:
  - 06-Frameworks-and-Components
date: 2022-10-15 13:44:53
---

官方文档: https://easyexcel.opensource.alibaba.com/docs/current/

## 代码示例

### Excel限制下拉框文本长度不能超过 255, 新建一个sheet存储下拉框

https://github.com/alibaba/easyexcel/issues/1138

### 构造多页签主子表数据

```java
public class MasterTest {
    public static void main(String[] args) {

        // 方法3 如果写到不同的sheet 不同的对象
        String fileName = "C:\\Users\\JUE\\Desktop\\testData.xlsx";
        ExcelWriter excelWriter = null;
        try {
            // 这里 指定文件
            excelWriter = EasyExcel.write(fileName).build();
            // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
            WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "sheet1").head(headMaster()).build();
            excelWriter.write(dataMaster(), writeSheet1);
            WriteSheet writeSheet2 = EasyExcel.writerSheet(2, "sheet2").head(headSub()).build();
            excelWriter.write(dataSub(), writeSheet2);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    public static List<List<String>> headMaster() {
        List<List<String>> list = ListUtils.newArrayList();
        list.add(ListUtils.newArrayList("编码"));
        list.add(ListUtils.newArrayList("名称"));
        return list;
    }

    public static List<List<String>> headSub() {
        List<List<String>> list = ListUtils.newArrayList();
        list.add(ListUtils.newArrayList("主表编码"));
        list.add(ListUtils.newArrayList("子表编码"));
        list.add(ListUtils.newArrayList("子表名称"));
        return list;
    }

    public static List<List<Object>> dataMaster() {
        List<List<Object>> list = ListUtils.newArrayList();
        for (int i = 0; i < 100; i++) {
            list.add(ListUtils.newArrayList("code" + i, "名称" + i));
        }
        return list;
    }

    public static List<List<Object>> dataSub() {
        List<List<Object>> list = ListUtils.newArrayList();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                list.add(ListUtils.newArrayList("code" + i, "sub" + j, "子表名称" + j));
            }
        }
        return list;
    }

}
```