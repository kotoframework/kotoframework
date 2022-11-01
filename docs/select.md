# 🔍Data query

We define two entity classes at the beginning of this article, which correspond to two data tables:

```kotlin
//The following are common common Pojo classes, you only need to inherit the "KPojo" interface, you can have ORM and toMap()/toMutableMap() capabilities
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
````



In the introduction of koto, you have seen some simple query statements. First, let's learn one of the simplest query methods:

## `.by(...Field)` Simple query condition

```kotlin
// Query by passing in the Pair key-value pair
val movie = select<Movie>().by("movieName" to "xxx", ...).first().queryForList()

//The column name can use the attributes of the data class
val movie = select<Movie>().by(Movie::movieName to "xxx").first().queryForList())

//You can pass in the movie object, so that by can only pass in the column name without providing a value
fun search(movie: Movie){
val result = select(searchMovie).by("movieName", Movie::director to "xxx", ...).queryForObject()
}
//Multiple conditions in by need to be satisfied at the same time, that is, they are connected using and
````



## Four query functions:

There are four query functions in koto, query, queryForList, queryForObject and queryForObjectOrNull

### 1.`.query()`

The <code>query</code> function is the most basic function, it will return a List<Map<String, Any>> containing all the query results, such as:

```kotlin
val movies = select<Movie>().where{ it::movieName.like("%xxx") }.query()

// movies: List<Map<String, Any>>
````

### 2.`.queryForList()`

<code>queryForList</code> provides type parsing capabilities, and can specify the type of covariant objects in the returned List (if not specified, the incoming KPojo type is returned by default):

```kotlin
val movies = select<Movie>().where{ it::id.notEq(1) }.queryForList()

//movies: List<Movie>

val movieNames = select(Movie(id = 1), "movieName").where{ it.notEq }.queryForList<String>() //Query a single column

//movieNames: List<String>
````

### 3.`.queryForObject()`

The <code>queryForObject</code> function can query a single result and provide type parsing capabilities. When there is no result, an exception will be thrown.

```kotlin
val movie = select(Movie(id = 1)).by(Movie::id).queryForObject()

//movie: Movie
````

### 4. `.queryForObjectOrNull()`

The function is basically the same as queryForObject, but returns null instead of throwing an exception if the result is not queried

```kotlin
val movie = select(Movie(id = 1)).where().queryForObjectOrNull()

//movie: Movie?
````

## `.where(...conditions)` complete query conditions

You can use where query conditions by calling .where, [specific usage of where query conditions](where.md)｜<a href="/#/where?id=where-api">All Apis that can be used after where</ a>

> Query all field instances of different records with paging and sorting according to where query conditions

```kotlin
val movies = select(movie) // automatically pass the value
    .where { "movieName".eq and it::publishDate.gt }
    .distinct()
    .page(pageIndex, pageSize)
    .orderBy(movie::updateTime.desc())
    .groupBy(movie::directorName)
    .query() // return result set

//movies: List<Map<String, Any>>
````



## Query part of the column / alias

By passing a value to the select function, you can specify the column to query, and by passing in Pair, you can specify an alias for the column.

"*" means query all columns.

The incoming column can contain "subquery SQL statements" and "functions". Several commonly used functions are encapsulated in koto, and more functions can be implemented by passing in strings. <strong>Please note that koto does not automatically alias subqueries and function columns. </strong>

```kotlin
val movies = select(
  movie,
  movie::movieName to "name", // The database field name is movie_name, and the alias is movieName
  "publishDate", // database field name publish_date, alias publishDate
  movie::actors
)
.where()
.query()
````

koto provides built-in functions:

 ```kotlin
 fun LEN(field: Field)
 fun SUM(field: Field)
 fun COUNT(field: Field)
 fun MAX(field: Field)
 fun MIN(field: Field)
 ````



## How to add subquery, function expression

Example:

```kotlin
val select(
    movie,
    "*",
    "(SELECT id from director where director.name = director_name limit 1) as directorId",
    MAX(movie::id) to "idMax" //or directly pass in the string "max(id) as idMax"
)
    .where { "xxx".gt(100) }
    .orderBy(movie::updateTime.DESC, movie::id.ASC)
    .query()
````



## future features: from function

The <code>from</code> function simplifies the problem of repeatedly writing object names for complex queries. The usage is as follows:

```kotlin
//pass in type
from<Movie>{
  it.select(it::movieName).where(it::directorName.eq("xxx") and it::publishDate.after("2022-12-31")).orderBy(it::id.desc( ))
}.queryForList<String>()

//pass in the object
from(Movie(publishDate = "2022-12-31")){
  it.select(it::movieName).where(it::directorName.isIn(listOf("xxx", ...)) and it::publishDate.notBefore).orderBy(it::id.desc())
}.queryForList<String>()
````



## dynamic data source

Koto itself supports dynamic data sources, and the specific form of use is provided by the extension functions that come with the wrapper you use. For example, if you use koto-spring-wrapper:

```kotlin
val namedJdbc = NamedParameterJdbcTemplate(dataSource)
val movies = namedJdbc.select<Movie>().by("movieName" to "xxx", ...).first().queryForList().query()
````
