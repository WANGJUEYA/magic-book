---
title: SQLite
summary: SQLite
tags:
  - Database
  - SQLite
categories:
  - 06-Frameworks-and-Components
  - Database
date: 2023-12-13 15:28:27
---

无需下载任何安装包，也无需启动任何数据库服务。就能维护一个SQLite数据库及在Spring程序中使用SQLite数据库。

## DBeaver连接SQLite(DBeaver会自动安装相关驱动)

+ 任意文件夹下创建一个文件，文件名为 `database.db`
+ 新建一个SQLite连接，填写文件路径

![DBeaver-setting](SQLite/DBeaver-setting.png)

+ 菜鸟教程 https://www.runoob.com/sqlite/sqlite-data-types.html

## jdbc集成

### 引入maven依赖

```xml
<!--https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.46.0.0-->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.46.0.0</version>
</dependency>
```

```java
public class JdbcSimpleUtils<T extends DataBase> {

    // "jdbc:sqlite:test.db"
    private String url;
    private String tableName;

    private Class<T> tClass;
    private List<String> tableColumns;
    private Map<String, Field> columnMap;

    private JdbcSimpleUtils() {
    }

    private JdbcSimpleUtils(String url, Class<T> tClass) {
        this.url = url;
        this.tClass = tClass;
    }

    private void init(Class<T> tClass) {
        tableName = tClass.getAnnotation(TableName.class).value();
        tableColumns = new ArrayList<>();
        columnMap = new HashMap<>();
        ReflectionUtils.doWithFields(tClass, f -> {
            f.setAccessible(true);
            TableField tableField = f.getAnnotation(TableField.class);
            if (tableField != null) {
                tableColumns.add(tableField.value());
                columnMap.put(tableField.value(), f);
            }
        });
    }

    public static <T extends DataBase> JdbcSimpleUtils<T> instance(String url, Class<T> tClass) {
        JdbcSimpleUtils<T> res = new JdbcSimpleUtils<>(url, tClass);
        res.init(tClass);
        if (!res.exist()) {
            res.create();
        }
        return res;
    }

    public List<T> list() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return executeQuery("SELECT * FROM " + tableName, res -> {
            List<T> result = new ArrayList<>();
            try {
                while (res.next()) {
                    T data = tClass.getDeclaredConstructor().newInstance();
                    String id = res.getString("ID");
                    data.setId(id);
                    for (Map.Entry<String, Field> entry : columnMap.entrySet()) {
                        ReflectionUtils.setField(entry.getValue(), data, res.getString(entry.getKey()));
                    }
                    result.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        });
    }

    public void insertOrUpdate(List<T> list) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (list == null || list.size() == 0) {
            return;
        }
        Map<String, T> map = list().stream().collect(Collectors.toMap(DataBase::getId, Function.identity()));
        List<String> sqlList = new ArrayList<>();
        for (T data : list) {
            if (map.containsKey(data.getId())) {
                sqlList.add(updateByIdSql(data));
            } else {
                sqlList.add(insertSql(data));
            }
        }
        executeUpdate(sqlList);
    }

    public String insertSql(T data) {
        List<String> columns = new ArrayList<>();
        List<String> values = new ArrayList<>();
        columns.add("ID");
        values.add(data.getId());
        for (Map.Entry<String, Field> entry : columnMap.entrySet()) {
            columns.add(entry.getKey());
            values.add("'" + ReflectionUtils.getField(entry.getValue(), data) + "'");
        }
        return String.format("INSERT INTO %s(%s) VALUES(%s);", tableName, String.join(",", columns), String.join(",", values));
    }

    public void insert(List<T> list) throws SQLException {
        if (list == null || list.size() == 0) {
            return;
        }
        executeUpdate(list.stream().map(this::insertSql).collect(Collectors.toList()));
    }

    public String updateByIdSql(T data) {
        String id = data.getId();
        String set = columnMap.entrySet().stream()
                .map((e) -> String.format("%s='%s'", e.getKey(), ReflectionUtils.getField(e.getValue(), data)))
                .collect(Collectors.joining(","));
        return String.format("UPDATE %s SET %s WHERE ID = '%s';", tableName, set, id);
    }

    public void updateById(List<T> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        executeUpdate(list.stream().map(this::updateByIdSql).collect(Collectors.toList()));
    }

    public void deleteByIds(List<String> ids) {
        executeUpdate(String.format("DELETE FROM %s WHERE ID IN (%s)", tableName, String.join("','", ids)));
    }

    public boolean exist() {
        return executeQuery("SELECT COUNT(1) FROM " + tableName, res -> {
            try {
                return res.next();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public void create() {
        String BEGIN = "CREATE TABLE %s (\n ID INT PRIMARY KEY NOT NULL,\n";
        String LINE = "%s TEXT";
        String END = ")";
        executeUpdate(String.format(BEGIN, tableName) + tableColumns.stream().map(f -> String.format(LINE, f)).collect(Collectors.joining(",\n")) + END);
    }

    public <R> R executeQuery(String sql, Function<ResultSet, R> function) {
        return execute((statement -> {
            try {
                ResultSet set = statement.executeQuery(sql);
                return function.apply(set);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }));
    }

    public void executeUpdate(String sql) {
        executeUpdate(Lists.newArrayList(sql));
    }

    public void executeUpdate(List<String> sql) {
        execute((statement -> {
            for (String sqlItem : sql) {
                try {
                    statement.executeUpdate(sqlItem);
                } catch (SQLException e) {
                    System.out.println(sqlItem);
                    e.printStackTrace();
                }
            }
            return null;
        }));
    }

    public <R> R execute(Function<Statement, R> fn) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            stmt = c.createStatement();
            return fn == null ? null : fn.apply(stmt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
```
