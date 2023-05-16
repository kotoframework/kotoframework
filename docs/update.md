# 🔖 update row

We define the entity class TbUser at the beginning of this article, which will be used as an example below:

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

val movie = Movie(
  id = 1,
  movieName = "Titanic",
  description = "The film is set in 1912 when the Titanic hit an iceberg and sank on its maiden voyage. It tells the story of two people from different classes, jack and Ruth, who abandon their worldly prejudices and fall in love. Jack finally gives up his life to Ruth's touching story.",
  publishDate = "1997-12-19",
  movieType = "Feature film",
  directorName = "James Cameron"
)
```

## `.byId(Int/Long)` to update a single row by id:

When updating with byId, the `updateTime` column is automatically updated and the `id` column is not updated

```kotlin
update(movie).byId().execute()
//or update(movie).byId(1).execute()
```



## `.byIds(List<Int/Number>)` update multiple rows according to multiple ids

When updating with byIds, the `updateTime` column is automatically updated and the `id` column is not updated

```kotlin
update(movie)
    .byIds(listOf(1, 2, 3, 4, 5))
    .execute()
```



## `.by(...Field)` to update rows based on multiple equality conditions:

`.by` accepts multiple columns as update conditions, these conditions need to be satisfied at the same time (that is, using and connection), you can only enter the column name (so that koto will use the value of KPojo as the value of the update condition), Or pass in a Pair object to specify the value of the update condition.

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



## `.where(...conditions)` complete query conditions

You can use where query conditions by calling .where, [specific usage of where query conditions](where.md)｜<a href="/#/where?id=where-api">All Apis that can be used after where</a>

Example of updating row based on where condition

```kotlin
update(movie)
    .where { //it -> Movie
      "directorName".eq() and
      it::publishDate.after("2022-02-22")
    }
    .execute()
```



## only update some fields

```kotlin
update(
    movie, "movieName", "movieType")
    .where { //it -> Movie
      "directorName".eq() and
      it::publishDate.after("2022-02-22")
    }
    .execute()
```



## `List<KotoOperationSet>.batchExecute()` batch update rows

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



## Do not use the default data source, dynamically specify the data source

koto supports you to provide dynamic sources, specifically using the wrapper you use if extended by the data functionality, e.g. using koto-spring-wrapper:

```kotlin
val db = NamedParameterJdbcTemplate(dataSource).wrapper()
db.update(movie).byId(1).execute()
```
