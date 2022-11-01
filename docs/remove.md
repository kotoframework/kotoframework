# 🧹 delete line



We define the entity class TbUser at the beginning of this article, which will be used as an example below:

```kotlin
//The following are common Pojo classes, you only need to inherit the "KPojo" interface, you can have ORM and toMap()/toMutableMap() capabilities
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
````



## `.byId(Int/Long)`

```kotlin
remove("movie").byId(1).execute()
````



## `.byIds(List<Int/Long>)` deletes multiple records by the given list of ids:

```kotlin
remove("movie").byIds(listOf(1, 2, 3, 4)).execute()
````



## `.by(...Field)` delete records based on given multiple conditions

```kotlin
remove("movie")
    .by(
        "movieName" to "Titanic",
        "movieType" to "Feature film",
        "directorName" to "James Cameron"
    )
    .execute()
````



## `remove(KPojo)` removes a record based on the given KPojo object:

```kotlin
remove(movie).execute()

remove(movie).byId().execute() //delete by id in combination with byId()

remove(movie).by(movie::movieName).execute() //Delete by some conditions in combination with by()

remove(movie).by(movie::movieName, movie::directorName to "koto").execute() // Combine by() to delete through some conditions and overwrite the value of KPojo

````



## `.where(...conditions)` complete query conditions

You can use where query conditions by calling .where, [specific usage of where query conditions](where.md)｜<a href="/#/where?id=where-api">All Apis that can be used after where</ a>

Example of deleting rows based on where condition

```kotlin
remove(movie).where { //it -> UserInfoKPojo
  it::movieName.eq() and
  "movieType".eq() and
  "directorName".eq() and
  it::publishDate.before(DateTime("2022-05-27 12:12:12"))
}.execute()

//or

remove(movie).where(
"movieName".eq(),
  "movieType".eq(),
  "publishDate".eq()
).execute()
````

## `.soft()` tombstone

> This operation will update the tombstone field (tinyint) to 1 and modify the updateTime

```kotlin
remove("movie").soft().byId(1).execute()
````



## `List<KotoOperationSet>.batchExecute()` batch delete rows

```kotlin
fun batchRemove(movies: List<Movie>) {
    movies.map{
        remove(it).build()
    }.batchExecute()
}
````



## Do not use the default data source, dynamically specify the data source

koto supports you to provide dynamic sources, specifically using the wrapper you use if extended by the data functionality, e.g. using koto-spring-wrapper:

```kotlin
val db = NamedParameterJdbcTemplate(dataSource).wrapper()
db.remove("table_name").byId(1).execute()
````
