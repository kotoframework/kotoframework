# 🧷约定：

### 1.数据表表名和字段默认使用小写字母，以下划线隔开; 对应的KPojo使用其转换的驼峰形式；

```kotlin
UserInfo -> user_info

userName -> user_name
```

### 2.每张表需要create_time和update_time字段，由koto完全托管，无需在KPojo中体现；

##### 3.类型转换：

```kotlin
(int) -> Kotlin.Int
(float) -> Kotlin.Float
(unsigned int) -> Kotlin.Long
(tinyint) -> Kotlin.Boolean(if you need Kotlin.Int,  change the datasource url: tinyInt1isBit=false)
(Date/Datetime) -> format as Kotlin.String
(varchar/text/others) -> Kotlin.String
```

### 4.在查询语句中，当使用where()简略查询时，KPojo中为null的属性将被过滤，如：

```kotlin
data class User(val id: Int? = null, val name: String? = null)

val user = User(1)
val koto = select(user).where().query()
// select id, name from user where id = 1
```

##### 若想要让属性为null时自动转换为is null，则可以使用ifNoValue：

```kotlin
select(user).where().ifNoValue{ Smart }.query()
//或
select(user).where(user::id.eq.ifNoValue{ IsNull } and user::name.eq.ifNoValue{ IsNull }).query()
// select id, name from user where id = 1 and name is null
```

##### 在update、remove时，KPojo中为null的属性将转换为is null，如：

```kotlin
remove(user).execute()
//remove user where id = 1 and name is null
```

