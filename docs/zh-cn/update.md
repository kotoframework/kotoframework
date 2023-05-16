# 🔖更新行

我们在本文的开头定义实体类TbUser，下文将以此举例：

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

val movie = Movie(
  id = 1,
  movieName = "Titanic", 
  description = "The film is set in 1912 when the Titanic hit an iceberg and sank on its maiden voyage. It tells the story of two people from different classes, jack and Ruth, who abandon their worldly prejudices and fall in love. Jack finally gives up his life to Ruth's touching story.", 
  publishDate = "1997-12-19", 
  movieType =  "Feature film",
  directorName = "James Cameron"
)
```

## `.byId(Int/Long)`根据id更新单行：

使用byId更新时，会自动`updateTime`列，并且不会更新`id`列

```kotlin
update(movie).byId().execute()
//或 update(movie).byId(1).execute()
```



## `.byIds(List<Int/Number>)`根据多个id更新多行

使用byIds更新时，会自动`updateTime`列，并且不会更新`id`列

```kotlin
update(movie)
    .byIds(listOf(1, 2, 3, 4, 5))
    .execute()
```



## `.by(...Field)`根据多个相等条件更新行：

`.by`可接受多个列作为更新的条件，这些条件是需要同时满足的（即使用and连接），您可以只输入列名（这样koto会将KPojo的值作为该更新条件的值），或传入Pair对象指定该更新条件的值。

```kotlin
update(movie)
		.by(
        movie::movieName to "Titanic",
        "movieType" to 'actions movie',
        movie::publishDate,
        "directorName"
    )
		.execute()
```



## `.where(...conditions)`完整查询条件

可以通过调用.where使用where查询条件，[where查询条件的具体用法](where.md)｜<a href="/#/zh-cn/where?id=where-api">所有where后可使用的Api</a>

> 根据where条件更新行的示例

```kotlin
update(movie)
    .where { //it -> Movie
      "directorName".eq() and
      	it::publishDate.after("2022-02-22")
    } 
    .execute()
```



## 只更新部分字段

```kotlin
update(
    movie, "movieName", "movieType")
    .where { //it -> Movie
      "directorName".eq() and
      	it::publishDate.after("2022-02-22")
    }
    .execute()
```



## `List<KotoOperationSet>.batchExecute()`批量更新行

```kotlin
fun batchUpdate(movies: List<Movie>) {
    movies.map{
        update(it).where { //it -> Movie
        "directorName".eq() and
          it::publishDate.after("2022-02-22")
        }.build()
    }.batchExecute()
}
```



## 不使用默认数据源，动态指定数据源

koto 支持您提供动态源，具体使用您使用的包装如果由扩展的数据功能，例如使用 koto-wrapper：

```kotlin
val db = NamedParameterJdbcTemplate(dataSource).wrapper()
db.update(movie).byId(1).execute()
```

