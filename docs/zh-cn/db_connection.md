# 🔗连接数据库

## 📌官方Wrapper

若您使用官方提供的wrapper插件，在您引入<code>koto-jdbc-wrapper</code>插件后，可以通过wrapper提供给kotoApp的<code>setDataSource</code>方法或者<code>setDynamicDataSource</code>方法定义数据源。

在这之前，您需要引入apache-commons-dbcp2数据连接池依赖：

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

BasicDataSource类实现了DataSource接口，可以用于DBCP连接池的简单使用。
创建连接池时需要的配置如下表。

| 分类         | 属性        | 描述                 |
| ------------ | ----------- | -------------------- |
| 必需项       | driverClass | 数据库驱动名称       |
| 必需项       | url         | 数据库地址           |
| 必需项       | username    | 用户名               |
| 必需项       | password    | 密码                 |
| 基本项(扩展) | maxActive   | 连接池最大连接数     |
| 基本项(扩展) | maxIdle     | 连接池最大空闲数     |
| 基本项(扩展) | minIdle     | 连接池最小空闲数     |
| 基本项(扩展) | initialSize | 初始化连接池时连接数 |

更多关于[BasicDataSource](https://commons.apache.org/proper/commons-dbcp/apidocs/index.html)的信息

然后，您可以参照<a href="/#/zh-cn/koto-config?id=数据源配置">数据源配置教程</a>设置全局数据源。



## 📌自己创建wrapper

Koto官方默认提供的wrapper使用BasicDataSource数据连接池，当然，您也可以自己通过自己实现的wrapper类来引入其他的连接池。

[如何快速创建wrapper](zh-cn/user_wrapper.md)
