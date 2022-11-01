# 🔗Connect to database

## 📌Official Wrapper

If you use the official wrapper plugin, after you import the <code>koto-jdbc-wrapper</code> plugin, you can provide the <code>setDataSource</code> method or <code>setDynamicDataSource</code> to kotoApp through the wrapper The code> method defines the data source.

Before this, you need to introduce the apache-commons-dbcp2 data connection pool dependency:

maven:

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-dbcp2</artifactId>
    <version>2.9.0</version>
</dependency>
```

gradle(Groovy):

```groovy
implementation 'org.apache.commons:commons-dbcp2:2.9.0'
```

gradle(Kts):

```kotlin
implementation("org.apache.commons:commons-dbcp2:2.9.0")
```



### BasicDataSource

The BasicDataSource class implements the DataSource interface and can be used for simple use of DBCP connection pools.
The configuration required when creating a connection pool is as follows.

| Category | Properties | Description |
| ------------ | ----------- | -------------------- |
| Required | driverClass | Database Driver Name |
| required | url | database address |
| Required | username | Username |
| required | password | password |
| Basic item (extended) | maxActive | Maximum number of connections in connection pool |
| Basic item (extended) | maxIdle | Maximum idle number of connection pool |
| Basic item (extended) | minIdle | Minimum idle number of connection pool |
| Basic item (extended) | initialSize | Number of connections when initializing connection pool |

More about [BasicDataSource](https://commons.apache.org/proper/commons-dbcp/apidocs/index.html)

Then, you can follow the <a href="/#/koto-config?id=Data Source Configuration">Data Source Configuration Tutorial</a> to set up the global data source.



## 📌 Create your own wrapper

The official default wrapper provided by Koto uses the BasicDataSource data connection pool. Of course, you can also introduce other connection pools through your own wrapper class.

[How to quickly create a wrapper](user_wrapper.md)
