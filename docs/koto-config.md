# ⚙️Complete Koto configuration in 1 minute

# 📌Global configuration

## global data source configuration

##### Single data source configuration:

```kotlin
KotoApp.setDataSource(url, username, password, driverClassName)
````

or

```kotlin
KotoApp.init(BasicDataSource)
````

##### Dynamic/multi-source configuration:

The dynamic data source configuration is determined by the imported <code>koto-wrapper</code>. If <code>koto-spring-wrapper</code> is used, the configuration is as follows

```kotlin
KotoApp.setDynamicDataSource { namedJdbc }
// Manually configure the method to get the data source in the project, such as:
// val namedJdbc: NamedParameterJdbcTemplate get() = {
// ...
// }
````

Use <code>koto-basic-wrapper</code> and other wrapper methods similar to change the incoming object type

```kotlin
KotoApp.setDynamicDataSource { ds/jdbi/... }
// Manually configure the method to get the data source in the project, such as:
// val ds: DataSource/Jdbi/... get() = {
// ...
// }
````

## Global log generation configuration

Koto will automatically generate logs of operation data source, operation type, operation table name, execution statement, and execution data.

The log is printed on the console by default, you can call configLog(str) to modify the generation path,
console represents printing to the console, and multiple addresses are separated by ",".

If you don't want to generate a log, you can call configLog("") to set it to empty.

```kotlin
KotoApp.setLogPath("console,C:/logs,/Users/user/logs")
````

## Global soft delete function configuration

*Soft delete*, also known as logical delete, mark delete, is turned off by default in koto, and the mark column name is enabled and renamed by the following settings:

```kotlin
KotoApp.setSoftDelete(true, "deleted")
````

## Global entity class suffix

Entity class suffix, koto uses KPojo's class name after underscore as the table name by default if there is no annotation configuration. If the global KPojo suffix is ​​configured, the suffix will be removed when taking the table name.

```kotlin
KotoApp.setKPojoSuffix("Entity/Pojo/Dto/...")
````



## Global camel case to underline settings

When the KPojo attribute in koto is mapped to the column name of the data table, the hump to underline processing will be performed by default. If you do not need this function in a few tables, you can [set hump2line to false](/#/where?id in the condition =condition-type) to turn off this function, if you need to turn it off globally, you can set the following:

```kotlin
KotoApp.setHump2line(false)
````



**The above configuration can be chained, such as:**

```kotlin
KotoApp
.setDynamicDataSource(Datasource)
.setLogPath(pathToLog)
.setSoftDelete() //default is true, "deleted"
.setKPojoSuffix("Entity/Pojo/KPojo/...")
````



# 📌 Annotation configuration



Koto provides a small number of simple annotation configurations for more complex services to meet some problems that cannot be solved by global configuration.



## Data Class annotation:

### 1. `Table` annotation

When KPojo does not have the <code>Table</code> annotation, Koto will use the camel case to underline KClass class name as the table name.

Through the <code>Table</code> annotation, you can specify the bound table name for any entity class with any name.

```kotlin
@Table(name = "user")
data class Alphabet(var id: Int? = null, var name: String? = null): KPojo
````



### 2. `SoftDelete` annotation

When KPojo does not have the <code>SoftDelete</code> annotation, the tombstone settings for this object will follow the global settings.

Annotated by <code>SoftDelete</code> to configure a single tombstone open state or tombstone column name for a KPojo

```kotlin
@SoftDelete(enable = true, column = "column_for_deleted")
data class User(var id: Int? = null, var name: String? = null): KPojo
````



## KProperty annotations:

### 3. `Column` annotation

When the property name of KPojo does not have the <code>Column</code> annotation configuration, Koto will convert the Kproperty name with camel case to underscore as the column name.

The <code>Column</code> annotation can specify the bound column name for the KPojo property of any name.

```kotlin
data class User(
  var id: Int? = null,
  @Column(name = "username") var name: String? = null
): KPojo
````



### 4. `Default` annotation

Set the default value of a column when creating an object

```kotlin
data class User(
  var id: Int? = null,
  var name: String? = null,
  @Default(value = "No introduce.") var introduce: String? = null
): KPojo
````



### 5. `DateTimeFormat` annotation

Koto provides formatting for Date field and DateTime field query by default. If you do not use the <code>DateTimeFormat</code> annotation, the default formatting is:

> date: %Y-%m-%d
>
> datetime: %Y-%m-%d %H:%i:%s

Through the <code>DateTimeFormat</code> annotation, personalized date formatting can be set.



```kotlin
data class User(
  var id: Int? = null,
  var name: String? = null,
  @DateTimeFormat(pattern = "%Y/%m/%d")var regDate: String? = null
): KPojo
````
