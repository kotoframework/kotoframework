Koto SQL Framework for Kotlin

# 🎉[Kronos ORM (Koto v2.0)](https://github.com/Kronos-orm/Kronos-orm) has been open sourced and is constantly being improved. It is based on the compiler plug-in developed for Kotlin K2, and its performance and ease of use have been greatly improved. Welcome to follow
> 📖 [Offical Website and Document](https://www.kotlinorm.com)

=============================

[![build](https://github.com/kotoframework/kotoframework/actions/workflows/build.yml/badge.svg)](https://github.com/kotoframework/kotoframework/actions/workflows/build.yml)
[![Maven central](https://img.shields.io/maven-central/v/com.kotoframework/koto-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.kotoframework%22)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/https/s01.oss.sonatype.org/com.kotoframework/koto-core.svg)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/kotoframework/)
[![License](https://img.shields.io/:license-apache-brightgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Coverage Status](https://coveralls.io/repos/github/kotoframework/kotoframework/badge.svg?branch=main)](https://coveralls.io/github/kotoframework/kotoframework?branch=main)

<img src="https://cdn.leinbo.com/assets/images/koto-logo.png" alt="koto" style="zoom: 33%;" />

### English🇬🇧 | [中文🇨🇳](zh-cn/README.md)

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

1.0.4-SNAPSHOT is the latest version, which is compatible with all versions of Kotlin.

##### maven

```xml

<dependency>
    <groupId>com.kotoframework</groupId>
    <artifactId>koto-core</artifactId>
    <version>1.0.3</version>
</dependency>
```

##### gradle

```groovy
compile "com.kotoframework:koto-core:1.0.3"
```

```kotlin
complie("com.kotoframework:koto-core:1.0.3")
```
if use the latest snapshot version, use `1.0.4-SNAPSHOT`

##### maven

```xml
<repository>
   <id>sonatype-nexus-snapshots</id>
   <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
   <releases>
       <enabled>false</enabled>
   </releases>
   <snapshots>
       <enabled>true</enabled>
       <updatePolicy>daily</updatePolicy>
       <checksumPolicy>fail</checksumPolicy>
   </snapshots>
</repository>

<dependency>
    <groupId>com.kotoframework</groupId>
    <artifactId>koto-core</artifactId>
    <version>1.0.4-SNAPSHOT</version>
</dependency>
```

##### gradle

```kotlin
implementation("com.kotoframework:koto-core:1.0.4-SNAPSHOT")
```

##### kotlin

```kotlin
implementation("com.kotoframework:koto-core:1.0.4-SNAPSHOT")
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

You can also use Where to build more complex query conditions, such as:

```kotlin
fun getUserBySomeCondition(user: User): UserInfo? { // query for object or null
    return select(user).where{
        it::id.eq and
        it::userName.notNull and
        it::active.eq(true) and
        it::age.lt() // < user.age
   }.queryForObjectOrNull()
}

fun getUser(user: User): Pair<List<User>, Int>{ // query for list and count
    return select(user).where{
        it::id.eq and
        it::userName.eq and
        it::active.eq and
        it::age.eq
    }
      .orderBy(it::id.desc)
      .page(1, 10)
      .withTotal{
          it.queryForList()
      }
   //can be simplified to return select(user).where().orderBy... 
}
```

quickly create or delete a record by passing in an KPojo data class:

```kotlin
create(user).execute()

remove(user).byId().execute()
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
