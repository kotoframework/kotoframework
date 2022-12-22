Koto SQL Framework for Kotlin
=============================

[![build](https://github.com/kotoframework/kotoframework/actions/workflows/build.yml/badge.svg)](https://github.com/kotoframework/kotoframework/actions/workflows/build.yml)
[![Maven central](https://img.shields.io/maven-central/v/com.kotoframework/koto-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.kotoframework%22)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/https/s01.oss.sonatype.org/com.kotoframework/koto-core.svg)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/kotoframework/koto-core/)
[![License](https://img.shields.io/:license-apache-brightgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

<img src="https://cdn.leinbo.com/assets/images/koto-logo.png" alt="koto" style="zoom: 33%;" />

### [English🇬🇧](../README.md) | 中文🇨🇳

### ❓什么是koto？

> 一款易上手、灵活，为kotlin设计的轻量级数据持久层ORM框架。
>

koto设计了简洁的操作实体和api，大多数情况下仅需一行，即可对数据库表完成复杂的逻辑功能：

```kotlin
val list = select<User>().queryForList() // list: List<User>
```

实际执行的SQL语句：

```sql
select `user_name` as `userName`, `nickname`, `id`, `active`, DATE_FORMAT(`create_time`, '%Y-%m-%d %H:%i:%s') as `createTime`, DATE_FORMAT(`update_time`, '%Y-%m-%d %H:%i:%s') as `updateTime` from user
```

Koto优势：

1. 轻量级，只有200KB大小，几乎不需要任何额外依赖
2. 配置简单，几乎不需要任何配置
3. 支持对象关系映射，可以轻松用于在关系型数据库和业务实体对象之间映射。
4. 写法简便且灵活，使用链式调用、默认参数、扩展函数等写法，与Kotlin语法完美结合。
5. 方便调试，具有强大和完善的日志功能。
6. 支持多数据源、动态数据源，支持多数据源的事务，支持多数据源的分布式事务。
7. 提供配套的代码生成器和ide插件

### ❓如何使用？

#### 1. 引入依赖

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

### ⌨️ 使用Koto写出第一个查询函数！

🎉恭喜你完成了Koto的配置，现在可以开始轻松简单地进行数据操作了！

下面我们将使用Koto写出第一个函数：

> Kotlin会通过类型推断进行自动映射，这会使我们的查询十分简洁！

```kotlin
fun getUserInfoById(id): User {
    return select(User(1)).where().queryForObject()
    
    // 另一种写法
    //select(User(1)).by("id").queryForObject()
}
// Koto实际上还支持更多符合直觉的写法，详见具体Api文档
// select(User(1)).by("id").queryForObject()
// select(User(1)).by(User::id).queryForObject()
// select(User(1)).where { it::id.eq }.queryForObject()
// select<User>().where { it::id.eq(1) }.queryForObject()
// select(User(1)).by("id").queryForObject()
// from<User> {  it.select(it).by(it::id to 1) }.queryForObject()
```

你还可以使用Where建立更复杂的查询条件，如：

```kotlin
fun getUserBySomeCondition(user: User): UserInfo? { //查询单个实体
    return select(user).where{
        it::id.eq and
        it::userName.notNull and
        it::active.eq(true) and
        it::age.lt() // < user.age
   }.queryForObjectOrNull()
}

fun getUser(user: User): List<User>{ // 查询满足条件的实体列表
  return select(user).where{
    it::id.eq and
    it::userName.eq and
    it::active.eq and
    it::age.eq
  }.queryForList()
}

//实际上第二个查询可以简写为下面的写法，koto会自动根据KPojo生成查询条件，条件值为null时则不会加入where条件中：
fun getUserUseWhere(user: User): List<User> {
    return select(user).where().queryForList()
}
```

使用Koto快速通过传入的对象创建一条数据：

```kotlin
fun createUserInfo(user: UserInfo): KotoExecuteResult {
    return create(user).execute()
    // 通过create(user).on(*Fields)，你可以指定当某些字段相同时更新记录而不是创建记录
}
```

使用Koto删除数据：

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
