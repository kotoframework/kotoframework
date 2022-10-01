# 🔍数据查询

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
```



在koto的介绍中，您已经见过一些简单的查询语句，首先我们学习一种最简单的查询方法：

## `.by(...Field)`简单查询条件

```kotlin
// 通过传入Pair键值对查询
val movie = select<Movie>().by("movieName" to "xxx", ...).first().queryForList()

//列名可以使用data class的属性
val movie = select<Movie>().by(Movie::movieName to "xxx").first().queryForList())

//可以传入movie对象，这样by中可以只传入列名而不需要提供值
fun search(movie: Movie){
	val result = select(searchMovie).by("movieName",  Movie::director to "xxx",  ...).queryForObject()
}
//by中的多个条件是需要同时满足的，即是使用and连接的
```



## 四种查询函数：

在koto中有四种查询函数，分别是query、queryForList、queryForObject和queryForObjectOrNull

### 1.`.query()`

<code>query</code>函数是最基本的函数，它会返回一个包含全部查询结果的List<Map<String, Any>>，如：

```kotlin
val movies = select<Movie>().where{ it::movieName.like("%xxx") }.query()

// movies: List<Map<String, Any>>
```

### 2.`.queryForList()`

<code>queryForList</code>提供类型解析能力，可以指定返回的List中协变对象的类型(若不指定，默认返回传入的KPojo类型)：

```kotlin
val movies = select<Movie>().where{ it::id.notEq(1) }.queryForList()

//movies: List<Movie>

val movieNames = select(Movie(id = 1), "movieName").where{ it.notEq }.queryForList<String>() //查询单列

//movieNames: List<String>
```

### 3.`.queryForObject()`

<code>queryForObject</code>函数可以查询单条结果同时提供类型解析能力，当无结果是会抛出异常。

```kotlin
val movie = select(Movie(id = 1)).by(Movie::id).queryForObject()

//movie: Movie
```

### 4.`.queryForObjectOrNull()`

功能和queryForObject基本相同，但是未查询到结果时返回null而不抛出异常

```kotlin
val movie = select(Movie(id = 1)).where().queryForObjectOrNull()

//movie: Movie?
```

## `.where(...conditions)`完整查询条件

可以通过调用.where使用where查询条件，[where查询条件的具体用法](where.md)｜<a href="/#/zh-cn/where?id=where-api">所有where后可使用的Api</a>

> 根据where查询条件查询带分页和带排序的不同记录的全部字段实例

```kotlin
val movies = select(movie) // 自动传值
    .where { "movieName".eq and it::publishDate.gt }
    .distinct()
    .page(pageIndex, pageSize)
    .orderBy(movie::updateTime.desc())
    .groupBy(movie::directorName)
    .query() // 返回结果集

//movies: List<Map<String, Any>>
```



## 查询部分列/起别名

通过给select函数传值，可以指定查询的列，通过传入Pair，可以为列指定别名。

"*"代表查询全部列。

传入的列中可以包含「子查询SQL语句」和「函数」，koto内封装了几个常用的函数，更多的函数可以传入字符串实现。<strong>请注意，koto不会为子查询和函数列自动起别名。</strong>

```kotlin
val movies = select(
  movie,
  movie::movieName to "name", // 数据库字段名为 movie_name，别名为 movieName
  "publishDate", // 数据库字段名为 publish_date，别名为 publishDate
  movie::actors
)
	.where()
	.query()
```

koto提供内置的函数：

 ```kotlin
 fun  LEN(field: Field)
 fun  SUM(field: Field)
 fun  COUNT(field: Field)
 fun  MAX(field: Field)
 fun  MIN(field: Field)
 ```

  

## 如何添加加子查询、函数表达式

示例：

```kotlin
val select(
    movie,
    "*",
    "(SELECT id from director where director.name = director_name limit 1) as directorId",
    MAX(movie::id) to "idMax" //或直接传入字符串"max(id) as idMax"
)
    .where { "xxx".gt(100) }
    .orderBy(movie::updateTime.DESC, movie::id.ASC)
    .query()
```



## future特性：from函数

<code>from</code>函数简化了复杂查询时需要重复写对象名的问题，使用方法如下：

```kotlin
//传入类型
from<Movie>{
  it.select(it::movieName).where(it::directorName.eq("xxx") and it::publishDate.after("2022-12-31")).orderBy(it::id.desc())
}.queryForList<String>()

//传入对象
from(Movie(publishDate = "2022-12-31")){
  it.select(it::movieName).where(it::directorName.isIn(listOf("xxx", ...)) and it::publishDate.notBefore).orderBy(it::id.desc())
}.queryForList<String>()
```



## 动态数据源

koto本身支持动态数据源，具体使用的形式由您使用的wrapper附带的扩展函数提供，比如，若您使用koto-spring-wrapper：

```kotlin
val namedJdbc = NamedParameterJdbcTemplate(dataSource)
val movies = namedJdbc.select<Movie>().by("movieName" to "xxx", ...).first().queryForList().query()
```

