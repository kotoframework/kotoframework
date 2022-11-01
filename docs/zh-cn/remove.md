# 🧹删除行



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



## `.byId(Int/Long)`

```kotlin
remove("movie").byId(1).execute()
```



## `.byIds(List<Int/Long>)`通过给定的id列表删除多条记录：

```kotlin
remove("movie").byIds(listOf(1, 2, 3, 4)).execute()
```



## `.by(...Field)`根据给定的多个条件删除记录

```kotlin
remove("movie")
    .by(
        "movieName" to "Titanic",
        "movieType" to "Feature film",
        "directorName" to "James Cameron"
    )
    .execute()
```



## `remove(KPojo)`根据给定的KPojo对象删除记录：

```kotlin
remove(movie).execute()

remove(movie).byId().execute() //结合byId()通过id删除

remove(movie).by(movie::movieName).execute() //结合by()通过部分条件删除

remove(movie).by(movie::movieName, movie::directorName to "koto").execute() //结合by()通过部分条件删除，并覆盖KPojo的值

```



## `.where(...conditions)`完整查询条件

可以通过调用.where使用where查询条件，[where查询条件的具体用法](where.md)｜<a href="/#/zh-cn/where?id=where-api">所有where后可使用的Api</a>

> 根据where条件删除行的示例

```kotlin
remove(movie).where { //it -> UserInfoKPojo
  it::movieName.eq() and 
  	"movieType".eq() and 
  	"directorName".eq() and
  	it::publishDate.before(DateTime("2022-05-27 12:12:12"))
}.execute()

//或

remove(movie).where(
	"movieName".eq(),
  "movieType".eq(),
  "publishDate".eq()
).execute()
```

## `.soft()`逻辑删除

> 该操作会将逻辑删除字段(tinyint)更新为1，并修改updateTime

```kotlin
remove("movie").soft().byId(1).execute()
```



##  `List<KotoOperationSet>.batchExecute()`批量删除行

```kotlin
fun batchRemove(movies: List<Movie>) {
    movies.map{
        remove(it).build()
    }.batchExecute()
}
```



## 不使用默认数据源，动态指定数据源

koto 支持您提供动态源，具体使用您使用的包装如果由扩展的数据功能，例如使用 koto-spring-wrapper：

```kotlin
val db = NamedParameterJdbcTemplate(dataSource).wrapper()
db.remove("table_name").byId(1).execute()
```

