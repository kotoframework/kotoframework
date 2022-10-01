# ⚙️1分钟完成Koto配置

# 📌全局配置

## 全局数据源配置

##### 单数据源配置：

```kotlin
KotoApp.setDataSource(url, username, password, driverClassName)
```

或

```kotlin
KotoApp.init(BasicDataSource)
```

##### 动态/多数据源配置：

动态数据源配置由引入的<code>koto-wrapper</code>决定，如使用<code>koto-spring-wrapper</code>，则配置如下

```kotlin
KotoApp.setDynamicDataSource { namedJdbc }
// 在项目中手动配置获取数据源的方法，如：
// val namedJdbc: NamedParameterJdbcTemplate get() = {
//  ...
// }
```

使用<code>koto-basic-wrapper</code>及其他wrapper方法类似，进改变传入的对象类型

```kotlin
KotoApp.setDynamicDataSource { ds/jdbi/... }
// 在项目中手动配置获取数据源的方法，如：
// val ds: DataSource/Jdbi/... get() = {
//  ...
// }
```

## 全局日志生成配置

Koto会自动生成操作数据源、操作类型、操作表名、执行语句、执行数据的日志。

日志默认打印在控制台，你可以调用configLog(str)修改生成路径，
console代表打印到控制台，多个地址用「,」分开。

如果你不想生成日志，可以调用configLog("")设置为空。

```kotlin
KotoApp.setLogPath("console,C:/logs,/Users/user/logs")
```

## 全局软删除功能配置

*软删除*又叫逻辑删除,标记删除，在koto中默认关闭，通过以下设置开启和重命名标记列名：

```kotlin
KotoApp.setSoftDelete(true, "deleted") 
```

## 全局实体类后缀

实体类后缀，koto在没有注解配置的情况下默认使用KPojo的类名转下划线后的名称作为表名，若配置了全局KPojo后缀，取表名时则会去掉该后缀

```kotlin
KotoApp.setKPojoSuffix("Entity/Pojo/Dto/...")
```



## 全局驼峰转下划线设置

在koto中KPojo属性映射到数据表的列名时，默认会进行驼峰转下划线处理，若您少数表中不需要此功能，可以通过在condition中[设置hump2line为false](/#/zh-cn/where?id=condition-类型)关闭此功能，若需要全局关闭，可以进行以下设置：

```kotlin
KotoApp.setHump2line(false)
```



**上述配置可以链式调用，如：**

```kotlin
KotoApp
	.setDynamicDataSource(Datasource)
	.setLogPath(pathToLog)
	.setSoftDelete() //默认为true, "deleted"
	.setKPojoSuffix("Entity/Pojo/Dto/...")
```



# 📌注解配置



Koto为较为复杂的业务提供了少量的简单注解配置，用于满足部分全局配置无法解决的问题。



## Data Class注解：

### 1.`Table`注解

当KPojo没有<code>Table</code>注解时，Koto会将KClass类名进行驼峰转下划线处理后的结果作为表名。

通过<code>Table</code>注解可以为任意名称的实体类指定绑定的表名。

```kotlin
@Table(name = "user")
data class Alphabet(var id: Int? = null, var name: String? = null): KPojo
```



### 2.`SoftDelete`注解

当KPojo没有<code>SoftDelete</code>注解时，此对象的逻辑删除设置将遵循全局设置。

通过<code>SoftDelete</code>注解为可以为某个KPojo配置单独的逻辑删除开启状态或逻辑删除列名

```kotlin
@SoftDelete(enable = true,  column = "column_for_deleted")
data class User(var id: Int? = null, var name: String? = null): KPojo
```



## KProperty注解：

### 3.`Column`注解

当KPojo的属性名没有<code>Column</code>注解配置时，Koto会将Kproperty名称进行驼峰转下划线处理后的结果作为列名。

通过<code>Column</code>注解可以为任意名称的KPojo属性指定绑定的列名。

```kotlin
data class User(
  var id: Int? = null, 
  @Column(name = "username") var name: String? = null
): KPojo
```



### 4.`Default`注解

设置create对象时一列的默认值

```kotlin
data class User(
  var id: Int? = null, 
  var name: String? = null,
  @Default(value = "No introduce.") var introduce: String? = null
): KPojo
```



### 5.`DateTimeFormat`注解

koto为Date字段和DateTime字段查询时默认提供了格式化，若不使用<code>DateTimeFormat</code>注解，默认的格式化为：

> date: %Y-%m-%d
>
> datetime: %Y-%m-%d %H:%i:%s

通过<code>DateTimeFormat</code>注解，可以进行个性化的日期格式化设置。



```kotlin
data class User(
  var id: Int? = null, 
  var name: String? = null,
  @DateTimeFormat(pattern = "%Y/%m/%d")var regDate: String? = null
): KPojo
```

