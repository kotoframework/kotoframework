# 🔎单列搜索

在koto中，使用`columnSearch`查询大小写模糊的选项列表：

### 默认查询fieldName字段

```kotlin
columnSearch(
    User::name to "ousc"
).queryForList<String>()
```

### 当需要查询其他字段时：

```kotlin
columnSearch(
    User::name to "ousc",
    listOf(User::username, User::age, *fields)
).queryForList<String>()
    .queryForList<String>()
```

### 传入字符串表名的写法（已不推荐使用）

```kotlin
columnSearch(
    "table_name",
    "fieldName" to searchValue,
    listOf("field1", "field2", *fields)
)
```

## 动态数据源

koto本身支持动态数据源，具体使用的形式由您使用的wrapper附带的扩展函数提供，比如，若您使用koto-basic-wrapper：

```kotlin
val db: DataSource = YourDataSource()
val movies = db.columnSearch(User::name to "ousc").queryForList()
```

