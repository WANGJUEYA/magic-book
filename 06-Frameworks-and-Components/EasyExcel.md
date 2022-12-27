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

+ 核心逻辑: 使用EXCEL 数据 > 数据校验 > `=select04!$A$1:$A$4`
+ 同样可以使用公式(不能使用数组) > `=IF($B$13=1,select00!$A$1:$A$44,IF($B$13=2,select03!$A$1:$A$10,select04!$A$1:$A$4))`
+ 可以使用子串截取的方式从同一个下拉框获取数据 | Excel 省市县联动下拉框

```java
@Slf4j
public class ImportSelectSheetWriteHandler implements SheetWriteHandler {

    /**
     * 自定义的行和列
     * key为下拉框对应的列号, 从0开始计算
     * value存储一个map(其中key为数据库读取的值/value为显示展示的值)
     */
    private final Map<Integer, String[]> customSelect;

    /**
     * 生成下拉框最后行号
     */
    private int lastRow;

    public ImportSelectSheetWriteHandler(Map<Integer, String[]> customSelect) {
        this.customSelect = customSelect;
        this.lastRow = lastRowConfig();
    }

    public ImportSelectSheetWriteHandler lastRow(int lastRow) {
        if (lastRow > 1) {
            this.lastRow = lastRow;
        }
        return this;
    }

    private int lastRowConfig() {
        try {
            String limit = SpringUtils.getBean(SystemConfigHelper.class).getConfigValue("SYS_LIMIT_IMPORT");
            int result = ConvertUtils.toInt(limit, 1000);
            if (result > 1) {
                return result;
            }
        } catch (Exception e) {
            log.error("获取导入上限配置失败!", e);
        }
        return 1000;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        log.info("第{}个Sheet写入成功。", writeSheetHolder.getSheetNo());
        if (customSelect != null && customSelect.size() > 0) {
            for (int key : customSelect.keySet()) {
                CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, lastRow, key, key);

                String sheetName = "select" + writeSheetHolder.getSheetNo() + key;
                String[] select = customSelect.get(key);
                generateOtherSheet(writeWorkbookHolder.getWorkbook(), sheetName, select);

                String formula = sheetName + "!$A$1:$A$" + select.length;
                DataValidationHelper helper = writeWorkbookHolder.getWorkbook().getSheet(sheetName).getDataValidationHelper();
                DataValidationConstraint constraint = helper.createFormulaListConstraint(formula);
                DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
                writeSheetHolder.getSheet().addValidationData(dataValidation);
                writeWorkbookHolder.getWorkbook().setSheetHidden(writeWorkbookHolder.getWorkbook().getSheetIndex(sheetName), true);
            }
        }
    }

    private void generateOtherSheet(Workbook wb, String sheetName, String[] select) {
        // 创建下拉列表值存储工作表
        Sheet sheet = wb.createSheet(sheetName);
        // 循环往该sheet中设置添加下拉列表的值
        int index = 0;
        for (String key : select) {
            Row row = sheet.createRow(index++);
            Cell cellValue = row.createCell(0);
            cellValue.setCellValue(key);
        }
    }
}
```

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
