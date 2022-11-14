# Where condition object

The where condition object provides the ability to define complex query/delete/update conditions.

You can use where condition objects to form complex query conditions for use in select, remove, update, associate, such as:

```kotlin
select().where(...).query()
update().where(...).execute()
associate().where(...).query()
remove().where(...).execute()
````

Example KPojo class and query conditions:

```kotlin
data class User(
     val id: Int? = null,
     val userName: String? = null,
     val telephone: String? = null,
     val emailAddress: String? = null,
     val active: Boolean? = null,
     val birthday: String? = null,
     val sex: String? = null,
     val habit: String? = null,
     val age: Int? = null,
     val roles: List<String>? = null
) : KPojo

val user = User(
     userName = "ousc",
     active = true,
     sex = "male",
     age = 99
)
````
## Instructions:

### 1. Basic usage

If no value is passed, the condition will be automatically generated based on the incoming KPojo:

```kotlin
val koto = xxx(user).where().build()
````
result:
```kotlin
//koto.sql
"userName = :userName and active = :active and sex = :sex and age = :age and deleted = 0"

//koto:paramMap
mapOf(
    "userName" to "ousc",
    "active" to true,
    "sex" to "male",
    "age" to 99
)
````

### 2. Complex query usage:

```kotlin
val koto = xxx(user).where { // it: User
    it::userName.eq() and // koto automatically obtains the property name through Kotlin's reflection mechanism
    it::active.eq() and
    it::nickName.like(pos = Left) and
    "telephone".notEq() and // Strings can be used directly, property names. Conditional function names()
    it::roles.isIn(listOf(1, 2, 3)) and
    "emailAddress".isNull() and
    "habit".notNull() and
    "DATE_FORMAT(`birthday`, '%Y-%m-%d') = :birthday" and
    "`habit` = ${(searchKPojo.sex == "male").yes { "golf" } ?: "tennis"}" and
    "`sex` is not null" and
    "`age` < 50"
}
````
result:
```kotlin
//koto.sql
"deleted = 0 and `user_name` = :userName and `active` = :active and `nick_name` like :nickName and `telephone` != :telephone and `roles` in (:roles) and `email_address` is null and ` habit` is not null and DATE_FORMAT(`birthday`, '%Y-%m-%d') = :birthday and `habit` = "basketball" and `sex` is not null and `age` < 50"

//koto:paramMap
mapOf(
    "userName" to "ousc",
    "nickName" to "%daiyue",
    "telephone" to "13800138000",
    "emailAddress" to "hangzhou@qq.com",
    "roles" to listOf("1", "2"),
    "habit" to "eat",
    "birthday" to "2020-01-01",
    "active" to true,
    "sex" to "male",
    "age" to 99
)
````

### 3. The use of or and and keywords:

```kotlin
val koto = xxx(user).where{
    ("userName".eq() or "nickName".eq()) and
    ("telephone".eq() or "emailAddress".eq()) and
    ("emailAddress".eq() or "roles".eq()) and
    ("habit".eq() or "active".eq()) and
    ("birthday".eq() or "age".lt()) and
    "srq is not null"
}.build()
````

result:

```kotlin
//koto.sql:
"deleted = 0 and (`user_name` = :userName or `nick_name` = :nickName) and (`telephone` = :telephone or `email_address` = :emailAddress) and (`email_address` = :emailAddress or `roles` = : roles) and (`habit` = :habit or `active` = :active) and (`birthday` = :birthday or `age` < :ageMax) and srq is not null"

//koto.paramMap
mapOf(
    "id" to null,
    "userName" to "ousc",
    "nickName" to "daiyue",
    "telephone" to "13800138000",
    "emailAddress" to "hangzhou@qq.com",
    "roles" to listOf("admin", "user"),
    "habit" to "eat",
    "birthday" to "2020-01-01",
    "active" to true,
    "sex" to "male",
    "age" to 99,
    "ageMax" to 99
)
````


## Condition type

### Equality reading `.eq()|.notEq()`

.eq() and .notEq() are used for equality/inequality judgment and can accept the following 4 parameters:

| parameter name | description | type | default value |
| ------------ | ------------------------------------------- | -------- | ------ |
| `value` | The value to be judged, if not passed in, it will be read from the KPojo object by default | Any? | null |

Example of use:

```kotlin
select(User(1), User::userName).where{ it::id.eq() }.query()
// can also be written: select(User(), User::userName).where{ it::id.eq(1) }.query()
// select user_name from user where id = 1

select(User(1), User::userName).where{ it::id.notEq() }.query()
// can also be written: select(User(), User::userName).where{ it::id.notEq(1) }.query()
// select user_name from user where id != 1
````

If you don't need to accept parameters, you can omit the parentheses:

```kotlin
select(User(1), User::userName).where{ it::id.eq }.query()
//This writing method is equivalent to the first writing method above
````

When querying (select/associate), if the value of a condition where is null, koto will delete this condition by default. If you need to convert the value to .isNull()/.notNull() when the value is null, you can use .orNull() Function, (in the where condition of remove/update, it will be automatically converted to isNull())

This function is not only applicable in .eq, but also applicable to similarity judgment, numerical comparison, date comparison, etc., which will not be repeated in the following, but the following examples can be seen:

```kotlin
select(User(1), User::userName).where{ it::id.eq() and User::userName.eq() }.query()
// select user_name from user where id = 1

select(User(1), User::userName).where{ it::id.eq() and User::userName.eq().orNull() }.query()
// select user_name from user where id = 1 and user_name is null
````

> `iif` conditional statement is deprecated, please use `takeIf|takeUnless` or takeUnless instead

Chain call `.takeIf(()->Boolean)|.takeUnless(()->Boolean)` after the condition to control whether the condition is applied or not, if it returns false, the condition will not be applied
This function is not only applicable in .eq, but also applicable to similarity judgment, numerical comparison, date comparison, etc., which will not be repeated in the following, but the following examples can be seen:

```kotlin
select(User(1, "koto"), User::userName).where{ it::id.eq() and it::userName.eq.takeIf{ false }?: it::userName.isNull }.query ()
// select user_name from user where id = 1
````

> `reName` statement is deprecated, use `.alias` instead

Chaining the `.alias(String)` function after the condition can directly modify the reName value of the condition

This function is not only applicable in .eq, but also applicable to similarity judgment, numerical comparison, date comparison, etc., which will not be repeated in the following, but the following examples can be seen:

```kotlin
select(User(1, "koto"), User::userName).where{ it::id.eq() and User::userName.eq().alias("name") }.query()
//paramMap:
mapOf(
  "id" to 1,
  "name" to "koto"
)
````



### Similarity judgment `.like()|.notLike()`

.like() and .notLike() are used for similarity judgment

| parameter name | description | type | default value |
|---------| ---------------------------------------------------- | ------------ | ------ |
| `value` | The value to be judged, if not passed in, it will be read from the KPojo object by default, with "%" | Any? | null |

Example of use:

```kotlin
select(User(userName = "%koto"), User::userName).where{ it::userName.like()}.query()
// can also be written: select(User(), User::userName).where{ it::userName.like("%koto")}.query()
// select user_name from user where user_name like "%koto"

select(User(userName = "%koto"), User::userName).where{ it::userName.notLike()}.query()
// can also be written: select(User(), User::userName).where{ it::userName.notLike("%koto")}.query()
// select user_name from user where user_name not like "%koto"
````

Instead of adding `%` to the match value, you can use .matchLeft()/.matchRight()/.matchBoth() chaining, e.g.

```kotlin
select(User(userName = "%koto"), User::userName).where{user::userName.like("koto").matchLeft()}.query()
//equivalent to user::userName.like(pos = Left)
// select user_name from user where user_name not like "%koto"
````

If you don't need to accept parameters, you can omit the parentheses:

```kotlin
select(User(userName = "%koto"), User::userName).where{ it::userName.like}.query()
//This writing method is equivalent to the first writing method above
````

### Numerical comparison `.gt()|.ge()|.lt()|.le()`

gt, ge, lt, and le represent greater than, greater than or equal to, less than, and less than or equal to judgment, respectively, and can accept the following four parameters for numerical comparison:

| parameter name | description | type | default value |
| ------------ | ------------------------------------ ------- | -------------- | ------ |
| `value` | The value to be judged, if not passed in, it will be read from the KPojo object by default | Comparable<*>? | null |

Example of use:

```kotlin
select(User(1), User::userName).where{ it::id.gt() }.query()
// can also be written: select(User(), User::userName).where{ it::id.gt(1) }.query()
// select user_name from user where id > 1

select(User(1), User::userName).where{ it::id.ge() }.query()
// can also be written: select(User(), User::userName).where{ it::id.ge(1) }.query()
// select user_name from user where id >= 1

select(User(1), User::userName).where{ it::id.lt() }.query()
// can also be written: select(User(), User::userName).where{ it::id.lt(1) }.query()
// select user_name from user where id < 1

select(User(1), User::userName).where{ it::id.le() }.query()
// can also be written: select(User(), User::userName).where{ it::id.ge(1) }.query()
// select user_name from user where id <= 1
````

If you don't need to accept parameters, you can omit the parentheses:

```kotlin
select(User(1), User::userName).where{ it::id.gt }.query()
//This writing method is equivalent to the first writing method above
````

### Date comparison `.before()|.after()|.notBefore()|.notAfter()`

before, after, notBefore, notAfter respectively represent earlier than, later than, not earlier than, not later than judgment, used for date comparison, can accept the following 4 parameters:

| parameter name | description | type | default value |
| ------------ |--------------------------| -------------- | ------ |
| `value` | The value to be judged, if not passed in, it will be read from the KPojo object by default | Comparable<*>? | null |

Example of use:

```kotlin
select(User(1), User::userName).where{ it::id.gt() }.query()
// can also be written: select(User(), User::userName).where{ it::id.gt(1) }.query()
// select user_name from user where id > 1

select(User(1), User::userName).where{ it::id.ge() }.query()
// can also be written: select(User(), User::userName).where{ it::id.ge(1) }.query()
// select user_name from user where id >= 1

select(User(1), User::userName).where{ it::id.lt() }.query()
// can also be written: select(User(), User::userName).where{ it::id.lt(1) }.query()
// select user_name from user where id < 1

select(User(1), User::userName).where{ it::id.le() }.query()
// can also be written: select(User(), User::userName).where{ it::id.le(1) }.query()
// select user_name from user where id <= 1
````

If you don't need to accept parameters, you can omit the parentheses:

```kotlin
select(User(1), User::userName).where{ it::id.gt }.query()
//This writing method is equivalent to the first writing method above
````



### Interval comparison `.between()/.notBetween()`

between and notBetween respectively represent between interval and not between interval judgment, which are used for date and numerical comparison, and can accept the following 4 parameters:

| parameter name | description | type | default value |
| ------------ | ------------------------------------------------------------ | -------------- | ------ |
| `value` | interval range, can be numeric value, date, etc. <strong><u>must be passed in</u></strong> | ClosedRange<*> | / |

Example of use:

```kotlin
select(User(1), User::userName).where{ it::id.between(1..100) }.query()
// select user_name from user where id between 1 and 100

select(User(1), User::userName).where{ it::id.notBetween(1..100) }.query()
// select user_name from user where id not between 1 and 100
````



### Range judgment `.isIn()|.notIn()`

isIn and notIn respectively represent whether they are in a certain set and can accept the following 4 parameters:

| parameter name | description | type | default value |
| ------------ | ------------------------------------------------------------ | ------------- | ------ |
| `value` | Collection, can be passed in Array/List/MutatbleList, etc. <strong><u>must be passed in</u></strong> | Collection<*> | / |

Example of use:

```kotlin
select(User(1), User::userName).where{ it::id.isIn(listOf(1, 2, 3)) }.query()
// select user_name from user where id in (1, 2, 3)

select(User(1), User::userName).where{it::id.notIn(listOf(1, 2, 3)) }.query()
// select user_name from user where id not in (1, 2, 3)
````



### Null value judgment `.isNull()|.notNull()`

isNull and notNull are used for null value judgment

Example of use:

```kotlin
select(User(1), User::userName).where{ it::userName.isNull() }.query()
// select user_name from user where user_name is null

select(User(1), User::userName).where{it::id.notNull() }.query()
// select user_name from user where user_name is not null
````

If you don't need to accept parameters, you can omit the parentheses:

```kotlin
select(User(1), User::userName).where{ it::userName.isNull }.query()
//This writing method is equivalent to the first writing method above
````



### Connection infix `and|or`

Please move to <a href="/#/zh-cn/where?id=_3-Use of or and and keywords:">Use of or and and keywords</a>



## Where APIs

| function name | description | remarks |
| ----------------------------------------- | ------------------------------------ | ----------------- |
| `.map(vararg Pair<String,Any?>)` | Add/override parameter Map value | |
| `.suffix(String)` | set suffix | |
| `.first()` | limit one | only used in queries |
| `.page(pageIndex: Int, pageSize: Int)` | query pagination | only used in queries |
| `.deleted()` | query tombstone data | only used in queries |
| `.distinct()` | query is different | only used in query |
| `.build()` | Generate KotoDataSet with sql and paramMap | |
| `.orderBy(vararg Field)` | Collation | Only used in queries |
| `.groupBy(vararg Field)` | grouping rules | only used in queries |
| `.allowNull(nullAllowed: Boolean = true)` | When the judgment value of the condition is null, it is automatically converted to isNull() | The default value is false when querying |
