# 🧷Convention:

### 1. The table names and fields of the data table use lowercase letters by default, separated by underscores; the corresponding KPojo uses its converted camel case;

```kotlin
UserInfo -> user_info

userName -> user_name
```

### 2. Each table needs create_time and update_time fields, which are fully managed by koto and do not need to be reflected in KPojo;

##### 3. Type conversion:

```kotlin
(int) -> Kotlin.Int
(float) -> Kotlin.Float
(unsigned int) -> Kotlin.Long
(tinyint) -> Kotlin.Boolean(if you need Kotlin.Int, change the datasource url: tinyInt1isBit=false)
(Date/Datetime) -> format as Kotlin.String
(varchar/text/others) -> Kotlin.String
```

### 4. In the query statement, when using where() abbreviated query, the properties that are null in KPojo will be filtered, such as:

```kotlin
data class User(val id: Int? = null, val name: String? = null)

val user = User(1)
val koto = select(user).where().query()
// select id, name from user where id = 1
```

##### If you want to automatically convert the property to is null when it is null, you can use ifNoValue:

```kotlin
select(user).where().ifNoValue{ Smart }.query()
//or
select(user).where(user::id.eq.ifNoValue{ IsNull } and user::name.eq.ifNoValue{ IsNull }).query()
select(user).where(user::id.eq.ifNoValue{ IsNull } and user::name.isIn(list).ifNoValue{ False }).query()
// select id, name from user where id = 1 and name is null
```

##### During update and remove, the properties that are null in KPojo will be converted to is null, such as:

```kotlin
remove(user).execute()
//remove user where id = 1 and name is null
```
