# 🚀快速开始

# 🌈koto-core

koto 已经发布到 maven 中央仓库，因此，如果你使用 maven 的话，只需要在 `pom.xml` 文件里面添加一个依赖：

```xml
<dependency>
    <groupId>com.kotoframework</groupId>
    <artifactId>koto-core</artifactId>
    <version>${koto.version}</version>
</dependency>
```

##### gradle(Groovy)：

```groovy
compile "com.kotoframework:koto-core:${koto.version}"
```

##### gradle(Kts):

```kotlin
complie("com.kotoframework:koto-core:${koto.version}")
```



# 🧩koto-jdbc-wrapper

koto-core大小为200KB，其中仅包含DSL和ORM的全部功能，此外需要引入一个大小约20～30KB的wrapper插件用于与数据库进行数据交互，才能正常使用（koto提供了相关的接口，您可以[自定义wrapper](zh-cn/user_wrapper.md)来连接您已有项目，而无需引入其他依赖）。

##  📌为其他持久层框架的增强

若您的项目正在使用其他数据持久层框架，如spring-data、Mybatis、jdbi等，可以直接加入官方提供的依赖引入对应的wrapper作为koto的Jdbc引擎，如：若您与spring-jdbc配合使用，只需另外引入：

##### maven

```xml
<dependency>
    <groupId>com.kotoframework</groupId>
    <artifactId>koto-spring-wrapper</artifactId>
    <version>${koto.version}</version>
</dependency>
```

##### gradle(Groovy)：

```groovy
compile "com.kotoframework:koto-spring-wrapper:${koto.version}"
```

##### gradle(Kts):

```kotlin
complie("com.kotoframework:koto-spring-wrapper:${koto.version}")
```



## 📌仅使用koto

若您仅使用koto进行数据查询，可使用koto使用koto-basic-wrapper，该包无需任何其他依赖即可使用koto的全部orm功能。

##### maven

```xml
<dependency>
    <groupId>com.kotoframework</groupId>
    <artifactId>koto-basic-wrapper</artifactId>
    <version>${koto.version}</version>
</dependency>
```

##### gradle(Groovy)：

```groovy
compile "com.kotoframework:koto-basic-wrapper:${koto.version}"
```

##### gradle(Kts):

```kotlin
complie("com.kotoframework:koto-basic-wrapper:${koto.version}")
```

