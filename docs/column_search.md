# 🔎Column Search

In koto, use the `columnSearch` function to query a list of options with case-insensitive fuzzy search:

### Default query fieldName field

```kotlin
columnSearch(
    User::name to "ousc"
).queryForList<String>()
```

### When you need to query other fields:

```kotlin
columnSearch(
    User::name to "ousc",
    listOf(User::username, User::age, *fields)
).queryForList<String>()
    .queryForList<String>()
```

### The way to pass in a string table name (no longer recommended)

```kotlin
columnSearch(
    "table_name",
    "fieldName" to searchValue,
    listOf("field1", "field2", *fields)
)
```

## dynamic data source

koto supports dynamic data source, the usage depends on the extension functions provided by your wrapper, for example, if you use koto-basic-wrapper:

```kotlin
val db: DataSource = YourDataSource()
val movies = db.columnSearch(User::name to "ousc").queryForList()
```

