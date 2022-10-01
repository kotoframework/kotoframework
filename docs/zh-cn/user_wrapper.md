# 📒如何创建自己的wrapper

用户可以自定义wrapper和handler类来快速和自己已有的业务框架结合，而不需要引入其他的插件及所需的依赖，如您如果正在使用mybatis/hebernate/sql2o框架，那么可以利用其执行原生sql的能力，实现以下几个接口并为koto添加扩展函数，即可除200kb的koto-core外无需引入更多的依赖。

## 需要做什么

实现以下接口或定义扩展函数：

- 实现`KotoQueryWrapper()`抽象类并重载以下函数：

  - ```kotlin
    abstract fun queryForList(sql: String,  paramMap: Map<String, Any?>): List<Map<String, Any>> //查询列表
    ```

  - ```kotlin
    abstract fun <T> queryForObject(sql: String,  paramMap: Map<String, Any?>,  clazz: Class<T>): T? //查询单个对象（T为基础类型）
    ```

  - ```kotlin
    abstract fun update(sql: String,  paramMap: Map<String, Any?>): Int //执行更新/新增/删除语句
    ```

  - ```kotlin
    abstract fun batchUpdate(sql: String,  paramMaps: Array<Map<String,  Any?>>): IntArray //批量updaet
    ```

  - ```kotlin
    abstract val url: String get() //获取当前connection的url
    ```

- 实现`KotoQueryHandler()`虚拟类并重载以下函数：

  - ```kotlin
    abstract fun forList(
        jdbc: KotoJdbcWrapper? = null, //此处请as为您自己的wrapper类型
        sql: String,
        paramMap: Map<String, Any?>,
        kClass: KClass<*>
    ): List<Any> //查询List（带类型，可能为KPojo对象，需要判断是否继承KPojo进行toKPojo处理）
    ```

  - ```kotlin
    abstract fun forObject(
        jdbc: KotoJdbcWrapper? = null,//此处请as为您自己的wrapper类型
        sql: String,
        paramMap: Map<String, Any?>,
        withoutErrorPrintln: Boolean = false,//只有在OrNull时为True，若为true，查询结果为null时返回null，否则抛出异常
        kClass: KClass<*>
    ): Any//查询对象（带类型，可能为KPojo对象，需要判断是否继承KPojo进行toKPojo处理）
    ```

  - ```kotlin
    abstract fun forObjectOrNull(
        jdbc: KotoJdbcWrapper? = null,//此处请as为您自己的wrapper类型
        sql: String,
        paramMap: Map<String,  Any?>,
        kClass: KClass<*>
    ): Any? //查询对象，调用forObject实现，返回结果可为空
    ```

- 为`KotoApp`提供扩展函数`setDataSource()`和`setDynamicDataSource`

- 最后，只要将以下函数中的handler换成您正在使用的其他框架handler类或者DataSource对象即可。

  - ```kotlin
    fun KotoOperationSet.execute(handler: OtherFrameworkHandler? = null): KotoExecuteResult
    ```

  - ```kotlin
    fun <T : KPojo, E : KPojo, ...> OtherFrameworkHandler.associate(...): Associate
    ```

  - ```kotlin
    fun CreateWhere<*>.execute(handler: OtherFrameworkHandler? = null): KotoExecuteResult
    ```

  - ```kotlin
    fun <T : KPojo> OtherFrameworkHandler.remove(KPojo: T): RemoveAction<T>
    ```

  - ```kotlin
    fun OtherFrameworkHandler.remove(tableName: String): RemoveAction<Unknown>
    ```

  - ```kotlin
    fun RemoveAction<*>.execute(handler: OtherFrameworkHandler? = null): KotoExecuteResult
    ```

  - ```kotlin
    inline fun <reified T : KPojo> OtherFrameworkHandler.select(
        kPojo: T = T::class.javaInstance(),
        vararg fields: Any
    ): SelectAction<T
    ```

  - ```kotlin
    fun OtherFrameworkHandler.statistic(tableName: String): Statistic
    ```

##### 若您还不知道从何下手，可以参考官方spring-wrapper/jdbi-wrapper/basic-wrapper项目，或fork其中之一进行修改。