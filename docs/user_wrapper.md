# 📒 How to create your own wrapper

Users can customize the wrapper and handler classes to quickly integrate with their existing business frameworks without introducing other plugins and required dependencies. For example, if you are using the mybatis/hebernate/sql2o framework, you can use it to execute native sql ability, implement the following interfaces and add extension functions to koto, you do not need to introduce more dependencies except the 200kb koto-core.

## What do I have to do

Implement the following interfaces or define extension functions:

- Implement the `KotoQueryWrapper()` abstract class and overload the following functions:

  - ```kotlin
    abstract fun queryForList(sql: String, paramMap: Map<String, Any?>): List<Map<String, Any>> //Query list
    ````

  - ```kotlin
    abstract fun <T> queryForObject(sql: String, paramMap: Map<String, Any?>, clazz: Class<T>): T? //Query a single object (T is the basic type)
    ````

  - ```kotlin
    abstract fun update(sql: String, paramMap: Map<String, Any?>): Int //Execute update/add/delete statements
    ````

  - ```kotlin
    abstract fun batchUpdate(sql: String, paramMaps: Array<Map<String, Any?>>): IntArray //Batch update
    ````

  - ```kotlin
    abstract val url: String get() //Get the url of the current connection
    ````

- Implement the `KotoQueryHandler()` virtual class and overload the following functions:

  - ```kotlin
    abstract fun forList(
        jdbc: KotoJdbcWrapper? = null, //please use as your own wrapper type here
        sql: String,
        paramMap: Map<String, Any?>,
        kClass: KClass<*>
    ): List<Any> //Query List (with type, may be KPojo object, you need to judge whether to inherit KPojo for toKPojo processing)
    ````

  - ```kotlin
    abstract fun forObject(
        jdbc: KotoJdbcWrapper? = null,//Please use as your own wrapper type here
        sql: String,
        paramMap: Map<String, Any?>,
        withoutErrorPrintln: Boolean = false,//True only when OrNull, if true, return null when the query result is null, otherwise throw an exception
        kClass: KClass<*>
    ): Any//Query object (with type, may be KPojo object, you need to judge whether to inherit KPojo for toKPojo processing)
    ````

  - ```kotlin
    abstract fun forObjectOrNull(
        jdbc: KotoJdbcWrapper? = null,//Please use as your own wrapper type here
        sql: String,
        paramMap: Map<String, Any?>,
        kClass: KClass<*>
    ): Any? //Query the object, call forObject to implement, the returned result can be empty
    ````

- Provides extension functions `setDataSource()` and `setDynamicDataSource` for `KotoApp`

- Finally, just replace the handlers in the following functions with other framework handler classes or DataSource objects you are using.

  - ```kotlin
    fun KotoOperationSet.execute(handler: OtherFrameworkHandler? = null): KotoExecuteResult
    ````

  - ```kotlin
    fun <T : KPojo, E : KPojo, ...> OtherFrameworkHandler.associate(...): Associate
    ````

  - ```kotlin
    fun CreateWhere<*>.execute(handler: OtherFrameworkHandler? = null): KotoExecuteResult
    ````

  - ```kotlin
    fun <T : KPojo> OtherFrameworkHandler.remove(KPojo: T): RemoveAction<T>
    ````

  - ```kotlin
    fun OtherFrameworkHandler.remove(tableName: String): RemoveAction<Unknown>
    ````

  - ```kotlin
    fun RemoveAction<*>.execute(handler: OtherFrameworkHandler? = null): KotoExecuteResult
    ````

  - ```kotlin
    inline fun <reified T : KPojo> OtherFrameworkHandler.select(
        kPojo: T = T::class.javaInstance(),
        vararg fields: Any
    ): SelectAction<T
    ````

  - ```kotlin
    fun OtherFrameworkHandler.statistic(tableName: String): Statistic
    ````

##### If you don't know where to start, you can refer to the official spring-wrapper/jdbi-wrapper/basic-wrapper project, or fork one of them to modify.
