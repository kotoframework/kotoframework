# ➕ Insert line

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

val movie = Movie(
  id = 1,
  movieName = "Titanic",
  description = "The film is set in 1912 when the Titanic hit an iceberg and sank on its maiden voyage. It tells the story of two people from different classes, jack and Ruth, who abandon their worldly prejudices and fall in love. Jack finally gives up his life to Ruth's touching story.",
  publishDate = "1997-12-19",
  movieType = "Feature film",
  directorName = "James Cameron"
)
```

In the introduction of koto, you have seen some simple query statements, first let's learn the easiest way to create:

## simple insert row

```kotlin
create(movie).execute()
//insert into movie (`id`, `movie_name`, `description`, `publish_date`, `movie_type`, `director_name`, `update_time`, `create_time`) values ​​(:id,:movieName,:description,: publish_date,:movie_type,:director_name,:update_time,:create_time`)
```



## `.onId()` If there is a row with the same Id, update the row

```kotlin
create(movie).onId(1).execute()
```



## `.update(...Fields)` update some columns

You can update only some of the columns through .update(...Fiels), if you don't use `.update()` to update the entire row of data by default:

```kotlin
create(movie).onId(1).update("publishDate").execute()
```



## `.except(...Field)` to exclude some columns from updating

Some columns can be excluded from updating with .except(...Field):

```kotlin
create(movie).onId(1).expect("movieName").execute()
```



## `.onDuplicateUpdate(...Field)` update rows according to unique index rules

If the data to be inserted violates the index rules of the current table, the record will be updated according to the index, otherwise a row of data will be inserted.

Similar to the usage of `.update()`, onDuplicateUpdate accepts multiple column names as parameters. When the passed parameter is empty, the entire row is updated, otherwise only part of the passed columns is updated.

This function can be used with `.except()`

```kotlin
create(movie).onDuplicateUpdate().execute()

create(movie).onDuplicateUpdate().expect(movie::movieName).execute()
```



## `.on(...Field)` update row based on some fields

on() can realize that when the specified columns of the existing data of a row are equal to the value of the data to be inserted, the record is updated, otherwise the data is inserted

This function can be used together with `.update()|.except()`

```kotlin
create(movie)
    .on("movieType", movie::directorName)
    .execute()
```



## `List<KotoOperationSet>.batchExecute()` batch insert rows

```kotlin
fun batchCreate(movies: List<Movie>) {
    movies.map{
        create(it).build()
    }.batchExecute()
}
```



## Do not use the default data source, dynamically specify the data source

koto supports you to provide dynamic sources, specifically using the wrapper you use if extended by the data functionality, e.g. using koto-spring-wrapper:

```kotlin
val db = NamedParameterJdbcTemplate(dataSource).wrapper()
db.create(movie).execute()
```
