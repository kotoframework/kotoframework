Koto SQL Framework for Kotlin
=============================
[![build](https://github.com/kotoframework/kotoframework/actions/workflows/build.yml/badge.svg)](https://github.com/kotlin-orm/ktorm/actions/workflows/build.yml)
[![maven-central](https://img.shields.io/maven-central/v/com.kotoframework/koto-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.kotoframework%22)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

<img src="https://cdn.leinbo.com/assets/images/koto-logo.png" alt="koto" style="zoom: 33%;" />

official website: [https://kotoframework.com](https://kotoframework.com)

### ❓What is "koto"？

> An easy-to-use, flexible, lightweight data persistence layer ORM framework designed for kotlin.
>

Koto has designed concise operation entities and APIs. In most cases, only one line is needed to complete complex
logical functions on database tables:

```kotlin
val list = select<User>().queryForList() // list: List<User>
```

The actual SQL statement executed:

```sql
select `user_name`                                     as `userName`,
       `nickname`,
       `id`,
       `active`,
       DATE_FORMAT(`create_time`, '%Y-%m-%d %H:%i:%s') as `createTime`,
       DATE_FORMAT(`update_time`, '%Y-%m-%d %H:%i:%s') as `updateTime`
from user
```

You can also use the `query` function to return a `List<Map<String, Any>>`:

```kotlin
val list = select<User>().query() // list: List<Map<String, Any>>
```

The actual SQL statement executed:

```sql
select `user_name`                                     as `userName`,
       `nickname`,
       `id`,
       `active`,
       DATE_FORMAT(`create_time`, '%Y-%m-%d %H:%i:%s') as `createTime`,
       DATE_FORMAT(`update_time`, '%Y-%m-%d %H:%i:%s') as `updateTime`
from user
```

### ❓Why use "koto"？

1. Lightweight, only 200KB in size, hardly need any additional dependencies
2. Simple configuration, almost no configuration required
3. Supports object-relational mapping, which can be easily used to map between relational databases and business entity
   objects.
4. The writing method is simple and flexible, using chain calls, default parameters, extension functions and other
   writing methods, which are perfectly combined with Kotlin syntax.
5. It is easy to debug and has powerful and perfect log function.
6. Support multiple data sources, dynamic data sources, transactions with multiple data sources, and distributed
   transactions with multiple data sources.
7. Provide supporting code generator and ide plugin

### ❓How to use "koto"？

#### 1. Add dependency

##### maven

```xml

<dependency>
    <groupId>com.kotoframework</groupId>
    <artifactId>koto-core</artifactId>
    <version>1.0.1</version>
</dependency>
```

##### gradle

```groovy
compile "com.kotoframework:koto-core:${koto.version}"
```

```kotlin
complie("com.kotoframework:koto-core:${koto.version}")
```

#### 2. Configuration

[koto-config.md](koto-config.md)


### ⌨️ Write your first query function using Koto!

🎉Congratulations you have completed the configuration of Koto, now you can start to operate data easily and simply!

Below we will write our first function using Koto:

> koto does automatic mapping via type inference, which makes our queries very concise!

```kotlin
fun getUserInfoById(id): User {
    return select(User(1)).where().queryForObject()

    // another way of writing
    //select(User(1)).by("id").queryForObject()
}
// Koto actually supports more intuitive writing methods, see the specific Api documentation for details
// select(User(1)).by("id").queryForObject()
// select(User(1)).by(User::id).queryForObject()
// select(User(1)).where { it::id.eq }.queryForObject()
// select<User>().where { it::id.eq(1) }.queryForObject()
// select(User(1)).by("id").queryForObject()
// from<User> {  it.select(it).by(it::id to 1) }.queryForObject()
```

You can also use Where to create more complex query conditions, such as:

```kotlin
fun getUserBySomeCondition(user: User): UserInfo? { // query a single entity
    return select(user).where {
        it::id.eq and
                it::userName.notNull and
                it::active.eq(true) and
                it::age.lt() // < user.age
    }.queryForObjectOrNull()
}

fun getUser(user: User): List<User> { // Query the list of entities that meet the condition
    return select(user).where {
        it::id.eq and
                it::userName.eq and
                it::active.eq and
                it::age.eq
    }.queryForList()
}

//In fact, the second query can be abbreviated as the following,
// koto will automatically generate query conditions based on KPojo, 
// and when the condition value is null,
// it will not be added to the where condition:
fun getUserUseWhere(user: User): List<User> {
    return select(user).where().queryForList()
}
```

Use Koto to quickly create a piece of data from the passed in object:

```kotlin
fun createUserInfo(user: UserInfo): KotoExecuteResult {
    return create(user).execute()
// With create(user).on(*Fields), 
// you can specify to update the record instead of creating it when certain fields are the same
}
```

Delete some data with Koto:

```kotlin
fun deleteUserInfo(user: UserInfo): KotoExecuteResult {
    return remove(user).execute()
}
```

### 📚Koto Api Documentation

[https://api.kotoframework.com](https://api.kotoframework.com)

### 📚Koto Code Generator

[https://kotoframework.com/#/code-generator](https://kotoframework.com/#/code-generator)

### 📚Koto Ide Plugin

[https://kotoframework.com/#/ide-plugin](https://kotoframework.com/#/ide-plugin)

### 📚Koto Official Website

[https://kotoframework.com](https://kotoframework.com)

### 📚Koto Official Blog

[https://blog.kotoframework.com](https://blog.kotoframework.com)

### 📚Koto Official Document

[https://koto.fun](https://koto.fun)
