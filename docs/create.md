# ➕插入行

我们在本文的开头定义两个实体类，它们分别对应两张数据表：

```kotlin
//以下是常见的普通Pojo类，只需继承「KPojo」interface，即可拥有ORM和toMap()/toMutableMap()的能力
data class Movie(
  var id: Int? = null,
  var movieName: String? = null,
  var description: String? = null,
  @DateFormat(pattern="YYYY-MM-DD") var publishDate: String? = null,
  var movieType: String? = null,
  var directorName: String? = null
) : KPojo

data class Director(
  var id: Int? = null,
  var name: String? = null
) : KPojo

val movie = Movie(
  id = 1,
  movieName = "Titanic", 
  description = "The film is set in 1912 when the Titanic hit an iceberg and sank on its maiden voyage. It tells the story of two people from different classes, jack and Ruth, who abandon their worldly prejudices and fall in love. Jack finally gives up his life to Ruth's touching story.", 
  publishDate = "1997-12-19", 
  movieType =  "Feature film",
  directorName = "James Cameron"
)
```

在koto的介绍中，您已经见过一些简单的查询语句，首先我们学习最简单的创建方法：

## 简单插入行

```kotlin
create(movie).execute()
//insert into movie (`id`, `movie_name`, `description`, `publish_date`, `movie_type`, `director_name`, `update_time`, `create_time`) values (:id,:movieName,:description,:publish_date,:movie_type,:director_name,:update_time,:create_time`)
```



## ` .onId()`存在Id相同行则更新行

```kotlin
create(movie).onId(1).execute()
```



## `.update(...Fields)`更新部分列

可以通过.update(...Fiels)只更新部分列，若不使用`.update()`默认更新整行数据：

```kotlin
create(movie).onId(1).update("publishDate").execute()
```



## `.except(...Field)`排除更新部分列

可以通过.except(...Field)排除更新部分列：

```kotlin
create(movie).onId(1).expect("movieName").execute()
```



## `.onDuplicateUpdate(...Field)`根据unique索引规则更新行

若待插入的数据违背当前表的索引规则，则会根据索引更新记录，否则会插入一行数据。

与`.update()`用法类似，onDuplicateUpdate接受多个列名作为参数，当传入的参数为空时，更新整行，否则只更新传入的部分列。

该函数可以与`.except()`共同使用

```kotlin
create(movie).onDuplicateUpdate().execute() 

create(movie).onDuplicateUpdate().expect(movie::movieName).execute() 
```



## `.on(...Field)`根据某些字段更新行

on()可以实现当已存在某行的数据的指定几列与待插入数据值相等时，更新该条记录，否则插入数据

该函数可以与`.update()|.except()`共同使用

```kotlin
create(movie)
    .on("movieType", movie::directorName)
    .execute()
```



## `List<KotoOperationSet>.batchExecute()`批量插入行

```kotlin
fun batchCreate(movies: List<Movie>) {
    movies.map{
        create(it).build()
    }.batchExecute()
}
```



## 不使用默认数据源，动态指定数据源

koto 支持您提供动态源，具体使用您使用的包装如果由扩展的数据功能，例如使用 koto-wrapper：

```kotlin
val namedJdbc = NamedParameterJdbcTemplate(dataSource)
create(movie).execute(namedJdbc)
```
