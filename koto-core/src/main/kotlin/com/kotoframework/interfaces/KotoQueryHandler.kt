package com.kotoframework.interfaces

import kotlin.reflect.KClass

/**
 * Created by ousc on 2022/9/20 10:20
 */
abstract class KotoQueryHandler {
    abstract fun forList(
        jdbc: KotoJdbcWrapper? = null,
        sql: String,
        paramMap: Map<String, Any?>,
        kClass: KClass<*>
    ): List<Any>

    abstract fun forObject(
        jdbc: KotoJdbcWrapper? = null,
        sql: String,
        paramMap: Map<String, Any?>,
        withoutErrorPrintln: Boolean = false,
        kClass: KClass<*>
    ): Any

    abstract fun forObjectOrNull(
        jdbc: KotoJdbcWrapper? = null,
        sql: String,
        paramMap: Map<String, Any?>,
        kClass: KClass<*>
    ): Any?
}
