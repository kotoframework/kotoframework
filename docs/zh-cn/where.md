# Where条件对象

where条件对象提供了定义复杂查询/删除/更新条件的功能。

你可以使用where条件对象组成复杂的查询条件在select、remove、update、associate中使用，如:

```kotlin
select().where(...).query()
update().where(...).execute()
associate().where(...).query()
remove().where(...).execute()
```

示例KPojo类及查询条件：

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
```

## 使用方法：

### 1.基本用法

不传值默认会根据传入的KPojo自动生成条件：

```kotlin
val koto = xxx(user).where().build()
```
结果：
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
```

### 2.复杂查询用法：

```kotlin
val koto = xxx(user).where { // it: User
    it::userName.eq() and // koto通过Kotlin的反射机制，自动获取属性名
    it::active.eq() and
    it::nickName.like(pos = Left) and
    "telephone".notEq() and // 可以直接使用字符串，属性名称.条件函数名()
    it::roles.isIn(listOf(1, 2, 3)) and
    "emailAddress".isNull() and
    "habit".notNull() and
    "DATE_FORMAT(`birthday`, '%Y-%m-%d') = :birthday" and
    "`habit` = ${(searchKPojo.sex == "male").yes { "golf" } ?: "tennis"}" and
    "`sex` is not null" and
    "`age` < 50"
}
```
结果：
```kotlin
//koto.sql
"deleted = 0 and `user_name` = :userName and `active` = :active and `nick_name` like :nickName and `telephone` != :telephone and `roles` in (:roles) and `email_address` is null and `habit` is not null and DATE_FORMAT(`birthday`, '%Y-%m-%d') = :birthday and `habit` = "basketball" and `sex` is not null and `age` < 50"

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
```

### 3. or和and关键词的使用：

```kotlin
val koto = xxx(user).where{
    ("userName".eq() or "nickName".eq()) and
    ("telephone".eq() or "emailAddress".eq()) and
    ("emailAddress".eq() or "roles".eq()) and
    ("habit".eq() or "active".eq()) and
    ("birthday".eq() or "age".lt()) and
    "srq is not null"
}.build()
```

结果：

```kotlin
//koto.sql:
"deleted = 0 and (`user_name` = :userName or `nick_name` = :nickName) and (`telephone` = :telephone or `email_address` = :emailAddress) and (`email_address` = :emailAddress or `roles` = :roles) and (`habit` = :habit or `active` = :active) and (`birthday` = :birthday or `age` < :ageMax) and srq is not null"

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
```



## Condition 类型

### 相等判读`.eq()|.notEq()`

.eq()和.notEq()用于相等/不相等判断，可以接受以下4个参数：

| 参数名       | 说明                                        | 类型     | 默认值 | 全局配置 |
| ------------ | ------------------------------------------- | -------- | ------ | -------- |
| `value`      | 判断的值，若不传入，则默认从KPojo对象中读取 | Any?     | null   |          |
| `reName`     | 重命名paramMap中的key值，防止重复           | String?  | null   |          |
| `iif`        | 当`iif`为false时则去掉该条件                | Boolean? | null   |          |
| `humpToline` | 属性名转为列名时是否由驼峰自动转换为下划线  | Boolean  | true   | ✅        |

使用示例：

```kotlin
select(User(1), User::userName).where{ it::id.eq() }.query()
//也可写作：select(User(), User::userName).where{ it::id.eq(1) }.query()
// select user_name from user where id = 1

select(User(1), User::userName).where{ it::id.notEq() }.query()
//也可写作：select(User(), User::userName).where{ it::id.notEq(1) }.query()
// select user_name from user where id != 1
```

若不需要接受参数时，可省略括号：

```kotlin
select(User(1), User::userName).where{ it::id.eq }.query()
//该写法等同于上方第一种写法
```

在查询(select/associate)时，若where某条件的值为null，koto会默认删除此条件，若需要在值为null时转换为.isNull()/.notNull(),可以使用.orNull()函数，(在remove/update的where条件中，会自动转换为isNull())

此函数不仅在.eq中适用，同时适用于相似判断、数值比较、日期比较等，在下文中不在重复介绍，可见以下示例：

```kotlin
select(User(1), User::userName).where{ it::id.eq() and User::userName.eq() }.query()
// select user_name from user where id = 1

select(User(1), User::userName).where{ it::id.eq() and User::userName.eq().orNull() }.query()
// select user_name from user where id = 1 and user_name is null
```

在条件后链式调用`.iif(Boolean)`函数可直接修改条件的iif值

此函数不仅在.eq中适用，同时适用于相似判断、数值比较、日期比较等，在下文中不在重复介绍，可见以下示例：

```kotlin
select(User(1, "koto"), User::userName).where{ it::id.eq() and User::userName.eq().iif(false) }.query()
// select user_name from user where id = 1
```

在条件后链式调用`.reName(String)`函数可直接修改条件的reName值

此函数不仅在.eq中适用，同时适用于相似判断、数值比较、日期比较等，在下文中不在重复介绍，可见以下示例：

```kotlin
select(User(1, "koto"), User::userName).where{ it::id.eq() and User::userName.eq().reName("name") }.query()
//paramMap:
mapOf(
  "id": 1,
  "name": "koto"
)
```



### 相似判断`.like()|.notLike()`

.like()和.notLike()用于相似判断

| 参数名       | 说明                                                 | 类型         | 默认值 | 全局配置 |
| ------------ | ---------------------------------------------------- | ------------ | ------ | -------- |
| `expression` | 判断的值，若不传入，则默认从KPojo对象中读取，需带“%” | Any?         | null   |          |
| `pos`        | 模糊匹配的方向，设定后expression不需加百分号         | LikePosition | Never  |          |
| `reName`     | 重命名paramMap中的key值，防止重复                    | String?      | null   |          |
| `iif`        | 当`iif`为false时则去掉该条件                         | Boolean?     | null   |          |
| `humpToline` | 属性名转为列名时是否由驼峰自动转换为下划线           | Boolean      | true   | ✅        |

使用示例：

```kotlin
select(User(userName = "%koto"), User::userName).where{ it::userName.like()}.query()
//也可写作：select(User(), User::userName).where{ it::userName.like("%koto")}.query()
// select user_name from user where user_name like "%koto"

select(User(userName = "%koto"), User::userName).where{ it::userName.notLike()}.query()
//也可写作：select(User(), User::userName).where{ it::userName.notLike("%koto")}.query()
// select user_name from user where user_name not like "%koto"
```

可以使用.matchLeft()/.matchRight()/.matchboth()链式调用代替设置pos = LikePosition.xxxx，例

```kotlin
select(User(userName = "%koto"), User::userName).where{user::userName.like("koto").matchLeft()}.query()
//等同于user::userName.like(pos = Left)
// select user_name from user where user_name not like "%koto"
```

若不需要接受参数时，可省略括号：

```kotlin
select(User(userName = "%koto"), User::userName).where{ it::userName.like}.query()
//该写法等同于上方第一种写法
```

### 数值比较`.gt()|.ge()|.lt()|.le()`

gt、ge、lt、le分别代表大于、大于等于、小于、小于等于判断，用于数值比较可以接受以下4个参数：

| 参数名       | 说明                                        | 类型           | 默认值 | 全局配置 |
| ------------ | ------------------------------------------- | -------------- | ------ | -------- |
| `value`      | 判断的值，若不传入，则默认从KPojo对象中读取 | Comparable<*>? | null   |          |
| `reName`     | 重命名paramMap中的key值，防止重复           | String?        | null   |          |
| `iif`        | 当`iif`为false时则去掉该条件                | Boolean?       | null   |          |
| `humpToline` | 属性名转为列名时是否由驼峰自动转换为下划线  | Boolean        | true   | ✅        |

使用示例：

```kotlin
select(User(1), User::userName).where{ it::id.gt() }.query()
//也可写作：select(User(), User::userName).where{ it::id.gt(1) }.query()
// select user_name from user where id > 1

select(User(1), User::userName).where{ it::id.ge() }.query()
//也可写作：select(User(), User::userName).where{ it::id.ge(1) }.query()
// select user_name from user where id >= 1

select(User(1), User::userName).where{ it::id.lt() }.query()
//也可写作：select(User(), User::userName).where{ it::id.lt(1) }.query()
// select user_name from user where id < 1

select(User(1), User::userName).where{ it::id.le() }.query()
//也可写作：select(User(), User::userName).where{ it::id.ge(1) }.query()
// select user_name from user where id <= 1
```

若不需要接受参数时，可省略括号：

```kotlin
select(User(1), User::userName).where{ it::id.gt }.query()
//该写法等同于上方第一种写法
```



### 日期比较`.before()|.after()|.notBefore()|.notAfter()`

before、after、notBefore、notAfter分别代表早于、晚于、不早于、不晚于判断，用于日期比较，可以接受以下4个参数：

| 参数名       | 说明                       | 类型           | 默认值 | 全局配置 |
| ------------ |--------------------------| -------------- | ------ | -------- |
| `value`      | 判断的值，若不传入，则默认从KPojo对象中读取 | Comparable<*>? | null   |          |
| `reName`     | 重命名paramMap中的key值，防止重复   | String?        | null   |          |
| `iif`        | 当`iif`为false时则去掉该条件      | Boolean?       | null   |          |
| `humpToline` | 属性名转为列名时是否由驼峰自动转换为下划线    | Boolean        | true   | ✅        |

使用示例：

```kotlin
select(User(1), User::userName).where{ it::id.gt() }.query()
//也可写作：select(User(), User::userName).where{ it::id.gt(1) }.query()
// select user_name from user where id > 1

select(User(1), User::userName).where{ it::id.ge() }.query()
//也可写作：select(User(), User::userName).where{ it::id.ge(1) }.query()
// select user_name from user where id >= 1

select(User(1), User::userName).where{ it::id.lt() }.query()
//也可写作：select(User(), User::userName).where{ it::id.lt(1) }.query()
// select user_name from user where id < 1

select(User(1), User::userName).where{ it::id.le() }.query()
//也可写作：select(User(), User::userName).where{ it::id.le(1) }.query()
// select user_name from user where id <= 1
```

若不需要接受参数时，可省略括号：

```kotlin
select(User(1), User::userName).where{ it::id.gt }.query()
//该写法等同于上方第一种写法
```



### 区间比较`.between()/.notBetween()`

between、notBetween分别代表介于区间、不介于区间判断，用于日期和数值比较，可以接受以下4个参数：

| 参数名       | 说明                                                         | 类型           | 默认值 | 全局配置 |
| ------------ | ------------------------------------------------------------ | -------------- | ------ | -------- |
| `value`      | 区间范围，可以为数值、日期等，<strong><u>必须传入</u></strong> | ClosedRange<*> | /      |          |
| `reName`     | 重命名paramMap中的key值，防止重复                            | String?        | null   |          |
| `iif`        | 当`iif`为false时则去掉该条件                                 | Boolean?       | null   |          |
| `humpToline` | 属性名转为列名时是否由驼峰自动转换为下划线                   | Boolean        | true   | ✅        |

使用示例：

```kotlin
select(User(1), User::userName).where{ it::id.between(1..100) }.query()
// select user_name from user where id between 1 and 100

select(User(1), User::userName).where{ it::id.notBetween(1..100) }.query()
// select user_name from user where id not between 1 and 100
```



### 范围判断`.isIn()|.notIn()`

isIn、notIn分别代表是否处于某集合判断，可以接受以下4个参数：

| 参数名       | 说明                                                         | 类型          | 默认值 | 全局配置 |
| ------------ | ------------------------------------------------------------ | ------------- | ------ | -------- |
| `value`      | 集合,可传入Array/List/MutatbleList等，<strong><u>必须传入</u></strong> | Collection<*> | /      |          |
| `reName`     | 重命名paramMap中的key值，防止重复                            | String?       | null   |          |
| `iif`        | 当`iif`为false时则去掉该条件                                 | Boolean?      | null   |          |
| `humpToline` | 属性名转为列名时是否由驼峰自动转换为下划线                   | Boolean       | true   | ✅        |

使用示例：

```kotlin
select(User(1), User::userName).where{ it::id.in(listOf(1, 2, 3)) }.query()
// select user_name from user where id in (1, 2, 3)

select(User(1), User::userName).where{it::id.notIn(listOf(1, 2, 3)) }.query()
// select user_name from user where id not in (1, 2, 3)
```



### 空值判断`.isNull()|.notNull()`

isNull、notNull用于空值判断，可以接受以下4个参数：

| 参数名       | 说明                                       | 类型     | 默认值 | 全局配置 |
| ------------ | ------------------------------------------ | -------- | ------ | -------- |
| `reName`     | 重命名paramMap中的key值，防止重复          | String?  | null   |          |
| `iif`        | 当`iif`为false时则去掉该条件               | Boolean? | null   |          |
| `humpToline` | 属性名转为列名时是否由驼峰自动转换为下划线 | Boolean  | true   | ✅        |

使用示例：

```kotlin
select(User(1), User::userName).where{ it::userName.isNull() }.query()
// select user_name from user where user_name is null

select(User(1), User::userName).where{it::id.notNull() }.query()
// select user_name from user where user_name is not null
```

若不需要接受参数时，可省略括号：

```kotlin
select(User(1), User::userName).where{ it::userName.isNull }.query()
//该写法等同于上方第一种写法
```



### 连接中缀`.and|.or`

请移步<a href="/#/zh-cn/where?id=_3-or和and关键词的使用：">or和and关键词的使用</a>



## Where Api

| 函数名                                    | 说明                                 | 备注              |
| ----------------------------------------- | ------------------------------------ | ----------------- |
| `.map(vararg Pair<String,Any?>)`          | 加入/覆盖参数Map值                   |                   |
| `.suffix(String)`                         | 设置后缀                             |                   |
| `.first()`                                | limit one                            | 仅查询中使用      |
| `.page(pageIndex: Int, pageSize: Int)`    | 查询分页                             | 仅查询中使用      |
| `.deleted()`                              | 查询逻辑删除数据                     | 仅查询中使用      |
| `.distinct()`                             | 查询不同                             | 仅查询中使用      |
| `.build()`                                | 生成带有sql和paramMap的KotoDataSet   |                   |
| `.orderBy(vararg Field)`                  | 排序规则                             | 仅查询中使用      |
| `.groupBy(vararg Field)`                  | 分组规则                             | 仅查询中使用      |
| `.allowNull(nullAllowed: Boolean = true)` | 条件的判断值为null时自动转为isNull() | 查询时默认为false |

