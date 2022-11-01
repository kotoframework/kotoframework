# 🔗Liánjiē shùjùkù ## 📌guānfāng Wrapper ruò nín shǐyòng guānfāng tígōng de wrapper chājiàn, zài nín yǐnrù <code>koto-jdbc-wrapper</code>chājiàn hòu, kěyǐ tōngguò wrapper tígōng jǐ kotoApp de <code>setDataSource</code>fāngfǎ huòzhě <code>setDynamicDataSource</code>fāngfǎ dìngyì shùjù yuán. Zài zhè zhīqián, nín xūyào yǐnrù apache-commons-dbcp2 shùjù liánjiē chí yīlài: Maven: ```Xml <dependency> <groupId>org.Apache.Commons</groupId> <artifactId>commons-dbcp2</artifactId> <version>2.9.0</Version> </dependency> ``` gradle(Groovy): ```Groovy implementation'org.Apache.Commons:Commons-dbcp2:2.9.0' ``` Gradle(Kts): ```Kotlin implementation("org.Apache.Commons:Commons-dbcp2:2.9.0") ``` ### BasicDataSource BasicDataSource lèi shíxiànle DataSource jiēkǒu, kěyǐ yòng yú DBCP liánjiē chí de jiǎndān shǐyòng. Chuàngjiàn liánjiē chí shí xūyào de pèizhì rúxià biǎo. | Fēnlèi | shǔxìng | miáoshù | | ------------ | ----------- | -------------------- | | bìxū xiàng | driverClass | shùjùkù qūdòng míngchēng | | bìxū xiàng | url | shùjùkù dìzhǐ | | bìxū xiàng | username | yònghù míng | | bìxū xiàng | password | mìmǎ | | jīběn xiàng (kuòzhǎn) | maxActive | liánjiē chí zuìdà liánjiē shù | | jīběn xiàng (kuòzhǎn) | maxIdle | liánjiē chí zuìdà kòngxián shù | | jīběn xiàng (kuòzhǎn) | minIdle | liánjiē chí zuìxiǎo kòngxián shù | | jīběn xiàng (kuòzhǎn) | initialSize | chūshǐhuà liánjiē chí shí liánjiē shù | gèng duō guānyú [BasicDataSource](https://Commons.Apache.Org/proper/commons-dbcp/apidocs/index.Html) de xìnxī ránhòu, nín kěyǐ cānzhào <a href="/#/koto-config?Id=shùjù yuán pèizhì">shùjù yuán pèizhì jiàochéng </a>shèzhì quánjú shùjù yuán. ## 📌Zìjǐ chuàngjiàn wrapper Koto guānfāng mòrèn tígōng de wrapper shǐyòng BasicDataSource shùjù liánjiē chí, dāngrán, nín yě kěyǐ zìjǐ tōngguò zìjǐ shíxiàn de wrapper lèi lái yǐnrù qítā de liánjiē chí. [Rúhé kuàisù chuàngjiàn wrapper](user_wrapper.Md)
展开
1,381 / 5,000
翻译结果
# 🔗Connect to database

## 📌Official Wrapper

If you use the official wrapper plugin, after you import the <code>koto-jdbc-wrapper</code> plugin, you can provide the <code>setDataSource</code> method or <code>setDynamicDataSource</code> to kotoApp through the wrapper The code> method defines the data source.

Before this, you need to introduce the apache-commons-dbcp2 data connection pool dependency:

maven:

````xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-dbcp2</artifactId>
    <version>2.9.0</version>
</dependency>
````

gradle(Groovy):

````groovy
implementation 'org.apache.commons:commons-dbcp2:2.9.0'
````

gradle(Kts):

```kotlin
implementation("org.apache.commons:commons-dbcp2:2.9.0")
````



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
