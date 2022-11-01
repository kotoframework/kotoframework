# 🚀 Quick Start

# 🌈koto-core

koto is already published to the maven central repository, so if you use maven, just add a dependency to the `pom.xml` file:

```xml
<dependency>
    <groupId>com.kotoframework</groupId>
    <artifactId>koto-core</artifactId>
    <version>1.0.1</version>
</dependency>
```

##### gradle(Groovy):

```groovy
compile "com.kotoframework:koto-core:${koto.version}"
```

##### gradle(Kts):

```kotlin
complie("com.kotoframework:koto-core:${koto.version}")
```



# 🧩koto-jdbc-wrapper

The size of koto-core is 200KB, which only contains all the functions of DSL and ORM. In addition, a wrapper plug-in with a size of about 20-30KB needs to be introduced to interact with the database before it can be used normally (koto provides relevant interfaces, you can [Custom wrapper](user_wrapper.md) to connect your existing project without introducing other dependencies).

## 📌 Enhancements for other persistence frameworks

If your project is using other data persistence layer frameworks, such as spring-data, Mybatis, jdbi, etc., you can directly add the officially provided dependencies and introduce the corresponding wrapper as the Jdbc engine of koto. For example, if you use it with spring-jdbc, Just additionally introduce:

##### maven

```xml
<dependency>
    <groupId>com.kotoframework</groupId>
    <artifactId>koto-spring-wrapper</artifactId>
    <version>1.0.1</version>
</dependency>
```

##### gradle(Groovy):

```groovy
compile "com.kotoframework:koto-spring-wrapper:${koto.version}"
```

##### gradle(Kts):

```kotlin
complie("com.kotoframework:koto-spring-wrapper:${koto.version}")
```



## 📌 only use koto

If you only use koto for data query, you can use koto to use koto-basic-wrapper, which can use all orm functions of koto without any other dependencies.

##### maven

```xml
<dependency>
    <groupId>com.kotoframework</groupId>
    <artifactId>koto-basic-wrapper</artifactId>
    <version>1.0.1</version>
</dependency>
```

##### gradle(Groovy):

```groovy
compile "com.kotoframework:koto-basic-wrapper:1.0.1"
```

##### gradle(Kts):

```kotlin
complie("com.kotoframework:koto-basic-wrapper:1.0.1")
```
